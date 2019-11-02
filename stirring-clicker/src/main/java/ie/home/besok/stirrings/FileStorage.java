package ie.home.besok.stirrings;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

public class FileStorage {

    private Path file;

    public FileStorage() throws IOException {
        this.file = Paths.get("data.log");
        if(!Files.exists(file)){
                Files.createFile(file);
        }
    }

    public void plus() {
        String line = LocalDateTime.now().toString()+System.lineSeparator();
        try {
            Files.write(file, line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> get() throws IOException {
        return Files.readAllLines(file);
    }

}
