
export function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
}

export function userEmail(token) {
  if (token) {
    const decodedPayload = parseJwt(token);
    return decodedPayload.sub;
  } else {
    console.log('토큰이 없습니다.');
  }
}

export function userId(token) {
  if (token) {
    const decodedPayload = parseJwt(token);
    return decodedPayload.id;
  } else {
    console.log('토큰이 없습니다.');
  }

}