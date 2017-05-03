/**
 * Created by Alexis on 28-Apr-17.
 * With Marker interface idea is to make empty interface and extend it.
 * Basically it is just to identify the special objects from normal objects.
 * Like in case of serialization , objects that need to be serialized must implement serializable interface
 * (it is empty interface) and down the line writeObject() method must be checking
 * if it is a instance of serializable or not.
 * <p>
 * Marker interface vs annotation
 * Marker interfaces and marker annotations both have their uses,
 * neither of them is obsolete or always better then the other one.
 * If you want to define a type that does not have any new methods associated with it,
 * a marker interface is the way to go.
 * If you want to mark program elements other than classes and interfaces,
 * to allow for the possibility of adding more information to the marker in the future,
 * or to fit the marker into a framework that already makes heavy use of annotation types,
 * then a marker annotation is the correct choice
 */
public class App {

  /**
   * Program entry point
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    Guard guard = new Guard();
    Thief thief = new Thief();

    if (guard instanceof Permission) {
      guard.enter();
    } else {
      System.out.println("You have no permission to enter, please leave this area");
    }

    if (thief instanceof Permission) {
      thief.steal();
    } else {
      thief.doNothing();
    }
  }
}

