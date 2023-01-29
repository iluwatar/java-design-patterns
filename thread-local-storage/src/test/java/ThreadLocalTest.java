import com.iluwatar.WithThreadLocal;
import com.iluwatar.WithoutThreadLocal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void withoutThreadLocal() throws InterruptedException {
        int initialValue = 1234567890;

        int threadSize = 2;
        ExecutorService executor = Executors.newFixedThreadPool(threadSize);

        WithoutThreadLocal threadLocal = new WithoutThreadLocal(initialValue);
        for (int i = 0; i < threadSize; i++) {
            executor.submit(threadLocal);
        }
        executor.awaitTermination(3, TimeUnit.SECONDS);

        List<String> lines = outContent.toString().lines().toList();
        //Matches only first thread, the second has changed by first thread value
        Assertions.assertFalse(lines.stream()
                .allMatch(line -> line.endsWith(String.valueOf(initialValue))));
    }

    @Test
    public void withThreadLocal() throws InterruptedException {
        int initialValue = 1234567890;

        int threadSize = 2;
        ExecutorService executor = Executors.newFixedThreadPool(threadSize);

        WithThreadLocal threadLocal = new WithThreadLocal(ThreadLocal.withInitial(() -> initialValue));
        for (int i = 0; i < threadSize; i++) {
            executor.submit(threadLocal);
        }

        executor.awaitTermination(3, TimeUnit.SECONDS);
        threadLocal.remove();

        List<String> lines = outContent.toString().lines().toList();
        Assertions.assertTrue(lines.stream()
                .allMatch(line -> line.endsWith(String.valueOf(initialValue))));
    }
}
