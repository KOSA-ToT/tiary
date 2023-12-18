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

export const getUser = async (userId) => {
  return await instance.get("/users/"+ userId);
}
export const listMyPost = async (userId,page) => {
  return await instance.get("/users/"+userId+"/posts?page="+ page);
}
export const editNickname = async (userId,nickname) => {
  return await authInstance.patch("/users/"+userId+"/updateNickname", nickname);
}
export const handleWithDrawal = async (userId,nickname) => {
  return await instance.patch("/users/"+userId+"/inactive", nickname);
}
export const changeProfileImage = async (userId,formData) => {
  return await fileInstance.post("/users/"+userId+"/uploadProfileImg", formData);
}
export const listMyComment = async (userId) => {
  return await instance.get("/users/"+userId+"/comments");
}
export const listSubscribers = async (userId) => {
  return await instance.get("/users/"+userId+"/subscriber");
}
export const listsubscribedWriter = async (userId) => {
  return await instance.get("/users/"+userId+"/subscribedWriter");
}
export const subscribeUser = async (userId) => {
  return await authInstance.get("/users/"+userId+"/subscribe");
}