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
      </div>
    </section>


    <router-view />
  </div>
</template>

<script>
import { ref } from 'vue';
import { loadTossPayments } from '@tosspayments/payment-sdk';
import { v4 as uuidv4 } from "uuid";

const clientKey = import.meta.env.VITE_CLIENT_KEY;
const successUrl = import.meta.env.VITE_SUCCESS_URL;


export default {
  name: 'pgWindow',
  setup() {
    const prices = ref([
      { id: 'C1', value: 1000, hover: false },
      { id: 'C5', value: 5000, hover: false },
      { id: 'C10', value: 10000, hover: false },
    ]);

    const func1 = loadTossPayments(clientKey);

    const formatPrice = (price) => {
      // 숫자를 3자리마다 쉼표를 추가하여 포맷팅
      return price.toLocaleString();
    };

    const generateOrderId = () => {
      // uuid 생성 및 8자리로 자르기
      return uuidv4().replace(/-/g, '').substring(0, 8);
    };

    const pay = (method, price) => {
      func1.then((tossPayments) => {
        const amt = price;
        const orderId = generateOrderId();

        tossPayments
          .requestPayment(method, {
            amount: amt,
            orderId: orderId,
            orderName: '토스 티셔츠 외 1건',
            customerName: '박토스',
            successUrl: 'http://localhost:8889/payment/success',
            failUrl: 'http://localhost:8889/payment/fail',
            supporterId: '',
            receiverId: ''
          })
          .catch((error) => {
            if (error.code === 'USER_CANCEL') {
              alert('취소되었습니다.');
            } else {
              alert(error.message);
            }
          });
      });
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
