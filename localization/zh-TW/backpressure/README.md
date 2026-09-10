---
title: "Java 背壓模式：調節生產者到消費者的資料流以避免過載"
shortTitle: 背壓
description: "透過實例了解 Java 背壓設計模式，讓資料流符合消費者能力，在避免過載的同時維持系統穩定與效能。"
category: Concurrency
language: zh-TW
tag:
    - Asynchronous
    - Event-driven
    - Reactive
    - Resilience
---

## 又稱為

* 流量控制
* 速率限制機制

## 背壓模式的目的

控制資料產生的速率，避免下游消費者承受過量負載。

## 背壓模式的詳細說明與真實世界範例

真實世界範例

> 想像一家忙碌的咖啡店有多位咖啡師負責沖泡飲料（生產者），但只有一位咖啡師負責製作拉花（消費者）。如果沖泡速度超過拉花速度，飲料就會堆積，造成品質問題或必須丟棄。加入節奏控制後，只有在拉花咖啡師準備好時才送出下一杯，所有人便能保持同步，減少浪費並維持一致的體驗。

簡單來說

> 背壓是一種流量控制機制，會依照消費者的處理能力調節資料產生速度，避免系統過載。

維基百科指出：

> 背壓是對流體通過管道時理想流動的阻力。在分散式系統，尤其是事件驅動架構中，背壓是一種調節資料流量的技術，可避免元件承受過量負載。

序列圖

![背壓序列圖](./etc/backpressure-sequence-diagram.png)

## Java 背壓模式的程式範例

本範例使用 Project Reactor 實作背壓。首先建立會產生整數串流的發佈者，並加入短暫延遲來模擬較慢的產生速率：

```java
public class Publisher {
    public static Flux<Integer> publish(int start, int count, int delay) {
        return Flux.range(start, count).delayElements(Duration.ofMillis(delay)).log();
    }
}
```

接著透過 Reactor 的 `BaseSubscriber` 建立自訂訂閱者。它每個項目休眠 500 毫秒來模擬慢速處理；一開始要求 10 個項目，每處理 5 個就再要求 5 個：

```java
public class Subscriber extends BaseSubscriber<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

    @Override
    protected void hookOnSubscribe(@NonNull Subscription subscription) {
        logger.info("subscribe()");
        request(10); // 一開始要求 10 個項目
    }

    @Override
    protected void hookOnNext(@NonNull Integer value) {
        processItem();
        logger.info("process({})", value);
        if (value % 5 == 0) {
            // 處理前 5 個項目後要求下一批 5 個
            request(5);
        }
    }

    @Override
    protected void hookOnComplete() {
        // 處理完成
    }

    private void processItem() {
        try {
            Thread.sleep(500); // 模擬慢速處理
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
```

最後在 `main` 方法中發佈一段整數範圍，並使用自訂訂閱者訂閱。主執行緒的短暫休眠讓資料發佈、背壓要求與處理過程能完整呈現：

```java
public static void main(String[] args) throws InterruptedException {
    Subscriber sub = new Subscriber();
    Publisher.publish(1, 8, 200).subscribe(sub);
    Thread.sleep(5000); // 等待執行完成
}
```

以下是程式輸出範例，包含訂閱者要求更多資料，以及每個整數被處理的紀錄：

```
23:09:55.746 [main] DEBUG reactor.util.Loggers -- Using Slf4j logging framework
23:09:55.762 [main] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onSubscribe(FluxConcatMapNoPrefetch.FluxConcatMapNoPrefetchSubscriber)
23:09:55.762 [main] INFO com.iluwatar.backpressure.Subscriber -- subscribe()
23:09:55.763 [main] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- request(10)
23:09:55.969 [parallel-1] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onNext(1)
23:09:56.475 [parallel-1] INFO com.iluwatar.backpressure.Subscriber -- process(1)
23:09:59.311 [parallel-5] INFO com.iluwatar.backpressure.Subscriber -- process(5)
23:09:59.311 [parallel-5] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- request(5)
23:10:01.437 [parallel-8] INFO reactor.Flux.ConcatMapNoPrefetch.1 -- onComplete()
```

## 何時在 Java 中使用背壓模式

* 資料高速產生、消費者有過載風險的 Java 系統。
* 需要在負載變動時維持穩定性的反應式或事件驅動架構。

## 背壓模式的優點與取捨

優點：

* 保護消費者免於飽和與資源耗盡。

取捨：

* 生產速度必須配合消費能力時，可能引入延遲。
* 多個並行資料來源的複雜系統需要仔細協調。

## 相關 Java 設計模式

* [觀察者模式](https://java-design-patterns.com/patterns/observer/)：兩者都涉及生產者通知消費者；觀察者通常是同步且緊密耦合的。
* [發佈訂閱模式](https://java-design-patterns.com/patterns/publish-subscribe/)：兩者都處理非同步資料流，可以一起管理訊息的分發與消費。

## 參考資料與致謝

* [Backpressure Explained（RedHat Developers Blog）](https://developers.redhat.com/articles/backpressure-explained)
* [Hands-On Reactive Programming in Spring 5](https://amzn.to/3YuYfyO)
* [Reactive Programming with RxJava: Creating Asynchronous, Event-Based Applications](https://amzn.to/42negbf)
* [Reactive Streams in Java](https://amzn.to/3RJjUzA)
* [Reactive Streams Specification](https://www.reactive-streams.org/)