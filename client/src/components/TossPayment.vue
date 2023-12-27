<template>
  <div>
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

<script setup>
import { ref, defineProps } from 'vue';
import { loadTossPayments } from '@tosspayments/payment-sdk';
import { v4 as uuidv4 } from 'uuid';

const props = defineProps(['userId', 'articleId', 'supporter', 'receiver', 'author']);
const clientKey = import.meta.env.VITE_CLIENT_KEY;
const successUrl = import.meta.env.VITE_PAYMENT_SUCCESS_URL;
const failUrl = import.meta.env.VITE_PAYMENT_FAIL_URL;
const paydata = props.articleId + '_' + props.supporter;

const prices = ref([
  { id: 'C1', value: 1000, hover: false },
  { id: 'C5', value: 5000, hover: false },
  { id: 'C10', value: 10000, hover: false },
]);

const func1 = loadTossPayments(clientKey);

const formatPrice = (price) => {
  return price.toLocaleString();
};

const generateOrderId = () => {
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
        orderName: props.author + '님에게 ' + amt + '원 후원',
        customerName: 'Tiary',
        successUrl: successUrl + paydata,
        failUrl: failUrl
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
