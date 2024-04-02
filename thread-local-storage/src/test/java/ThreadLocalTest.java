/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

        for (int i = 0; i < threadSize; i++) {
          //Create independent thread
          WithoutThreadLocal threadLocal = new WithoutThreadLocal(initialValue);
          executor.submit(threadLocal);
        }
        executor.awaitTermination(3, TimeUnit.SECONDS);
        List<String> lines = outContent.toString().lines().toList();

        Assertions.assertTrue(lines.stream()
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
