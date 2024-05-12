---
title: Model-View-ViewModel
category: Architectural
language: en
tag:
    - Architecture
    - Data binding
    - Decoupling
    - Presentation
    - Scalability
---

## Also known as

* MVVM

## Intent

The intent of MVVM is to provide a clear [separation of concerns](https://java-design-patterns.com/principles/#separation-of-concerns) between the UI logic, the presentation logic, and the business logic by dividing the application into three interconnected components: Model, View, and ViewModel.

## Explanation

Real-world example

> Consider a real-world analogous example of the MVVM pattern similar to organizing a cooking show. In this scenario:
>
> - **Model:** Represents the recipe itself, which includes the ingredients and the steps needed to cook the dish. The model is purely about the data and rules for preparing the dish but does not concern itself with how this information is presented to the audience.
>
> - **View:** Is akin to the kitchen set where the cooking show is filmed, including all the visual elements like the layout of the kitchen, the placement of ingredients, and the cookware. The view is responsible for the visual presentation and how the audience sees the cooking process.
>
> - **ViewModel:** Acts like the script for the cooking show, where it interprets the recipe (model) and organizes the flow of the show. It tells the chef (view) what to display next, when to add ingredients, and how to respond to changes like substituting an ingredient. The ViewModel bridges the gap between the technical details of the recipe and the chef's presentation, ensuring the audience understands each step without delving into the complexities of the recipe itself.
>
> In this example, the ViewModel allows the chef to focus on cooking and interacting with the audience, while the underlying recipe remains unchanged, promoting a clear separation of concerns.

In plain words

> The Model-View-ViewModel (MVVM) design pattern separates an application into three distinct components: the Model, which holds the data and business logic; the View, which displays the user interface; and the ViewModel, which acts as an intermediary to bind data from the Model to the View, facilitating a clear separation of concerns and easier maintenance and testing of user interfaces.

Wikipedia says

> Model–view–viewmodel (MVVM) is a software architectural pattern that facilitates the separation of the development of the graphical user interface (the view) – be it via a markup language or GUI code – from the development of the business logic or back-end logic (the model) so that the view is not dependent on any specific model platform. 

**Programmatic Example**

Zkoss implementation:

> ViewModel will hold the business logic and expose the data from model to View

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

> View will have no logic, only UI elements

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

To deploy the example, go to model-view-viewmodel folder and run:

* `mvn clean install`
* `mvn jetty:run -Djetty.http.port=9911`
* Open browser to address: http://localhost:9911/model-view-viewmodel/

## Class diagram

![MVVM](./etc/model-view-viewmodel.png "MVVM pattern class diagram")

## Applicability

* MVVM is applicable in applications requiring a clear separation between the user interface and the underlying business logic, especially in large-scale, data-driven applications where UI and business logic change independently.

## Tutorials

* [Zkoss Demo](https://www.zkoss.org/zkdemo/getting_started/mvvm)
* [Data Binding in Android](https://developer.android.com/codelabs/android-databinding#0)
* [ZK MVVM](https://www.zkoss.org/wiki/ZK%20Developer's%20Reference/MVVM)
* [GeeksforGeeks  MVVM Intro](https://www.geeksforgeeks.org/introduction-to-model-view-view-model-mvvm/)
* [ZK MVVM Book](http://books.zkoss.org/zk-mvvm-book/9.5/)
* [Microsoft MVVM](https://docs.microsoft.com/en-us/archive/msdn-magazine/2009/february/patterns-wpf-apps-with-the-model-view-viewmodel-design-pattern)

## Known uses

* Widely used in JavaFX applications for desktop interfaces.
* Utilized in Android development with libraries like DataBinding and LiveData for reactive UI updates.
* ZK Framework [zkoss.org](https://www.zkoss.org/)
* KnockoutJS [knockoutjs.com](https://knockoutjs.com/)

## Consequences

Benefits:

* Improved testability due to decoupling of business and presentation logic.
* Easier maintenance and modification of the user interface without affecting the underlying data model.
* Enhanced reusability of the ViewModel across different views if designed generically.

Trade-offs:

* Increased complexity in small applications where simpler patterns might suffice.
* Learning curve associated with understanding and applying the pattern correctly.

## Related Patterns

* [MVC (Model-View-Controller)](https://java-design-patterns.com/patterns/model-view-controller/): MVVM can be seen as a derivative of MVC with a stronger emphasis on binding and decoupling, where the ViewModel acts as an intermediary unlike the controller in MVC.
* [MVP (Model-View-Presenter)](https://java-design-patterns.com/patterns/model-view-presenter/): Similar to MVVM but with a focus on the presenter handling the UI logic, making MVVM's ViewModel more passive in terms of direct UI manipulation.

## Credits

* [Android Programming: The Big Nerd Ranch Guide](https://amzn.to/3wBGG5o)
* [Pro JavaFX 8: A Definitive Guide to Building Desktop, Mobile, and Embedded Java Clients](https://amzn.to/4a8qcQ1)
