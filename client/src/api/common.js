import { instance, fileInstance, authInstance } from "./config";

export const signupReq = async (signupDto) => {
  return await instance.post("/auth/signup", signupDto);
}

export const loginReq = async (loginDto) => {
  return await instance.post("/auth/login", loginDto);
}

export const emailAuthReq = async (emailDto) => {
  return await instance.post("/auth/send-email", emailDto);
}

export const nickDupCheckReq = async (nick) => {
  return await instance.get("/auth/chk-nickname?nickname=" + nick)
}

export const emailDupCheckReq = async (email) => {
  return await instance.get("/auth/chk-email?email=" + email)
}

export const emailAuthResultReq = async (key, task) => {
  return await instance.get("/auth/verify-email?link=" + key + "&task=" + task)
}

export const oauthTokenReq = async () => {
  return await instance.post("/auth/oauth2/access-token");
}

export const postArticleRequest = async (requestArticleDto) => {
  return await authInstance.post("/article",requestArticleDto)
}

export const deleteArticleRequest = async (articleId) => {
  return await authInstance.delete("/article/"+articleId)
}