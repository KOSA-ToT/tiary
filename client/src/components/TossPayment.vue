<template>
  <div>
    <section class="hero is-link">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">
            토스페이먼츠 연동 샘플
          </h1>
        </div>
      </div>
    </section>
    <section class="section">
      <div class="container">
        <button
          class="price-btn"
          v-on:click="pay('카드', price.value)"
          v-for="price in prices"
          :key="price.id"
          :class="{ 'hovered': price.hover }"
          @mouseover="hoverButton(price.id, true)"
          @mouseout="hoverButton(price.id, false)"
        >
          {{ formatPrice(price.value) }}원
        </button>
        <!-- 다른 결제 수단 버튼들 주석 처리 -->
      </div>
    </section>

    <!-- 선택한 페이지 렌더링 -->
    <router-view />
  </div>
</template>

<script>
import { ref } from 'vue';
import { loadTossPayments } from '@tosspayments/payment-sdk';
import { tossPaymentReq } from '@/api/common';

const clientKey = import.meta.env.VITE_CLIENT_KEY;
const payUrl = import.meta.env.VITE_PAY_URL;

export default {
  name: 'pgWindow',
  setup() {
    const prices = ref([
      { id: 1, value: 1000, hover: false },
      { id: 2, value: 5000, hover: false },
      { id: 3, value: 10000, hover: false },
    ]);

    const tossPaymentDto = ref({
      amount: 0,
      orderId: '',
      orderName: '',
      customerName: '',
      successUrl: 'http://localhost:5173/success',
      failUrl: 'http://localhost:5173/fail'
    });

    const formatPrice = (price) => {
      // 숫자를 3자리마다 쉼표를 추가하여 포맷팅
      return price.toLocaleString();
    };

    const func1 = loadTossPayments(clientKey)

    // 백엔드에서 결제창 요청 후에 200 뜨면 프론트에서 창 띄워주는 requestPayment요청

    const pay = (method, price) => {
      tossPaymentDto.value.amount = price;
      try {
        const response = tossPaymentReq(tossPaymentDto.value);
        console.log(response)


        if(response.status === 200) {
          func1.then((tossPayments) => {
            const amt = price;
            const orderId = new Date().getTime();

            tossPayments
              .requestPayment(method, tossPaymentDto.value)
              .catch((error) => {
                if (error.code === 'USER_CANCEL') {
                  alert('취소되었습니다.');
                } else {
                  alert(error.message);
                }
              });
          });
        }
      } catch(error) {
        console.log(error)
      }

      
    };

    const hoverButton = (id, isHovered) => {
      const button = prices.value.find((price) => price.id === id);
      if (button) {
        button.hover = isHovered;
      }
    };

    return {
      prices,
      pay,
      hoverButton,
      formatPrice,
    };
  },
};
</script>

<style scoped>
.price-btn {
  background-color: #ffffff;
  border: 1px solid #ff6600;
  color: #ff6600;
  width: 100px; /* 버튼의 폭을 조정하여 크기 고정 */
  height: 50px; /* 버튼의 높이를 조정하여 크기 고정 */
  margin-right: 10px;
  position: relative;
  overflow: hidden;
  text-align: center;
  transition: background-color 0.3s, border-color 0.3s, color 0.3s;
}

.price-btn.hovered {
  background-color: #ff6600;
  border-color: #ffffff;
  color: #ffffff;
}

.price-text {
  display: inline-block;
  line-height: 50px; /* 텍스트의 세로 중앙 정렬을 위한 라인 높이 조정 */
}
</style>
