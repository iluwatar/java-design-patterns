/**
 * Created by Alexis on 28-Apr-17.
 */
public class App {
  public static void main(String[] args) {
    Guard guard = new Guard();

    if (guard instanceof Permission) {
      guard.enter();
    }
  }
}

