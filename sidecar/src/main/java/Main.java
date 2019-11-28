import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Main {
  public static void main(String args[]) throws Exception {
    List<Callable> sidecars = new ArrayList<  >();
    sidecars.add(new SideCarImpl());
    sidecars.add(new Tomcat());
   for(int i = 0 ; i < sidecars.size() ; i++){
     sidecars.get(i).call();
   }
  }
}
