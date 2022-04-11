# whynot-here-api-server
whynot-here 서비스 API 서버



<br />

## Link
- Swagger: [http://localhost:9000/swagger-ui/index.html](http://localhost:9000/swagger-ui/index.html)



<br />

## Intelij 환경설정

- Edit Configuration -> Environment Variables

```
--spring.config.location=classpath:/whynot-config/env.yml,classpath:/application.yml
```



<br />

## 서브모듈

- 클론받는 경우

  ```shell
  git clone --recurse-submodules [repo주소]
  ```

- 업데이트 하는 경우

  ```shell
  git submodule update --remote
  ```

  

