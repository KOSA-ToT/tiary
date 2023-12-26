import { instance, authInstance, fileInstance , batchInstance, paymentInstance  } from "./config";

export const signupReq = async (signupDto) => {
  return await instance.post("/auth/signup", signupDto);
};

export const loginReq = async (loginDto) => {
  return await instance.post("/auth/login", loginDto);
};

export const emailAuthReq = async (emailDto) => {
  return await instance.post("/auth/send-email", emailDto);
};

export const rejectEmailAuthReq = async (emailDto) => {
  return await instance.post("/auth/reject-email", emailDto);
};

export const acceptEmailAuthReq = async (emailDto) => {
  return await instance.post("/auth/accept-email", emailDto);
};

export const nickDupCheckReq = async (nick) => {
  return await instance.get("/auth/chk-nickname?nickname=" + nick);
};

export const emailDupCheckReq = async (email) => {
  return await instance.get("/auth/chk-email?email=" + email);
};

export const emailAuthResultReq = async (key, task) => {
  return await instance.get("/auth/verify-email?link=" + key + "&task=" + task);
};

export const oauthTokenReq = async () => {
  return await instance.post("/auth/oauth2/access-token");
};

export const getUserInfoReq = async () => {
  return await authInstance.get("/auth/user");
};

// comment

export const getCommentList = async (articleId) => {
  return await instance.get("/comment/" + articleId);
};

export const createUserComment = async (commentRequestDTO, articleId) => {
  return await authInstance.post("/comment/" + articleId, commentRequestDTO);
};

export const createGuestComment = async (commentRequestDTO, articleId) => {
  return await instance.post("/comment/guest/" + articleId, commentRequestDTO);
};

export const commentPasswordConfirm = async (commentRequestDTO, commentId) => {
  return await instance.post(
    "/comment/guest/password-confirm/" + commentId,
    commentRequestDTO
  );
};

export const editUserComment = async (
  commentRequestDTO,
  articleId,
  commentId
) => {
  return await authInstance.patch(
    "/comment/" + articleId + "/" + commentId,
    commentRequestDTO
  );
};

export const editGuestComment = async (
  commentRequestDTO,
  articleId,
  commentId
) => {
  return await instance.patch(
    "/comment/guest/" + articleId + "/" + commentId,
    commentRequestDTO
  );
};

export const deleteUserComment = async (articleId, commentId) => {
  return await authInstance.delete("/comment/" + articleId + "/" + commentId);
};

export const deleteGuestComment = async (articleId, commentId) => {
  return await instance.delete("/comment/guest/" + articleId + "/" + commentId);
};
export const getArticleRequest = async (articleId) => {
  return await instance.get("/article/" + articleId);
};
export const getArticleListRequest = async () => {
  return await instance.get("/article");
};
export const getRecommendationArticleRequest = async (articleId) => {
  return await batchInstance.get("batch/recommendations/" + articleId);
};
export const postArticleRequest = async (requestArticleDto) => {
  return await authInstance.post("/article", requestArticleDto);
};

export const patchArticleRequest = async (articleId, requestArticleDto) => {
  return await authInstance.patch("/article/" + articleId, requestArticleDto);
};

export const deleteArticleRequest = async (articleId) => {
  return await authInstance.delete("/article/" + articleId);
};
export const getLikeState =async(articleId) =>{
  return await authInstance.get("/likes/" + articleId)
}
export const getArticleLikeRequest = async (articleId) => {
  return await authInstance.get("/article/" + articleId + "/likes?check=likes")
}
export const getArticleLikeCancelRequest = async (articleId) => {
  return await authInstance.get("/article/" + articleId + "/likes?check=")
}
export const getCategoryList = async() => {
  return await instance.get("/category")
}
export const getArticleFromCategory = async(categoryName) =>{
  return await instance.get("/category?categoryName="+categoryName)
}
export const getArticleFromHashtag = async(categoryName) => {
  return await instance.get("/hashtag?categoryName="+categoryName)
}
export const getUser = async (userId) => {
  return await instance.get("/users/" + userId);
};
export const listMyPost = async (userId, page) => {
  return await instance.get("/users/" + userId + "/posts?page=" + page);
};
export const listMySubscribe = async () => {
  return await authInstance.get("/users/postSubscribe");
};
export const editNickname = async (userId, nickname) => {
  return await authInstance.patch(
    "/users/" + userId + "/updateNickname",
    nickname
  );
};
export const handleWithDrawal = async (userId, nickname) => {
  return await instance.patch("/users/" + userId + "/inactive", nickname);
};
export const changeProfileImage = async (userId, formData) => {
  return await fileInstance.post(
    "/users/" + userId + "/uploadProfileImg",
    formData
  );
};
export const listMyComment = async (userId, page) => {
  return await instance.get("/users/" + userId + "/comments?page=" + page);
};
export const listSubscribers = async (userId) => {
  return await instance.get("/users/" + userId + "/subscriber");
};
export const listsubscribedWriter = async (userId) => {
  return await instance.get("/users/" + userId + "/subscribedWriter");
};
export const subscribeUser = async (userId) => {
  return await authInstance.get("/users/" + userId + "/subscribe");
};
export const getUserByEmail = async (email) => {
  return await instance.get("/users/"+email);
}

export const writerApprove = async () => {
  return await authInstance.get("/admin/approving");
};
export const listApprove = async () => {
  return await authInstance.get("/admin/showList");
};
export const writerAccpet = async (userId) => {
  return await authInstance.patch("/admin/acceptWriter/"+userId);
};
export const writerReject = async (userId) => {
  return await authInstance.patch("/admin/rejectWriter/"+userId);
};

export const writerConfirm = async (userId) => {
  return await authInstance.get("/admin/confirmWriter/"+userId);
};
export const numberPosts = async (userId) => {
  return await authInstance.get("/users/"+userId+"/postsNumber");
};
// Admin
export const readNoticeList = async (userId, page) => {
  return await instance.get("/admin/" + userId + "/notice?page=" + page);
};

export const tossPaymentReq = async (paymentDto) => {
  return await paymentInstance.post("/payment/savepayinfo", paymentDto);
}