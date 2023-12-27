# 첫 번째 단계: 빌드 환경
# Node.js 이미지를 기반으로 설정합니다.
FROM node:18 AS build-stage
WORKDIR /app

# 패키지 파일과 잠금 파일을 복사합니다.
COPY package*.json ./
COPY .env ./

# 의존성을 설치합니다.
RUN npm install

# 애플리케이션 소스 코드를 복사합니다.
COPY . .

# 애플리케이션을 빌드합니다.
RUN npm run build

# 두 번째 단계: 프로덕션 환경
FROM nginx:stable as production-stage

# 빌드된 애플리케이션을 Nginx 서버에 복사합니다.
COPY --from=build-stage /app/dist /usr/share/nginx/html
# Nginx 설정 파일을 복사합니다.
COPY default.conf /etc/nginx/conf.d/default.conf

# Nginx의 포트 80을 노출시킵니다.
EXPOSE 80

# Nginx 서버를 실행합니다.
CMD ["nginx", "-g", "daemon off;"]