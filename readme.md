# Spring R Socket Server Example

## 개요

- Spring Webflux에서 R Socket을 다루는 예시 코드입니다.  
  R Socket을 통해 이벤트를 주고받을 때, 이 애플리케이션은 이벤트를 받아 처리하는 부분에 해당합니다.

- Client로부터 REST API 등의 호출을 받고, 내부적으로 이벤트를 발행하는 코드는 <a href="https://github.com/Example-Collection/Spring_RSocket_Client_Example">여기</a>에 있습니다.

- R Socket 패러다임에 맞게, 총 4개의 라우팅 메소드가 포함되어 있습니다.

  - _요청-응답_ : `items.saveRequestResponse`
  - _요청-스트림_ : `items.getRequestStream`
  - _실행 후 망각_: `items.saveWithoutResponse`
  - _채널_ : `items.monitor`

- Netty 서버 자체는 9000번 포트에, Spring R Socket 포트는 7000번을 사용합니다.

<hr/>

## 실행 방법

- 이 프로젝트는 Spring Webflux로 작성되어 있으며, MongoDB를 함께 사용합니다.  
  따라서 실행되고 있고, 접근할 수 있는 MongoDB가 있어야 합니다.  
  가장 간단하게, 아래 명령어로 Docker에 Mongo Container를 띄우면 데모용으로는 충분합니다.

```sh
docker run -p 27017-27019:27017-27019 mongo
```
