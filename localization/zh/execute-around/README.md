---
layout: pattern
title: Execute Around
folder: execute-around
permalink: /patterns/execute-around/zh
categories: Idiom
language: zh
tags:
- Extensibility
---

## 目的
Execute Around 习惯用法将用户从某些应始终在执行之前和之前执行的操作中解放出来
在商业方法之后。一个很好的例子是资源分配和解除分配离开
用户只指定如何处理资源。

## 解使

案例
> 我们需要提供一个可以用来将文本字符串写入文件的类。为了方便
> 我们让我们的服务类自动打开和关闭文件的用户，用户只需要
> 指定写入哪个文件的内容。

简单来说
> Execute Around idiom 在业务方法之前和之后处理样板代码。

[Stack Overflow](https://stackoverflow.com/questions/341971/what-is-the-execute-around-idiom) says

> 基本上这是你编写一个方法来做总是需要的事情的模式，例如
> 资源分配和清理，并让调用者传入“我们想用
> 资源”。

**Programmatic Example**

让我们介绍一下我们的文件编写器类
```java
@FunctionalInterface
public interface FileWriterAction {

  void writeFile(FileWriter writer) throws IOException;

}

public class SimpleFileWriter {

  public SimpleFileWriter(String filename, FileWriterAction action) throws IOException {
    try (var writer = new FileWriter(filename)) {
      action.writeFile(writer);
    }
  }
}
```

要使用文件编写器，需要以下代码。

```java
    FileWriterAction writeHello = writer -> {
      writer.write("Hello");
      writer.append(" ");
      writer.append("there!");
    };
    new SimpleFileWriter("testfile.txt", writeHello);
```

## 类图

![alt text](./etc/execute-around.png "Execute Around")

## 适用性

* 您使用的 API 需要成对调用方法，例如 open/close 或 分配/解除分配。

## 鸣谢

* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
