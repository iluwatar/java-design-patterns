---
title: Chain of responsibility
shortTitle: Chain of responsibility
category: Behavioral
language: zh
tag:
 - Gang of Four
---

## 目的
通过给多个对象一个处理请求的机会，避免请求的发送者和它的接收者耦合。串联接收对象并在链条中传递请求直到一个对象处理它。

## 解释

真实世界例子

> 兽王大声命令他的军队。最近响应的是指挥官，然后是军官，然后是士兵。指挥官，军官，士兵这里就形成了一个责任链。

通俗的说

> 它帮助构建一串对象。请求从一个对象中进入并结束然后进入到一个个对象中直到找到合适的处理器。

维基百科说

> 在面向对象设计中，责任链模式是一种由源命令对象和一系列处理对象组成的设计模式。每个处理对象包含了其定义的可处理的命令对象类型的逻辑。剩下的会传递给链条中的下一个处理对象。

**程序示例**

用上面的兽人来翻译我们的示例。首先我们有请求类

```java
public class Request {

  private final RequestType requestType;
  private final String requestDescription;
  private boolean handled;

  public Request(final RequestType requestType, final String requestDescription) {
    this.requestType = Objects.requireNonNull(requestType);
    this.requestDescription = Objects.requireNonNull(requestDescription);
  }

  public String getRequestDescription() { return requestDescription; }

  public RequestType getRequestType() { return requestType; }

  public void markHandled() { this.handled = true; }

  public boolean isHandled() { return this.handled; }

  @Override
  public String toString() { return getRequestDescription(); }
}

public enum RequestType {
  DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```

然后是请求处理器的层次结构

```java
@Slf4j
public abstract class RequestHandler {
  private final RequestHandler next;

  public RequestHandler(RequestHandler next) {
    this.next = next;
  }

  public void handleRequest(Request req) {
    if (next != null) {
      next.handleRequest(req);
    }
  }

  protected void printHandling(Request req) {
    LOGGER.info("{} handling request \"{}\"", this, req);
  }

  @Override
  public abstract String toString();
}

public class OrcCommander extends RequestHandler {
  public OrcCommander(RequestHandler handler) {
    super(handler);
  }

  @Override
  public void handleRequest(Request req) {
    if (req.getRequestType().equals(RequestType.DEFEND_CASTLE)) {
      printHandling(req);
      req.markHandled();
    } else {
      super.handleRequest(req);
    }
  }

  @Override
  public String toString() {
    return "Orc commander";
  }
}

// OrcOfficer和OrcSoldier的定义与OrcCommander类似

```

然后我们有兽王下达命令并形成链条

```java
public class OrcKing {
  RequestHandler chain;

  public OrcKing() {
    buildChain();
  }

  private void buildChain() {
    chain = new OrcCommander(new OrcOfficer(new OrcSoldier(null)));
  }

  public void makeRequest(Request req) {
    chain.handleRequest(req);
  }
}
```

然后这样使用它

```java
var king = new OrcKing();
king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle")); // Orc commander handling request "defend castle"
king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner")); // Orc officer handling request "torture prisoner"
king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax")); // Orc soldier handling request "collect tax"
```

## 类图
![alt text](./etc/chain-of-responsibility.urm.png "Chain of Responsibility class diagram")

## 适用性
使用责任链模式当

* 多于一个对象可能要处理请求，并且处理器并不知道一个优先级。处理器应自动确定。
* 你想对多个对象之一发出请求而无需明确指定接收者
* 处理请求的对象集合应该被动态指定时

## Java世界例子

* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
