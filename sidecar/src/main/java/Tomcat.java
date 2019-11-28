import java.util.Map;

public class Tomcat implements SideCarInterface {
  private String port;

  public Tomcat(){this.port = "8080";}

  public Tomcat(Map<String, String> props) {
    port = props.getOrDefault("port","8080");
  }

  public Object call() {
    runTomcat();
    return this;
  }

  private void runTomcat(){
    System.out.println("Tomcat is using port :"+port);
  }
}
