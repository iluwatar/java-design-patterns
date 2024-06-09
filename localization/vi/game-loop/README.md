---
title: Game Loop (Vòng lặp trò chơi)
category: Behavioral
language: vi
tag:
    - Concurrency
    - Event-driven
    - Game programming
    - Performance
---

## Còn được gọi là

* Game Cycle (Chu kỳ trò chơi)
* Main Game Loop (Vòng lặp trò chơi chính)

## Mục tiêu

Mẫu thiết kế Game Loop nhằm thực thi liên tục của một trò chơi, trong đó mỗi chu kỳ vòng lặp xử lý đầu vào, cập nhật trạng thái trò chơi, và hiển thị trạng thái trò chơi lên màn hình, duy trì trải nghiệm chơi mượt mà và tương tác.

## Giải thích

Ví dụ thực tế

> Game loop là quá trình chính của tất cả các luồng hiển thị trò chơi. Nó hiện diện trong tất cả các trò chơi hiện đại. Nó điều khiển việc xử lý đầu vào, cập nhật trạng thái nội bộ, hiển thị, xử lý trí tuệ nhân tạo và tất cả các quá trình khác.

Nói một cách đơn giản

> Mẫu Game Loop đảm bảo rằng thời gian trò chơi thực thi với tốc độ bằng nhau trong tất cả các cấu hình phần cứng khác nhau.

Theo Wikipedia

> Thành phần trung tâm của bất kỳ trò chơi nào, từ góc độ lập trình, đều là game loop. Game loop cho phép trò chơi chạy trơn tru bất kể đầu vào của người dùng hoặc thiếu đầu vào.

**Ví dụ lập trình**

Hãy bắt đầu với một thứ gì đó đơn giản. Đây là lớp `Bullet` (Đạn). Đạn sẽ di chuyển trong trò chơi của chúng ta. Để dễ hình dung, ta cho nó có vị trí 1 chiều.

```java
public class Bullet {

    private float position;

    public Bullet() {
        position = 0.0f;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }
}
```

`GameController`  có trách nhiệm di chuyển các đối tượng trong trò chơi, bao gồm cả đạn đã đề cập bên trên.

```java
public class GameController {

    protected final Bullet bullet;

    public GameController() {
        bullet = new Bullet();
    }

    public void moveBullet(float offset) {
        var currentPosition = bullet.getPosition();
        bullet.setPosition(currentPosition + offset);
    }

    public float getBulletPosition() {
        return bullet.getPosition();
    }
}
```

Bây giờ chúng ta tạo ra một vòng lặp trò chơi (game loop). Hoặc thực tế trong ví dụ minh họa này, chúng ta có loại 3 vòng lặp trò chơi khác nhau. Hãy xem lớp cha `GameLoop` trước.

```java
public enum GameStatus {

    RUNNING,
    STOPPED
}

public abstract class GameLoop {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected volatile GameStatus status;

    protected GameController controller;

    private Thread gameThread;

    public GameLoop() {
        controller = new GameController();
        status = GameStatus.STOPPED;
    }

    public void run() {
        status = GameStatus.RUNNING;
        gameThread = new Thread(this::processGameLoop);
        gameThread.start();
    }

    public void stop() {
        status = GameStatus.STOPPED;
    }

    public boolean isGameRunning() {
        return status == GameStatus.RUNNING;
    }

    protected void processInput() {
        try {
            var lag = new Random().nextInt(200) + 50;
            Thread.sleep(lag);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    protected void render() {
        var position = controller.getBulletPosition();
        logger.info("Current bullet position: " + position);
    }

    protected abstract void processGameLoop();
}
```

Đây là cách hiện thực đầu tiên, `FrameBasedGameLoop` (Vòng lặp trò chơi dựa trên khung hình):

```java
public class FrameBasedGameLoop extends GameLoop {

    @Override
    protected void processGameLoop() {
        while (isGameRunning()) {
            processInput();
            update();
            render();
        }
    }

    protected void update() {
        controller.moveBullet(0.5f);
    }
}
```

Cuối cùng, chúng ta sẽ thực thi tất cả các vòng lặp trò chơi.

```java
    try {
      LOGGER.info("Start frame-based game loop:");
      var frameBasedGameLoop = new FrameBasedGameLoop();
      frameBasedGameLoop.run();
      Thread.sleep(GAME_LOOP_DURATION_TIME);
      frameBasedGameLoop.stop();
      LOGGER.info("Stop frame-based game loop.");

      LOGGER.info("Start variable-step game loop:");
      var variableStepGameLoop = new VariableStepGameLoop();
      variableStepGameLoop.run();
      Thread.sleep(GAME_LOOP_DURATION_TIME);
      variableStepGameLoop.stop();
      LOGGER.info("Stop variable-step game loop.");

      LOGGER.info("Start fixed-step game loop:");
      var fixedStepGameLoop = new FixedStepGameLoop();
      fixedStepGameLoop.run();
      Thread.sleep(GAME_LOOP_DURATION_TIME);
      fixedStepGameLoop.stop();
      LOGGER.info("Stop variable-step game loop.");
      
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
    }
```

