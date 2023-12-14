import axios from "axios";

const baseConfig = {
  baseURL: "http://localhost:8088", // 로컬
  headers: {
    'Content-type': 'application/json'
  }
};

const fileConfig = {
  baseURL: "http://localhost:8088", // 로컬
  headers: {
    'Content-Type': 'multipart/form-data',
    accept: 'application/json'
  },
};

export const instance = axios.create(baseConfig);
export const authInstance = axios.create(baseConfig);
export const fileInstance = axios.create(fileConfig);


authInstance.interceptors.request.use((config) => {
  config.headers.Authorization = getLocalStorageToken();
  return config;
})

const getLocalStorageToken = () => {
  return localStorage.getItem("accessToken");
}