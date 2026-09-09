---
title: "Polling Publisher-Subscriber Pattern in Java: Mastering Asynchronous Messaging Elegantly"
shortTitle: Polling Pub/Sub
description: "Learn how to implement a Polling Publisher-Subscriber system in Java using Spring Boot and Kafka. Explore the architecture, real-world analogies, and benefits of asynchronous communication with clean code examples."
category: Architectural
language: pt
tag:
  - Spring Boot
  - Kafka
  - Microservices
  - Asynchronous Messaging
  - Decoupling
---

## Também conhecido como

* Event-Driven Architecture
* Asynchronous Pub/Sub Pattern
* Message Queue-Based Polling System

## Propósito

O padrão Polling Publisher-Subscriber desacopla os produtores de dados dos consumidores ao permitir uma comunicação assíncrona e orientada a mensagens. Um serviço consulta periodicamente (poll) uma fonte de dados e publica mensagens em um message broker (por exemplo, Kafka), que são então consumidas por um ou mais serviços assinantes.

## Explicação

### Exemplo do mundo real

> Uma agência de notícias consulta constantemente as últimas atualizações. Assim que recebe novas informações, ela as publica em diferentes veículos (TV, jornais, aplicativos). Cada veículo consome e exibe as atualizações de forma independente.

### Em outras palavras

> Um serviço verifica regularmente se há atualizações (polling) e envia mensagens para o Kafka. Outro serviço escuta o Kafka e processa as mensagens de forma assíncrona.

### De acordo com a Wikipédia

> Este padrão se assemelha muito ao [modelo Publish–subscribe](https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern), no qual as mensagens são enviadas pelos publicadores e recebidas pelos assinantes sem que eles se conheçam.

### Fluxo da arquitetura

```
+------------+      +--------+      +-------------+
|  Publisher | ---> | Kafka  | ---> | Subscriber  |
+------------+      +--------+      +-------------+
```

## Exemplo Programático (Spring Boot + Kafka)

### Serviço Publisher

- Usa o `@Scheduled` do Spring para consultar dados periodicamente.
- Publica dados em um tópico do Kafka.
- Opcionalmente expõe uma API REST para publicação manual de dados.

```java
@Scheduled(fixedRate = 5000)
public void pollAndPublish() {
    String data = pollingService.getLatestData();
    kafkaTemplate.send("updates-topic", data);
}
```

### Serviço Subscriber

- Escuta um tópico do Kafka usando `@KafkaListener`.
- Processa as mensagens de forma assíncrona.

```java
@KafkaListener(topics = "updates-topic")
public void processUpdate(String message) {
    log.info("Received update: {}", message);
    updateProcessor.handle(message);
}
```

## Quando usar o padrão Polling Publisher-Subscriber

Use esse padrão quando:

* Não é possível o produtor enviar dados em tempo real (push).
* Deseja-se um baixo acoplamento entre produtores e consumidores.
* É necessário processamento de eventos assíncrono e escalável.
* Você está construindo uma arquitetura de microsserviços orientada a eventos.

## Aplicações do mundo real

* Painéis de relatórios em tempo real
* Agregadores de health check para sistemas distribuídos
* Processamento de telemetria de IoT
* Sistemas de notificação e alerta

## Benefícios e desafios do padrão Polling Pub/Sub

### Benefícios

* Baixo acoplamento entre serviços
* Arquitetura assíncrona e escalável
* Tolerante a falhas, com persistência de mensagens no Kafka
* Fácil de estender com novos consumidores ou publicadores

### Desafios

* O polling introduz latência entre a geração e o consumo dos dados
* Requer o gerenciamento e a configuração do Kafka (ou de outro broker)
* Implantação e infraestrutura ligeiramente mais complexas

## Padrões relacionados

* [Observer Pattern](https://java-design-patterns.com/patterns/observer/)
* [Mediator Pattern](https://java-design-patterns.com/patterns/mediator/)
* [Message Queue Pattern](https://java-design-patterns.com/patterns/event-queue/)

## Referências e Créditos

* [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
* [Spring Kafka Documentation](https://docs.spring.io/spring-kafka)
* [Spring Scheduled Tasks](https://www.baeldung.com/spring-scheduled-tasks)
* [Spring Kafka Tutorial – Baeldung](https://www.baeldung.com/spring-kafka)
* Inspired by: [iluwatar/java-design-patterns](https://github.com/iluwatar/java-design-patterns)
