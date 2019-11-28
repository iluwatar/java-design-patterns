import java.util.concurrent.Callable;

public class SideCarImpl implements Callable {

  public Object call() throws Exception {
    System.out.println("Nitzi The ...");
    return null;
  }
}
