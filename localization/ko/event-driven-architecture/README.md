---
title: Event Driven Architecture
category: Architectural
language: ko
tag:
- Reactive
---

## 의도
이벤트 드리븐 아키텍처를 사용하는 다른 어플리케이션에 특정 객체의 상태 변화를 알리고 관련 이벤트를 발송할 수 있습니다.

## 클래스 다이어그램
![alt text](./etc/eda.png "Event Driven Architecture")

## 적용 가능성
아래와 같은 상황에서 이벤트 드리븐 아키텍처를 사용할 수 있습니다.

* 느슨하게 연결된 시스템을 구축하고자 하는 경우
* 반응형 시스템을 구축하고자 하는 경우
* 확장성을 갖춘 시스템을 구축하고자 하는 경우

## 실제 적용 사례

* Chargify라는 결제 API에서는 다양한 이벤트를 통해 결제 활동을 드러냅니다. (https://docs.chargify.com/api-events)
* Amazon의 AWS Lambda에서는 Amazon S3 버킷에서의 변경, Amazon DynamoDB 테이블에서의 수정이나 어플리케이션 및 기기에서 발생한 커스텀 이벤트에 대응하여 코드를 실행할 수 있습니다. (https://aws.amazon.com/lambda)
* MySQL은 데이터베이스 테이블에서 발생하는 삽입이나 수정 이벤트를 기반으로 트리거를 실행합니다.

## 크레딧

* [이벤트 드리븐 아키텍처 - 위키피디아](https://en.wikipedia.org/wiki/Event-driven_architecture)
* [이벤트 드리븐 아키텍처는 무엇인가?](https://aws.amazon.com/event-driven-architecture/)
* [실제 적용 사례/이벤트 드리븐 아키텍처](https://wiki.haskell.org/Real_World_Applications/Event_Driven_Applications)
* [이벤트 드리븐 아키텍처의 정의](http://searchsoa.techtarget.com/definition/event-driven-architecture)
