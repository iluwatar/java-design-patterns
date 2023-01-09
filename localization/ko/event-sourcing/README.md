---
title: Event Sourcing
category: Architectural
language: ko
tag:
- Performance
- Cloud distributed

---

## 의도

데이터의 현재 상태만 도메인에 저장하는 대신, 추가 전용 저장소를 사용하여 해당 데이터에 수행된 전체 작업을 기록하십시오. 저장소는 기록 시스템으로서의 역할을 하며 도메인 객체를 구체화하는데 사용할 수 있습니다. 따라서 데이터 모델과 비지니스 도메인을 동기화할 필요가 없고 성능, 확장성 및 응답성이 향상되어 복잡한 도메인에서의 작업을 단순화할 수 있습니다.
또한, 트랜잭션 데이터에 대한 일관성을 제공하고, 보상 작업을 수행할 수 있는 전체 감사용 기록과 히스토리를 유지 관리할 수 있습니다.

## 클래스 다이어그램

![alt text](./etc/event-sourcing.png "Event Sourcing")

## 적용 가능성
아래와 같은 상황에서 이벤트 소싱 패턴을 사용할 수 있습니다.

* 관계형 데이터 구조가 복잡한 어플리케이션 상태를 유지하기 위해 매우 높은 성능이 필요한 경우
* 어플리케이션 상태의 변경 사항과 특정 시점의 상태를 복원할 수 있는 기능에 대한 로그가 필요한 경우
* 과거에 발생한 이벤트를 다시 적용하여 프로덕션 문제를 디버깅하는 경우

## 실제 적용 사례

* [Lmax 아키텍처](https://martinfowler.com/articles/lmax.html)

## 크레딧

* [마틴 파울러 - 이벤트 소싱](https://martinfowler.com/eaaDev/EventSourcing.html)
* [이벤트 소싱에 대한 마이크로소프트의 문서](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
* [레퍼런스 3: 이벤트 소싱에 대한 소개](https://msdn.microsoft.com/en-us/library/jj591559.aspx)
* [이벤트 소싱 패턴](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
