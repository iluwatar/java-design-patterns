---
title: Chain of responsibility
category: Behavioral
language: pt
tag:
 - Gang of Four
---

## Propósito

Evitar acoplar o remetente de uma *request* ao seu destinatário, dando a mais de um objeto a chance de
lidar com a solicitação. Encadeie os objeto recebidos e passe a solicitação ao longo da cadeia até um objeto
lidar com isso.

## Explicação

Exemplo do mundo real

> O Rei Orc dá ordens ao seu exército. O primeiro a receber a ordem é o comandante, então
> um oficial e depois um soldado. O comandante, o oficial e o soldado formam uma cadeia de responsabilidades.


Em outras palavras

> Ajuda a construir uma cadeia de objetos. Uma solicitação entra de uma extremidade e continua indo de um objeto
> para outro até encontrar um manipulador adequado.

Wikipedia diz

> Em orientação a objetos, Chain of Responsibility é um padrão cuja principal função é evitar a dependência 
> entre um objeto receptor e um objeto solicitante. Consiste em uma série de objetos receptores e de objetos
> de solicitação, onde cada objeto de solicitação possui uma lógica interna que separa quais são tipos de 
> objetos receptores que podem ser manipulados. O restante é passado para os próximos objetos de solicitação da cadeia.

**Exemplo de programação**

Traduzindo nosso exemplo com os orcs acima. Primeiro, nós temos a classe `Request`:

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

Em seguida, mostramos a hierarquia no request handler

```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

@Slf4j
public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Orc commander";
    }
}

// OrcOfficer and OrcSoldier are defined similarly as OrcCommander

```

O Rei Orc dá as ordens e forma a cadeia

```java
public class OrcKing {

  private List<RequestHandler> handlers;

  public OrcKing() {
    buildChain();
  }

  private void buildChain() {
    handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
  }

  public void makeRequest(Request req) {
    handlers
        .stream()
        .sorted(Comparator.comparing(RequestHandler::getPriority))
        .filter(handler -> handler.canHandleRequest(req))
        .findFirst()
        .ifPresent(handler -> handler.handle(req));
  }
}
```

O chain of responsibility em ação.

```java
var king = new OrcKing();
king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner"));
king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));
```

A saída do console.

```
Orc commander handling request "defend castle"
Orc officer handling request "torture prisoner"
Orc soldier handling request "collect tax"
```

## Diagrama de classe

![alt text](././etc/chain-of-responsibility.urm.png "Diagrama de classe Chain of Responsibility")

## Aplicabilidade

Usar Chain of Responsibility quando

* Mais de um objeto pode lidar com uma solicitação, e o manipulador não é conhecido a priori. O manipulador deve ser verificado automaticamente.
* Você deseja emitir uma solicitação para um dos vários objetos sem especificar o destinatário explicitamente.
* O conjunto de objetos que podem lidar com uma solicitação deve ser especificado dinamicamente.

## Exemplos do mundo real

* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
