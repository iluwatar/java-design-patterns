---
title: Execute Around
category: Idiom
language: en
tags:
 - Extensibility
---

## Intent

Execute Around idiom frees the user from certain actions that should always be executed before and 
after the business method. A good example of this is resource allocation and deallocation leaving 
the user to specify only what to do with the resource.

## Explanation

Real-world example

> A class needs to be provided for writing text strings to files. To make it easy for 
> the user, the service class opens and closes the file automatically. The user only has to 
> specify what is written into which file.       

In plain words

> Execute Around idiom handles boilerplate code before and after business method.  

[Stack Overflow](https://stackoverflow.com/questions/341971/what-is-the-execute-around-idiom) says

> Basically it's the pattern where you write a method to do things which are always required, e.g. 
> resource allocation and clean-up, and make the caller pass in "what we want to do with the 
> resource".

**Programmatic Example**

`SimpleFileWriter` class implements the Execute Around idiom. It takes `FileWriterAction` as a
constructor argument allowing the user to specify what gets written into the file.

```java
@FunctionalInterface
public interface FileWriterAction {
  void writeFile(FileWriter writer) throws IOException;
}

@Slf4j
public class SimpleFileWriter {
    public SimpleFileWriter(String filename, FileWriterAction action) throws IOException {
        LOGGER.info("Opening the file");
        try (var writer = new FileWriter(filename)) {
            LOGGER.info("Executing the action");
            action.writeFile(writer);
            LOGGER.info("Closing the file");
        }
    }
}
```

The following code demonstrates how `SimpleFileWriter` is used. `Scanner` is used to print the file
contents after the writing finishes.

```java
FileWriterAction writeHello = writer -> {
    writer.write("Gandalf was here");
};
new SimpleFileWriter("testfile.txt", writeHello);

var scanner = new Scanner(new File("testfile.txt"));
while (scanner.hasNextLine()) {
LOGGER.info(scanner.nextLine());
}
```

Here's the console output.

```
21:18:07.185 [main] INFO com.iluwatar.execute.around.SimpleFileWriter - Opening the file
21:18:07.188 [main] INFO com.iluwatar.execute.around.SimpleFileWriter - Executing the action
21:18:07.189 [main] INFO com.iluwatar.execute.around.SimpleFileWriter - Closing the file
21:18:07.199 [main] INFO com.iluwatar.execute.around.App - Gandalf was here
```

## Class diagram

![alt text](./etc/execute-around.png "Execute Around")

## Applicability

Use the Execute Around idiom when

* An API requires methods to be called in pairs such as open/close or allocate/deallocate.

## Credits

* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
