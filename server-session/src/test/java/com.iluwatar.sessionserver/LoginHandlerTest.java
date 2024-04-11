import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.iluwatar.sessionserver.LoginHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginHandlerTest {

    private LoginHandler loginHandler;
    //private Headers headers;
    private Map<String, Integer> sessions;
    private Map<String, Instant> sessionCreationTimes;

    @Mock
    private HttpExchange exchange;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessions = new HashMap<>();
        sessionCreationTimes = new HashMap<>();
        loginHandler = new LoginHandler(sessions, sessionCreationTimes);
    }

    @Test
    public void testHandle() throws IOException {

        //assemble
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //Exchange object is mocked so OutputStream must be manually created
        when(exchange.getResponseHeaders()).thenReturn(new Headers()); //Exchange object is mocked so Header object must be manually created
        when(exchange.getResponseBody()).thenReturn(outputStream);

        //act
        loginHandler.handle(exchange);

        //assert
        String[] response = outputStream.toString().split("Session ID: ");
        assertEquals(sessions.entrySet().toArray()[0].toString().split("=1")[0], response[1]);
    }
}