Kết quả chương trình:

```Start frame-based game loop:
Current bullet position: 0.5
Current bullet position: 1.0
Current bullet position: 1.5
Current bullet position: 2.0
Current bullet position: 2.5
Current bullet position: 3.0
Current bullet position: 3.5
Current bullet position: 4.0
Current bullet position: 4.5
Current bullet position: 5.0
Current bullet position: 5.5
Current bullet position: 6.0
Stop frame-based game loop.
Start variable-step game loop:
Current bullet position: 6.5
Current bullet position: 0.038
Current bullet position: 0.084
Current bullet position: 0.145
Current bullet position: 0.1805
Current bullet position: 0.28
Current bullet position: 0.32
Current bullet position: 0.42549998
Current bullet position: 0.52849996
Current bullet position: 0.57799995
Current bullet position: 0.63199997
Current bullet position: 0.672
Current bullet position: 0.778
Current bullet position: 0.848
Current bullet position: 0.8955
Current bullet position: 0.9635
Stop variable-step game loop.
Start fixed-step game loop:
Current bullet position: 0.0
Current bullet position: 1.086
Current bullet position: 0.059999995
Current bullet position: 0.12999998
Current bullet position: 0.24000004
Current bullet position: 0.33999994
Current bullet position: 0.36999992
Current bullet position: 0.43999985
Current bullet position: 0.5399998
Current bullet position: 0.65999967
Current bullet position: 0.68999964
Current bullet position: 0.7299996
Current bullet position: 0.79999954
Current bullet position: 0.89999944
Current bullet position: 0.98999935
Stop variable-step game loop.
```

## Class diagram

![alt text](../../../game-loop/etc/game-loop.urm.png "Game Loop pattern class diagram")

## Ứng dụng

Mẫu Game Loop được áp dụng trong mô phỏng thời gian thực và trò chơi, nơi trạng thái cần được cập nhật liên tục và nhất quán để đáp ứng đầu vào của người dùng và các sự kiện khác.

## Các trường hợp ứng dụng đã biết

* Trò chơi video, cả 2D và 3D, trên các nền tảng khác nhau.
* Mô phỏng thời gian thực yêu cầu tỷ lệ khung hình ổn định để cập nhật logic và hiển thị.

## Hậu quả

Ưu điểm:

* Đảm bảo trò chơi thực thi mượt mà và nhất quán.
* Tạo điều kiện thuận lợi cho việc đồng bộ giữa trạng thái trò chơi, đầu vào người dùng và hiển thị lên màn hình.
* Cung cấp một cấu trúc rõ ràng để các nhà phát triển trò chơi quản lý chuyển động và thời gian trong trò chơi.

Nhược điểm:

* Có thể dẫn đến vấn đề hiệu suất nếu vòng lặp không được quản lý tốt, đặc biệt là trong các hàm cập nhật hoặc hiển thị tốn nhiều tài nguyên.
* Khó khăn trong việc quản lý tỷ lệ khung hình (frame rates) khác nhau trên các phần cứng khác nhau.

## Các mẫu liên quan

* [State](https://java-design-patterns.com/patterns/state/): Thường được sử dụng trong vòng lặp trò chơi để quản lý các trạng thái khác nhau của trò chơi (ví dụ: menu, đang chơi, tạm dừng). Mối quan hệ nằm ở việc quản lý hành vi cụ thể của trạng thái và chuyển đổi trạng thái một cách mượt mà trong vòng lặp trò chơi.
* [Observer](https://java-design-patterns.com/patterns/observer/): Hữu ích trong vòng lặp trò chơi để xử lý sự kiện, nơi các thực thể trò chơi có thể đăng ký và phản ứng với các sự kiện (ví dụ: va chạm, ghi điểm).

## Tham khảo

* [Game Programming Patterns - Game Loop](http://gameprogrammingpatterns.com/game-loop.html)
* [Game Programming Patterns](https://www.amazon.com/gp/product/0990582906/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0990582906&linkId=1289749a703b3fe0e24cd8d604d7c40b)
* [Game Engine Architecture, Third Edition](https://www.amazon.com/gp/product/1138035459/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1138035459&linkId=94502746617211bc40e0ef49d29333ac)
* [Real-Time Collision Detection](https://amzn.to/3W9Jj8T)