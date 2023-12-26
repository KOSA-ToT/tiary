<template>
  <Loading>
    <h1 class="text-7xl font-bold mt-20">Loading...</h1>
  </Loading>
</template>

<script setup>
import Loading from '@/components/Loading.vue';
import { onMounted, ref, defineProps } from 'vue';
import { tossPaymentReq } from '@/api/common';
import router from '@/router';

onMounted(() => {
  extractPaymentInfoAndRedirect();
});

const props = defineProps(['data']);
const paymentKey = ref(null);
const orderId = ref(null);
const amount = ref(null);

async function extractPaymentInfoAndRedirect() {
  const params = new URLSearchParams(window.location.search);
  paymentKey.value = params.get('paymentKey');
  orderId.value = params.get('orderId');
  amount.value = params.get('amount');

  console.log(props.data);
  const [articleId, userEmail] = props.data.split('_');

  const paymentDto = {
    supporterEmail: userEmail,
    articleId: articleId,
    paymentKey: paymentKey.value,
    orderId: orderId.value,
    amount: amount.value,
  };

  try {
    const response = await tossPaymentReq(paymentDto);
    console.log(response);
    console.log(response.status);
    if(response.status === 200) {
      alert("후원이 완료되었습니다!")
      router.push('/article/' + articleId);
    }
  } catch (error) {
    console.log(error);
  }
}
</script>
