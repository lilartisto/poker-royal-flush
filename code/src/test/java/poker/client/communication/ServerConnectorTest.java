package poker.client.communication;

import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerConnectorTest {

    private ServerSocket server;
    private int port;

    @BeforeEach
    public void setUp() {
        port = 5000;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void closeServer() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldThrowExceptionWhenHostIsIncorrect() {
        String host = "shouldThrowException";
        UnknownHostException exception = assertThrows(
                UnknownHostException.class,
                () -> {
                    ServerConnector.init(host, port + 1, "test");
                });
        assertEquals(host, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPortIsIncorrect() {
        ConnectException exception = assertThrows(
                ConnectException.class,
                () -> {
                    ServerConnector.init(server.getInetAddress().getHostAddress(), port + 1, "test");
                });
        assertEquals("Connection refused: connect", exception.getMessage());
    }

    @Test
    public void shouldSendConnectMsgToServerWhenClientTryConnectToServer() {
        try {
            Thread thread = new Thread(() -> {
                try {
                    ServerConnector.init(server.getInetAddress().getHostAddress(), port, "test");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();

            String actualConnectMsg = listen(server.accept().getInputStream());
            String expectedConnectMsg = "{\"name\": \"connect\",\"nickname\": \"test\"}";

            assertEquals(expectedConnectMsg, actualConnectMsg);
        } catch (Exception e) {
            assert false;
        }
    }

    private String listen(InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}