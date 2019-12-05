import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class ReaderApp extends SideCarApp implements Callable {
    private String path;


    @Override
    public void getProps(Map<String, String> props) {
        this.path= props.get("path");
    }

    @Override
    public Object call() throws Exception {
        try (Stream<Path> paths = Files.walk(Paths.get(this.path))) {
            paths.filter(Files::isRegularFile).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
