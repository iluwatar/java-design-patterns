import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.iluwatar.sessionserver.LogoutHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class LogoutHandlerTest {

    private LogoutHandler logoutHandler;
    private Headers headers;
    private Map<String, Integer> sessions;
    private Map<String, Instant> sessionCreationTimes;

    @Mock
    private HttpExchange exchange;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessions = new HashMap<>();
        sessionCreationTimes = new HashMap<>();
        logoutHandler = new LogoutHandler(sessions, sessionCreationTimes);
        headers = new Headers();
        headers.add("Cookie", "sessionID=1234"); //Exchange object methods return Header Object but Exchange is mocked so Headers must be manually created
    }

    @Test
    public void testHandler_SessionNotExpired() throws IOException {

        //assemble
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        sessions.put("1234", 1); //Fake login details since LoginHandler isn't called
        sessionCreationTimes.put("1234", Instant.now()); //Fake login details since LoginHandler isn't called
        when(exchange.getRequestHeaders()).thenReturn(headers);
        when(exchange.getResponseBody()).thenReturn(outputStream);

        //act
        logoutHandler.handle(exchange);

        //assert
        String[] response = outputStream.toString().split("Session ID: ");
        Assertions.assertEquals("1234", response[1]);
        Assertions.assertFalse(sessions.containsKey(response));
        Assertions.assertFalse(sessionCreationTimes.containsKey(response));
    }

    @Test
    public void testHandler_SessionExpired() throws IOException {

        //assemble
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getRequestHeaders()).thenReturn(headers);
        when(exchange.getResponseBody()).thenReturn(outputStream);

        //act
        logoutHandler.handle(exchange);

        //assert
        String[] response = outputStream.toString().split("Session ID: ");
        Assertions.assertEquals("Session has already expired!", response[0]);
        Assertions.assertFalse(sessions.containsKey(response));
        Assertions.assertFalse(sessionCreationTimes.containsKey(response));
    }
}
