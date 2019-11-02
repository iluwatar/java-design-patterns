package ie.home.besok.stirrings;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FileStorageTest {

    @Test
    public void fsTest() throws IOException {
        FileStorage fs = new FileStorage();
        List<String> arrs = fs.get();
        int oldSize = arrs.size();
        fs.plus();
        fs.plus();
        fs.plus();
        fs.plus();
        arrs = fs.get();
        int newSize = arrs.size();
        assertEquals(4, newSize - oldSize);
    }
}