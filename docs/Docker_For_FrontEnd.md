# API서버 도커로 사용하기



1. 도커 다운로드

2. 전달받은 tar파일 load하기

   ```shell
   docker load -i [파일명].tar
   ```

   현재 API서버, DB서버 2개 모두 docker load 필요합니다.

3. docker-compose파일 만들기

   ***docker-compose.yml***

   ```yaml
   version: "3"
   services:
     db:
       image: mysql:latest
       container_name: db
       restart: always
       ports:
         - "3307:3306"
       environment:
         MYSQL_DATABASE: "whynot"
         MYSQL_ROOT_PASSWORD: "root"
         MYSQL_USER: "user"
         MYSQL_PASSWORD: "user"
       command:
         - --character-set-server=utf8mb4
         - --collation-server=utf8mb4_unicode_ci
       networks:
         - springboot-mysql-net
     api-server:
       image: whynot:0.0.1-SNAPSHOT
       container_name: "api-server"
       ports:
         - "9000:9000"
       depends_on:
         - db
       networks:
         - springboot-mysql-net
   
   networks:
     springboot-mysql-net:
       driver: bridge
   ```

4. 도커 실행

   docker-compose.yml이 있는 경로에서 다음 명령어 실행

   ```shell
   docker-compose -f docker-compose.yml up -d
   ```

   
