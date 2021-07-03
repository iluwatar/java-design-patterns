---
layout: pattern
title: Model-View-ViewModel
folder: model-view-viewmodel
permalink: /patterns/model-view-viewmodel/zh
categories: Architectural
language: zh
tags:
 - Decoupling
---

## 也称为
模型-视图-绑定器

## 目的

应用"[Separation of Concerns](https://java-design-patterns.com/principles/#separation-of-concerns)" 将逻辑与 UI 组件分开，并允许开发人员在不影响逻辑的情况下处理 UI，反之亦然。

## 解释

维基百科说

> 模型-视图-视图模型 (MVVM) 是一种软件架构模式，它有助于将图形用户界面（视图）的开发（无论是通过标记语言还是 GUI 代码）
> 与业务逻辑的开发或返回分离-end 逻辑（模型），以便视图不依赖于任何特定的模型平台。

**程序示例**

Zkoss 实现：

> ViewModel 将保存业务逻辑并将数据从模型暴露给视图

```java
public class BookViewModel {
  @WireVariable
  private List<Book> bookList;
  private Book selectedBook;
  private BookService bookService = new BookServiceImpl();
  
  public Book getSelectedBook() {
    return selectedBook;
  }

  @NotifyChange("selectedBook")
  public void setSelectedBook(Book selectedBook) {
    this.selectedBook = selectedBook;
  }

  public List<Book> getBookList() {
    return bookService.load();
  }
  
  /** Deleting a book.
   */
  @Command
  @NotifyChange({"selectedBook","bookList"})
  public void deleteBook() {
    if (selectedBook != null) {
      getBookList().remove(selectedBook);
      selectedBook = null;
    }
}
```

> 视图将没有逻辑，只有 UI 元素

```xml
<zk>
<window title="List of Books" border="normal" width="600px" apply="org.zkoss.bind.BindComposer" viewModel="@id('vm') @init('com.iluwatar.model.view.viewmodel.BookViewModel')">
    <vbox hflex="true">
        <listbox model="@bind(vm.bookList)" selectedItem="@bind(vm.selectedBook)" height="400px" mold="paging">
            <listhead>
                <listheader label="Book Name"/>
                <listheader label="Author"/>               
            </listhead>
            <template name="model" var="book">
                <listitem >
                    <listcell label="@bind(book.name)"/>
                    <listcell label="@bind(book.author)"/>
                </listitem>
            </template>
        </listbox>
    </vbox>
    <toolbar>
        <button label="Delete" onClick="@command('deleteBook')" disabled="@load(empty vm.selectedBook)" />
    </toolbar>
    <hbox style="margin-top:20px" visible="@bind(not empty vm.selectedBook)">
		<vbox>
			<hlayout>
				Book Name : <label value="@bind(vm.selectedBook.name)" style="font-weight:bold"/>
			</hlayout>
			<hlayout>
				Book Author : <label value="@bind(vm.selectedBook.author)" style="font-weight:bold"/>
			</hlayout>
			<hlayout>
				Book Description : <label value="@bind(vm.selectedBook.description)" style="font-weight:bold"/>
			</hlayout>
		</vbox>
	</hbox>
</window>
</zk>
```

要部署示例，请转到 model-view-viewmodel 文件夹并运行：

* `mvn 干净安装`
* `mvn jetty:run -Djetty.http.port=9911`
* 打开浏览器地址：http://localhost:9911/model-view-viewmodel/

## 类图

![alt text](./etc/model-view-viewmodel.png "MVVM pattern class diagram")

## 适用性

* When looking for clean architecture, with better reusability, testability and maintainability.

## 教程

* [Zkoss Demo](https://www.zkoss.org/zkdemo/getting_started/mvvm)
* [Learn MVVM](https://www.learnmvvm.com/)
* [Android Developer CodeLabs](https://codelabs.developers.google.com/codelabs/android-databinding)

## 典型用例

* 安卓应用
* .NET 框架应用程序
* JavaScript 应用程序

##真实世界的例子

* ZK Framework [zkoss.org](https://www.zkoss.org/)
* KnockoutJS [knockoutjs.com](https://knockoutjs.com/)

## 结果

* John Gossman 批评了 MVVM 模式及其在特定用途中的应用，指出在创建简单的用户界面时，MVVM 可能是“矫枉过正”。
  对于较大的应用程序，他认为预先泛化视图模型可能很困难，并且大规模数据绑定会导致性能降低 - 
  参考：[MVVM-Wiki](https://en.wikipedia.org/wiki/Model% E2%80%93view%E2%80%93viewmodel)

* 为较大的应用程序设计 ViewModel 可能很困难。
* 对于复杂的数据绑定，调试可能很困难。

## 鸣谢


* [ZK MVVM](https://www.zkoss.org/wiki/ZK%20Developer's%20Reference/MVVM)
* [GeeksforGeeks  MVVM Intro](https://www.geeksforgeeks.org/introduction-to-model-view-view-model-mvvm/)
* [ZK MVVM Book](http://books.zkoss.org/zk-mvvm-book/9.5/)
* [Microsoft MVVM](https://docs.microsoft.com/en-us/archive/msdn-magazine/2009/february/patterns-wpf-apps-with-the-model-view-viewmodel-design-pattern)
