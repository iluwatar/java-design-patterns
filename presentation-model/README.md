---
title: Presentation Model
category: Architectural
language: en
tag:
    - Decoupling
    - Encapsulation
    - Presentation
    - Testing
---

## Also known as

* Application Model

## Intent

To separate the logic of the user interface (UI) from the business logic by creating a model that represents the data and behavior of the UI independently.

## Explanation

Real-world example

> An analogous real-world example of the Presentation Model design pattern is the relationship between a scriptwriter, an actor, and a director in a theater production. The scriptwriter creates the script (analogous to the business logic), which the actor then interprets and performs on stage (analogous to the user interface). The director acts as the intermediary, ensuring that the actor's performance aligns with the script and the vision of the play (similar to the Presentation Model coordinating the UI and the business logic). This separation allows the script to be rewritten without changing the actor's techniques or the director's interpretation, ensuring flexibility and maintainability.

In plain words

> The Presentation Model design pattern separates the UI logic from the business logic by creating an intermediate model that represents the data and behavior of the UI independently, enhancing testability, maintainability, and flexibility.

**Programmatic example**

The Presentation Model design pattern is a pattern that separates the responsibility of managing the state and behavior of the GUI in a separate model class. This model class is not tied to the view and can be used to test the GUI behavior independently from the GUI itself.

Let's take a look at the code provided and see how it implements the Presentation Model pattern.

First, we have the `Album` class. This class represents the data model in our application. It contains properties like `title`, `artist`, `isClassical`, and `composer`.

```java
@Setter
@Getter
@AllArgsConstructor
public class Album {
    private String title;
    private String artist;
    private boolean isClassical;
    private String composer;
}
```

Next, we have the `DisplayedAlbums` class. This class is responsible for managing a collection of `Album` objects.

```java
@Slf4j
@Getter
public class DisplayedAlbums {
    private final List<Album> albums;

    public DisplayedAlbums() {
        this.albums = new ArrayList<>();
    }

    public void addAlbums(final String title,
                          final String artist, final boolean isClassical,
                          final String composer) {
        if (isClassical) {
            this.albums.add(new Album(title, artist, true, composer));
        } else {
            this.albums.add(new Album(title, artist, false, ""));
        }
    }
}
```

The `PresentationModel` class is where the Presentation Model pattern is implemented. This class is responsible for managing the state and behavior of the GUI. It contains a reference to the `DisplayedAlbums` object and provides methods for interacting with the selected album.

```java
public class PresentationModel {
    
    private final DisplayedAlbums data;
    private int selectedAlbumNumber;
    private Album selectedAlbum;

    public PresentationModel(final DisplayedAlbums dataOfAlbums) {
        this.data = dataOfAlbums;
        this.selectedAlbumNumber = 1;
        this.selectedAlbum = this.data.getAlbums().get(0);
    }

    // other methods...
}
```

The `App` class is the entry point of the application. It creates a `View` object and calls its `createView` method to start the GUI.

```java
public final class App {
    public static void main(final String[] args) {
        var view = new View();
        view.createView();
    }
}
```

In this example, the `PresentationModel` class is the Presentation Model. It separates the GUI's state and behavior from the `View` class, allowing the GUI to be tested independently from the actual GUI components.

## Applicability

Use the Presentation Model Pattern when

* Use when you want to decouple the UI from the underlying business logic to allow for easier testing, maintenance, and the ability to support multiple views or platforms.
* Ideal for applications where the UI changes frequently or needs to be different across various platforms while keeping the core logic intact.

## Known Uses

* JavaFX applications: Utilizing JavaFX properties and bindings to create a clear separation between the UI and business logic.
* Swing applications: Employing a Presentation Model to decouple Swing components from the application logic, enhancing testability and flexibility.
* Android apps: Implementing MVVM architecture using ViewModel classes to manage UI-related data and lifecycle-aware components.

## Consequences

Benefits:

* Decoupling: Enhances [separation of concerns](https://java-design-patterns.com/principles/#separation-of-concerns), making the system more modular and testable.
* Testability: Facilitates unit testing of UI logic without the need for actual UI components.
* Maintainability: Simplifies maintenance by isolating changes to the UI or business logic.
* Flexibility: Supports multiple views for the same model, making it easier to adapt the UI for different platforms.

Trade-offs:

* Complexity: Can introduce additional layers and complexity in the application architecture.
* Learning Curve: May require a deeper understanding of binding mechanisms and state management.

## Related Patterns

* [Model-View-Controller (MVC)](https://java-design-patterns.com/patterns/model-view-controller/): Similar in that it separates concerns, but Presentation Model encapsulates more of the view logic.
* [Model-View-Presenter (MVP)](https://java-design-patterns.com/patterns/model-view-presenter/): Another UI pattern focusing on separation of concerns, but with a different interaction model.
* [Observer](https://java-design-patterns.com/patterns/observer/): Often used within the Presentation Model to update the UI when the model changes.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Presentation Model (Martin Fowler)](https://martinfowler.com/eaaDev/PresentationModel.html)
