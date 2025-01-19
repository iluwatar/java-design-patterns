# Binding Properties Pattern

## Intent
Synchronize state between objects with automatic updates, enabling one-way or two-way data binding.

## Explanation

This pattern is used to ensure that changes in one component (like a data model) are reflected in another component (like a UI element) automatically, and vice versa.

### Participants
1. **ObservableProperty**: Holds the value and notifies observers when it changes.
2. **Observer**: Defines the interface for objects that respond to observable property changes.
3. **TextView**: Implements the `Observer` interface and simulates a UI component.

### Applicability
- Synchronizing state between multiple objects.
- Minimizing boilerplate code for updates.

### Example Code
```java

public class ObservableProperty<T> {
  private T value;
  private final List<Observer<T>> observers = new ArrayList<>();
  private boolean isUpdating = false;

  public ObservableProperty(T initialValue) {
    this.value = initialValue;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    if (isUpdating || this.value == value || (this.value != null && this.value.equals(value))) {
      return;
    }
    this.value = value;
    notifyObservers();
  }

  public void addObserver(Observer<T> observer) {
    observers.add(observer);
    observer.bind(this);
  }

  public void removeObserver(Observer<T> observer) {
    observers.remove(observer);
    observer.unbind();
  }

  private void notifyObservers() {
    for (Observer<T> observer : observers) {
      isUpdating = true;
      observer.update(value);
      isUpdating = false;
    }
  }
}
// TextView reacts to the change
