---
title: Active Object
category: Concurrency
language: vi
tag:
 - Performance
---

## Mục tiêu

Mẫu Đối Tượng Chủ Động (tiếng Anh: Active Object) chia tách việc thực thi hàm khỏi lời gọi hàm cho các đối tượng mà mỗi đối tượng nằm trong luồng điều khiển của chúng.
Mục tiêu là tận dụng khả năng xử lý đồng thời, bằng cách sử dụng phương thức bất đồng bộ và bộ lập lịch để xử lý các yêu cầu.

## Giải thích

Lớp hiện thực mẫu Đối Tượng Chủ Động sẽ bao gồm cơ chế tự đồng bộ (self-synchronization) nhưng không sử dụng đến phương thức/từ khóa 'synchronized'.

Ví dụ thực tế

> Tộc Orcs được biết đến với tính cách hoang dã và không thuần hóa được. Có thể coi như họ có một luồng điều khiển riêng do họ quyết định việc thực thi mà ta không can thiệp được.

Để hiện thực hóa một sinh vật mà nó sở hữu riêng cơ chế điều khiển luồng và chỉ để lộ ra giao diện lập trình (API), chúng ta có thể sử dụng mẫu Đối Tượng Chủ Động.

**Chương trình ví dụ**

```java
public abstract class ActiveCreature{
  private final Logger logger = LoggerFactory.getLogger(ActiveCreature.class.getName());

  private BlockingQueue<Runnable> requests;
  
  private String name;
  
  private Thread thread;

  public ActiveCreature(String name) {
    this.name = name;
    this.requests = new LinkedBlockingQueue<Runnable>();
    thread = new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            try {
              requests.take().run();
            } catch (InterruptedException e) { 
              logger.error(e.getMessage());
            }
          }
        }
      }
    );
    thread.start();
  }
  
  public void eat() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} is eating!",name());
          logger.info("{} has finished eating!",name());
        }
      }
    );
  }

  public void roam() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} has started to roam the wastelands.",name());
        }
      }
    );
  }
  
  public String name() {
    return this.name;
  }
}
```

Có thể thấy rằng mọi lớp kế thừa từ lớp `ActiveCreature` sẽ có riêng luồng điều khiển để gọi và thực thi phương thức.

Ví dụ lớp `Orc`:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }
  
}
```

Bây giờ, chúng ta có thể khởi tạo các sinh vật như Orcs, bảo chúng ăn (gọi hàm `eat()`) và tản bộ (gọi hàm `roam()`), và chúng sẽ thực thi trên luồng điều khiển của riêng chúng:

```java
  public static void main(String[] args) {  
    var app = new App();
    app.run();
  }
  
  @Override
  public void run() {
    ActiveCreature creature;
    try {
      for (int i = 0;i < creatures;i++) {
        creature = new Orc(Orc.class.getSimpleName().toString() + i);
        creature.eat();
        creature.roam();
      }
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
    Runtime.getRuntime().exit(1);
  }
```

## Sơ đồ lớp

![alt text](../../../active-object/etc/active-object.urm.png "Active Object class diagram")

## Hướng dẫn

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)