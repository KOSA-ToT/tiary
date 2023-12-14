import { defineStore } from 'pinia';
import { ref } from 'vue';

export const appStore = defineStore('app',()=>{
    // id: 'app',
    const dataFromChild= ref()
    // actions: {
    //     setDataFromChild(data) {
    //     // 수정: this.$patch 대신 this.patch를 사용
    //     this.patch({ dataFromChild: data });
    //     },
    // },
    function setDataFromChild(data) {
        dataFromChild.value=data;
    }
    return {dataFromChild, setDataFromChild}
});
