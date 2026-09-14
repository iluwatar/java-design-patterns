---
title: "Java 元件模式：以可重複使用的元件簡化複雜系統"
shortTitle: 元件
description: "了解 Java 元件設計模式，包括 ECS 架構、模組化與解耦，以及遊戲開發中的實際應用。"
categories: Structural
language: zh-TW
tag:
  - Game programming
  - Decoupling
  - Modularity
---

## 又稱為

* 實體－元件－系統（ECS）
* 元件－實體－系統（CES）
* 元件式架構（CBA）

## 元件設計模式的目的

元件模式將程式碼組織成可重複使用、可互換的元件，促進彈性、模組化與易維護性。它特別適合遊戲開發，讓實體可以動態組合不同的行為。

## 元件模式的詳細說明與真實世界範例

真實世界範例

> 假設一款遊戲同時有圖形與音效元件。若把兩者放在同一個 Java 類別中，長程式碼與不同團隊同時修改同一類別可能造成維護困難與衝突。元件模式將圖形與音效拆成個別元件，讓它們能獨立開發，提升可維護性與可擴充性。

簡單來說

> 元件模式提供單一屬性，讓多個物件都能存取它，而不必讓物件彼此建立關係。

架構圖

![元件架構圖](./etc/component-architecture-diagram.png)

## Java 元件模式的程式範例

`App` 建立兩個由可修改元件集合組成的物件，示範元件模式的使用方式。

```java
public final class App {
    public static void main(String[] args) {
        final var player = GameObject.createPlayer();
        final var npc = GameObject.createNpc();
        LOGGER.info("Player Update:");
        player.update(KeyEvent.KEY_LOCATION_LEFT);
        LOGGER.info("NPC Update:");
        npc.demoUpdate();
    }
}
```

`GameObject` 負責建立玩家與 NPC，也包含更新或修改物件元件資料的方法。

```java
public class GameObject {
    private final InputComponent inputComponent;
    private final PhysicComponent physicComponent;
    private final GraphicComponent graphicComponent;
    public String name;
    public int velocity = 0;
    public int coordinate = 0;

    public static GameObject createPlayer() {
        return new GameObject(new PlayerInputComponent(), new ObjectPhysicComponent(),
                new ObjectGraphicComponent(), "player");
    }

    public static GameObject createNpc() {
        return new GameObject(new DemoInputComponent(), new ObjectPhysicComponent(),
                new ObjectGraphicComponent(), "npc");
    }

    public void demoUpdate() {
        inputComponent.update(this);
        physicComponent.update(this);
        graphicComponent.update(this);
    }

    public void update(int e) {
        inputComponent.update(this, e);
        physicComponent.update(this);
        graphicComponent.update(this);
    }

    public void updateVelocity(int acceleration) { this.velocity += acceleration; }
    public void updateCoordinate() { this.coordinate += this.velocity; }
}
```

元件套件中的元件提供物件繼承各種領域的介面。以下 `PlayerInputComponent` 會依照使用者按鍵更新物件的速度。

```java
public class PlayerInputComponent implements InputComponent {
    private static final int WALK_ACCELERATION = 1;

    @Override
    public void update(GameObject gameObject, int e) {
        switch (e) {
            case KeyEvent.KEY_LOCATION_LEFT -> {
                gameObject.updateVelocity(-WALK_ACCELERATION);
                LOGGER.info(gameObject.getName() + " has moved left.");
            }
            case KeyEvent.KEY_LOCATION_RIGHT -> {
                gameObject.updateVelocity(WALK_ACCELERATION);
                LOGGER.info(gameObject.getName() + " has moved right.");
            }
            default -> {
                LOGGER.info(gameObject.getName() + "'s velocity is unchanged due to the invalid input");
                gameObject.updateVelocity(0);
            }
        }
    }
}
```

## 何時在 Java 中使用元件模式

* 遊戲與模擬系統中的實體（角色、物品等）需要動態組合能力或狀態。
* 系統需要高度模組化，且實體可能在執行期間改變行為，不適合依賴繼承階層。

## 元件模式的實際應用

元件模式適合遊戲開發與模擬，也適合角色和物品具有動態能力或狀態的系統。它讓實體能在執行期間改變行為，不必依賴繼承階層。

## 元件模式的優點與取捨

優點：

* 彈性與重複使用：元件能被不同實體共用，方便新增或修改功能。
* 解耦：減少遊戲實體狀態與行為的相依性，讓變更與維護更容易。
* 動態組合：實體可在執行期間新增或移除元件，提供高度彈性。

取捨：

* 可能增加系統架構的複雜度，尤其是元件間的相依與溝通。
* 間接呼叫與動態行為可能產生效能負擔，在高效能遊戲迴圈中尤其需要注意。

## 相關 Java 設計模式

* [裝飾者](https://java-design-patterns.com/patterns/decorator/)：同樣能動態增加責任，但不專注於遊戲實體。
* [享元](https://java-design-patterns.com/patterns/flyweight/)：可與元件模式搭配，在多個實體間共用元件以節省記憶體。
* [觀察者](https://java-design-patterns.com/patterns/observer/)：元件系統常用觀察者傳遞元件間的狀態變更。

## 參考資料與致謝

* [Game Programming Patterns](https://amzn.to/4cDRWhV)
* [Procedural Content Generation for Unity Game Development](https://amzn.to/3vBKCTp)
* [Unity in Action: Multiplatform Game Development in C#](https://amzn.to/3THO6vw)
* [Component（Game Programming Patterns）](https://gameprogrammingpatterns.com/component.html)
* [Component pattern - game programming series](https://www.youtube.com/watch?v=n92GBp2WMkg&ab_channel=Tutemic)