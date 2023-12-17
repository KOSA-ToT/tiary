import axios from "axios";
import moment from "moment";

const baseConfig = {
  baseURL: "http://localhost:8088", // 로컬
  headers: {
    'Content-type': 'application/json'
  },
  withCredentials: true,
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
  refresh();
  return localStorage.getItem("Authorization");
}

// 리프레시 토큰
const refresh = async (config) => {
  let token = localStorage.getItem('Authorization');
  const decodedPayload = parseJwt(token);
    //   email
    // : 
    // "dpa357@gmail.com"
    // exp
    // : 
    // 1702799682
    // id
    // : 
    // 4
    // sub
    // : 
    // "dpa357@gmail.com"
  
  // const expireAt = ;
  console.log(decodedPayload.exp) // 유닉스 시간

  const currentTime = Math.floor(Date.now() / 1000);
  // 토큰 만료시
  if(decodedPayload.exp - currentTime < 0) {
    console.log(decodedPayload.exp - currentTime)
    console.log('토큰 만료');
  }

  return config;
}

function parseJwt (token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
}