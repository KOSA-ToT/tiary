import axios from "axios";
import Cookies from 'js-cookie';
import { parseJwt } from '@/utils/jwtUtils';

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

export const instance = axios.create(baseConfig);
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
      console.log('토큰 만료, 새로고침 필요');

      // 토큰을 갱신하고 새로운 토큰을 설정
      await refresh();
      authInstance.defaults.headers.common['Authorization'] = getLocalStorageToken();

      // 갱신된 토큰으로 다시 요청을 시도
      return authInstance(error.config);
    }

    // 다른 에러 처리
    return Promise.reject(error);
  }
);

// 리프레시 토큰
const refresh = async (config) => {
  let token = localStorage.getItem('Authorization');
  const decodedPayload = parseJwt(token);
  console.log(decodedPayload.exp) // 유닉스 시간

  const currentTime = Math.floor(Date.now() / 1000);
  // 토큰 만료시
  if(decodedPayload.exp - currentTime < 0) {
    console.log(decodedPayload.exp - currentTime)
    console.log('토큰 만료');

    // 리프레시 토큰으로 엑세스 토큰 갱신
    console.log('리프레시 토큰 = ')
    const refreshToken = Cookies.get('refreshToken');
    console.log(refreshToken)
    // localStorage.setItem('Authorization', refreshToken);
  }
  return config;
}

