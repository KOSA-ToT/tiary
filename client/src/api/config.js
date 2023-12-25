import axios from "axios";

const baseConfig = {
  baseURL: "http://localhost:8088", // 로컬
  headers: {
    'Content-type': 'application/json'
  },
  withCredentials: true
};

const fileConfig = {
  baseURL: "http://localhost:8088", // 로컬
  headers: {
    'Content-Type': 'multipart/form-data',
    accept: 'application/json'
  },
  withCredentials: true,
};

const batchConfig = {
  baseURL: "http://localhost:8089", // 로컬
  headers: {
    'Content-type': 'application/json'
  },
};

const paymentConfig = {
  baseURL: "http://localhost:8889", // 로컬
  headers: {
    'Content-type': 'application/json'
  },
};


export const instance = axios.create(baseConfig);
export const batchInstance = axios.create(batchConfig); // 게이트웨이 구현 하면 필요없어질듯
export const paymentInstance = axios.create(paymentConfig); // 게이트웨이 구현 하면 필요없어질듯
export const authInstance = axios.create(baseConfig);
export const fileInstance = axios.create(fileConfig);


authInstance.interceptors.request.use((config) => {
  config.headers.Authorization = getLocalStorageToken();
  return config;
})

const getLocalStorageToken = () => {
  return localStorage.getItem("Authorization");
}

authInstance.interceptors.response.use(
  (response) => {
    // 응답이 성공적으로 왔을 때 처리
    return response;
  },
  async (error) => {
    // 응답이 에러인 경우 처리
    if (error.response && error.response.status === 403) {
      // 토큰을 갱신하고 새로운 토큰을 설정
      await refresh(error);
      authInstance.defaults.headers.common['Authorization'] = getLocalStorageToken();

      // 갱신된 토큰으로 다시 요청을 시도
      return authInstance(error.config);
    }

    // 다른 에러 처리
    return Promise.reject(error);
  }
);

// 리프레시 토큰
const refresh = async (error) => {
  const headerToken = error.response.headers.get('Authorization');
  localStorage.setItem('Authorization', headerToken);
}

