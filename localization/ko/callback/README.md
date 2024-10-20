---  
title: Callback  
shortTitle: Callback  
category: Idiom  
language: ko  
tag:
- Reactive
---  

## 의도

콜백은 다른 코드에 인자로 넘어가는 실행 가능한 코드의 일부로, 인자는 적절한 시점에 다시 호출 (실행) 된다.

## 설명

실제 예제

> 실행 항목이 완료되었다는 사실을 고지받아야 할 때, 콜백 함수를 실행 항목을 담당하는 함수에게 넘겨주고 다시 호출할 때까지 기다린다.

명확하게는

> 콜백은 실행 함수에 넘겨져서 정의된 순간에 호출되는 함수이다.

위키피디아의 정의는

> 컴퓨터 프로그래밍에서, "사후-호출" 함수로도 알려진 콜백은, 다른 코드에 인자로 넘겨지는 실행 가능한 코드를 말한다;  
> 다른 코드는 정해진 시간에 인자를 다시 호출 (실행) 할 것으로 기대되어진다.

**코드 예제**

콜백은 하나의 메서드를 가지고 있는 단순한 인터페이스이다.

```java  
public interface Callback {  
  
  void call();}  
```  

다음으로 작업의 실행이 끝나고 나서 콜백을 실행시킬 Task 클래스를 정의한다.

```java  
public abstract class Task {  
  
  final void executeWith(Callback callback) {    execute();    Optional.ofNullable(callback).ifPresent(Callback::call);  }  
  public abstract void execute();}  
  
@Slf4j  
public final class SimpleTask extends Task {  
  
  @Override  public void execute() {    LOGGER.info("우선순위의 작업을 마친 이후 콜백 메서드를 호출한다.");  
  }}  
```  

마지막으로, 다음은 작업을 실행한 후 마쳤을 때 콜백을 받는 방법의 예시이다.

```java  
    var task = new SimpleTask();    task.executeWith(() -> LOGGER.info("완료되었음."));  
```  

## 클래스 다이어그램

![alt text](./etc/callback.png "callback")

## 적용

콜백 패턴을 사용할 수 있을 때는

* 동기 또는 비동기적인 임의의 행위가 정의된 어떠한 행위의 실행 이후에 수행되어야 할 때이다.

## 실질적인 예시들

* [CyclicBarrier](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CyclicBarrier.html#CyclicBarrier%28int,%20java.lang.Runnable%29) 생성자는 장벽이 넘어질 때마다 발동되는 콜백을 인자로 받을 수 있다.