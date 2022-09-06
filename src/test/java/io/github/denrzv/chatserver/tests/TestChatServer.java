package io.github.denrzv.chatserver.tests;

import io.github.denrzv.chatserver.ChatServer;
import io.github.denrzv.chatserver.ChatServerImpl;
import io.github.denrzv.common.SettingsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TestChatServer {
    static ChatServer server;
    static int port;

    @BeforeAll
    public static void setup() {
        server = new ChatServerImpl(new SettingsImpl("server_settings.json"));
        port = 31313;
        Thread serverThread = new Thread(() -> server.start());
        serverThread.start();
        System.out.println(server.isRunning());
    }

    @Test
    @DisplayName("Запуск сервера")
    public void testChatServerStart() throws IOException, InterruptedException {
        Thread.sleep(3000);
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            System.out.println(socket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Assertions.assertTrue(socket.isConnected());
            socket.close();
        }
    }

    @Test
    @DisplayName("Остановка сервера")
    public void testChatServerStop() {
        server.stop();
        Socket socket;
        boolean result = false;
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            result = socket.isConnected();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Тест на невалидные значения")
    public void shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new ChatServerImpl(null));
    }
}
