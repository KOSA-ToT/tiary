# Quick Start

## ✨ Features

- Spring Boot
- MySQL
- Redis
- Docker
- AWS S3
- Vue.js
- tailwindCSS
- pinia

## 🚀 Getting Started

### 1. Prerequisites

- [Node.js](https://nodejs.org/)
- [Docker](https://www.docker.com/get-started)
    - [Mac/Window] [Install Docker Desktop](https://www.docker.com/products/docker-desktop/)
    - [Linux] [Install Docker Engine](https://ysheee.github.io/docker/note/installation/)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

### 2. Clone the repository

``` bash
# (git + ssh key)
git clone git@github.com:KOSA-ToT/tiary.git

# 또는 (git + https)
git clone https://github.com/KOSA-ToT/tiary.git

# 프로젝트 디렉토리로 이동
cd tiary
```

### 3. Run the project

### 3-1. SERVER

1. MySQL 실행 (권장: Docker)
2. Redis 실행 (권장: Docker)
3. `server` 디렉토리로 이동
``` bash
cd server
```
4. application.yml 생성하기
``` bash
# resources 디렉토리 생성
mkdir -p src/main/resources

# application.yml 생성
touch src/main/resources/application.yml
```
5. application.yml 설정하기 (`{}` 값 변경)
``` yaml
server:
  port: 8088

spring.datasource.url: jdbc:mysql://{MySQL_IP:MySQL_PORT}/{DB_name}?characterEncoding=UTF-8
spring.datasource.username: {DB_username}
spring.datasource.password: {DB_password}
spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver

spring:
  #메일 관련 설정
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${mail.username}
    password: ${mail.password}
    properties:
      mail:
        smtp:
          auth: true
          timeout: 300000
          starttls:
            enable: true
    verify-link: "http://{Client_IP}:5173/auth/verify-email?link="
  #redis
  data:
    redis:
      host: {Redis_IP}
      port: {Redis_PORT}
  #파일관련설정
  servlet:
    multipart:
      max-file-size: 50MB
      max-request-size: 50MB

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    generate-ddl: true
    properties:
      hibernate:
        format_sql: true
    database: mysql
    database-platform: org.hibernate.dialect.MySQL8Dialect
  security:
    oauth2:
      success-redirect: "http://{Clint_IP}:5173/oauth/callback"
      failure-redirect: "http://{Clint_IP}:5173/error"
      client:
        registration:
          google:
            client-id: {Google_Client_ID}
            client-secret: {Google_Client_Secret}
            scope:
              - email
              - profile
          naver:
            client-id: {Naver_Client_ID}
            client-secret: {Naver_Client_Secret}
            client-name: Naver
            redirect-uri: http://{Server_IP}:8088/login/oauth2/code/naver
            authorization-grant-type: authorization_code
            scope:
              - name
              - nickname
              - email
        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response
logging:
  level:
    org:
      hibernate : info

cloud:
  aws:
    credentials:
      access-key: {AWS_ACCESS_KEY}
      secret-key: {AWS_SECRET_KEY}
    region:
      static: {AWS_REGION}
      auto: false
    stack:
      auto: false
    s3:
      bucket: {AWS_S3_BUCKET}

batch:
  server:
    url: http://{Server_IP}:8089

jwt:
  issuer: {JWT_ISSUER}
  secret: {JWT_SECRET}
```
6. IntelliJ IDEA에서 이메일 서비스를 위한 설정하기 (수정 예정)
``` bash
mail.username=;mail.password=
```
7. Spring Boot 애플리케이션 실행하기

### 3-2. BATCH

1. `batch` 디렉토리로 이동
``` bash
cd batch
```
2. application.yml 생성하기
``` bash
# resources 디렉토리 생성
mkdir -p src/main/resources

# application.yml 생성
touch src/main/resources/application.yml
```
3. application.yml 설정하기
``` yaml
server:
  port: 8089

spring.datasource.url: jdbc:mysql://{MySQL_IP:MySQL_PORT}/{DB_name}?characterEncoding=UTF-8
spring.datasource.username: {DB_username}
spring.datasource.password: {DB_password}
spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver

spring:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    generate-ddl: true
    properties:
      hibernate:
        format_sql: true
    database: mysql
    database-platform: org.hibernate.dialect.MySQL8Dialect
    
logging:
  level:
    org:
      hibernate : info

cloud:
  aws:
    credentials:
      access-key: {AWS_ACCESS_KEY}
      secret-key: {AWS_SECRET_KEY}
    region:
      static: {AWS_REGION}
      auto: false
    stack:
      auto: false
    s3:
      bucket: {AWS_S3_BUCKET}
```
4. Spring Boot 애플리케이션 실행하기

### 3-3. PAYMENT

1. `payment` 디렉토리로 이동
``` bash
cd payment
```
2. application.yml 생성하기
``` bash
# resources 디렉토리 생성
mkdir -p src/main/resources

# application.yml 생성
touch src/main/resources/application.yml
```
3. application.yml 설정하기
``` yaml
server:
  port: 8889

spring.datasource.url: jdbc:mysql://{MySQL_IP:MySQL_PORT}/{DB_name}?characterEncoding=UTF-8
spring.datasource.username: {DB_username}
spring.datasource.password: {DB_password}
spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver

spring:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    generate-ddl: true
    properties:
      hibernate:
        format_sql: true
    database: mysql
    database-platform: org.hibernate.dialect.MySQL8Dialect

toss:
  payments:
    payment-url: "https://api.tosspayments.com/v1/payments"
    test-secret-key: {"TOSS_TEST_SECRET_KEY"}
```
4. Spring Boot 애플리케이션 실행하기

### 3-4. CLIENT

1. 필요 라이브러리 설치

``` bash
npm install
```

2. 환경 변수 설정

``` bash
# .env.example을 .env로 복사하기
cp -v .env.example .env

# 환경 변수 수정하기 (필요한 경우)
vi .env
```

3. 클라이언트 서버 실행

``` bash
npm run dev
```




