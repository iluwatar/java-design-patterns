---
title: "Java Bloc 模式：簡化狀態管理"
shortTitle: Bloc
description: "了解 Bloc 模式如何管理 Java 應用程式的狀態變更，以及如何動態管理監聽者。"
category: Architectural
language: zh-TW
tag:
    - Abstraction
    - Data binding
    - Decoupling
    - Event-driven
    - Presentation
    - Reactive
    - Reusability
    - State tracking
---

## 又稱為

* 商業邏輯元件
* 商業邏輯控制器

## Bloc 模式的目的

Bloc 模式管理物件狀態，並在狀態變更時動態通知感興趣的監聽者。它將狀態管理邏輯與應用程式其他部分分離，使程式更有組織且更具彈性。

## Bloc 模式的詳細說明與真實世界範例

真實世界範例

> 考慮一個數位計數器應用程式，每當計數器變更時，畫面上的多個部分都需要更新，例如顯示數值的標籤與記錄變更的活動紀錄。Bloc 模式不直接修改這些 UI 元件，而是管理計數器狀態，並通知所有已註冊的監聽者。監聽者也可以動態訂閱或取消訂閱更新。

簡單來說

> Bloc 模式管理單一狀態物件，並在狀態變更時動態通知已註冊的監聽者。

維基百科指出

> Bloc 並不是正式的四人幫設計模式，但廣泛用於狀態驅動的應用程式。它集中管理狀態，並將狀態變更傳播給已註冊的觀察者，遵循關注點分離原則。

序列圖

![Bloc 序列圖](./etc/bloc-sequence-diagram.png)

## Java Bloc 模式的程式範例

本範例使用 Java 與 Swing 實作 Bloc。此模式將應用程式狀態與 UI 元件分離，以反應式、事件驅動的方式管理更新。

Bloc 的核心元件包括 `State` 狀態物件、負責管理與更新狀態的 `Bloc` 類別，以及用來訂閱變更的 `StateListener` 與 `ListenerManager` 介面。

`State` 類別代表應用程式在特定時間的資料。

```java
public record State(int value) {}
```

`ListenerManager` 介面宣告新增、移除與取得監聽者的方法。

```java
public interface ListenerManager<T> {
  void addListener(StateListener<T> listener);
  void removeListener(StateListener<T> listener);
  List<StateListener<T>> getListeners();
}
```

`StateListener` 定義監聽者如何回應狀態變更。

```java
public interface StateListener<T> {
  void onStateChange(T state);
}
```

`Bloc` 維護目前狀態並通知監聽者。`increment` 與 `decrement` 會更新狀態並自動發出通知。

```java
public class Bloc implements ListenerManager<State> {

  private State currentState;
  private final List<StateListener<State>> listeners = new ArrayList<>();

  public Bloc() {
    this.currentState = new State(0);
  }

  @Override
  public void addListener(StateListener<State> listener) {
    listeners.add(listener);
    listener.onStateChange(currentState);
  }

  @Override
  public void removeListener(StateListener<State> listener) {
    listeners.remove(listener);
  }

  @Override
  public List<StateListener<State>> getListeners() {
    return Collections.unmodifiableList(listeners);
  }

  private void emitState(State newState) {
    currentState = newState;
    for (StateListener<State> listener : listeners) {
      listener.onStateChange(currentState);
    }
  }

  public void increment() {
    emitState(new State(currentState.value() + 1));
  }

  public void decrement() {
    emitState(new State(currentState.value() - 1));
  }
}
```

以下類別展示如何將 Bloc 整合到簡單的 Swing GUI，包含計數器、修改狀態的按鈕，以及動態啟用或停用監聽者的切換按鈕。

```java
public class Main {
  public static void main(String[] args) {
    BlocUi blocUi = new BlocUi();
    blocUi.createAndShowUi();
  }
}

public class BlocUi {
  public void createAndShowUi() {
    final Bloc bloc = new Bloc();
    JFrame frame = new JFrame("BloC example");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    JLabel counterLabel = new JLabel("Counter: 0", SwingConstants.CENTER);
    JButton decrementButton = new JButton("Decrement");
    JButton toggleListenerButton = new JButton("Disable Listener");
    JButton incrementButton = new JButton("Increment");
    frame.setLayout(new BorderLayout());
    frame.add(counterLabel, BorderLayout.CENTER);
    frame.add(incrementButton, BorderLayout.NORTH);
    frame.add(decrementButton, BorderLayout.SOUTH);
    frame.add(toggleListenerButton, BorderLayout.EAST);
    StateListener<State> stateListener = state -> counterLabel.setText("Counter: " + state.value());
    bloc.addListener(stateListener);
    toggleListenerButton.addActionListener(e -> {
      if (bloc.getListeners().contains(stateListener)) {
        bloc.removeListener(stateListener);
        toggleListenerButton.setText("Enable Listener");
      } else {
        bloc.addListener(stateListener);
        toggleListenerButton.setText("Disable Listener");
      }
    });
    incrementButton.addActionListener(e -> bloc.increment());
    decrementButton.addActionListener(e -> bloc.decrement());
    frame.setVisible(true);
  }
}
```

### 程式輸出

* **增加**：`Counter: 1`
* **減少**：`Counter: 0`
* **動態切換監聽者**：停用後計數器停止更新，重新啟用後恢復更新。

## 何時使用 Bloc 模式

* 想在 Java 應用程式中清楚分離商業邏輯與 UI。
* 需要依照狀態變更反應式更新 UI。
* 想避免控制器或呈現器直接耦合資料操作。
* 多個 UI 元素需要存取相同的商業邏輯。

## Bloc 模式的實際應用

* 需要即時 UI 更新的 Java 桌面應用程式。
* 將服務層與呈現層分離的後端 Java 框架。
* 必須維持一致邏輯、不受 UI 技術影響的跨平台應用程式。

## Bloc 模式的優點與取捨

優點：

* 移除直接商業邏輯，簡化 UI 元件。
* 隔離狀態與行為，提高可測試性。
* 集中資料流，鼓勵程式碼重複使用。
* 清楚分離關注點，提高可維護性。

取捨：

* 管理串流或觀察者時可能增加樣板程式碼。
* 必須妥善設計，避免形成單一龐大的元件。
* 需要一致的反應式程式設計實務才能發揮效果。

## 相關模式

* [觀察者](https://java-design-patterns.com/patterns/observer/)：Bloc 是觀察者模式的專門實作。
* [中介者](https://java-design-patterns.com/patterns/mediator/)：透過中央元件協調多個物件間的互動。
* [MVC](https://java-design-patterns.com/patterns/model-view-controller/)：同樣強調分離不同層的關注點。

## 參考資料與致謝

* [Bloc 架構（bloclibrary.dev）](https://bloclibrary.dev/architecture/)
* [Clean Architecture: A Craftsman's Guide to Software Structure and Design](https://amzn.to/3UoKkaR)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Event-Driven Programming in Java（Oracle）](https://www.oracle.com/java/)
* [Java Swing 文件（Oracle）](https://docs.oracle.com/javase/tutorial/uiswing/)