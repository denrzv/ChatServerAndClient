package io.github.denrzv.chatclient.tests;

import io.github.denrzv.chatclient.ChatClient;
import io.github.denrzv.chatclient.ChatClientImpl;
import io.github.denrzv.common.Settings;
import io.github.denrzv.common.SettingsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Scanner;

public class TestChatClient {
    static ChatClient chatClient;

    @BeforeAll
    public static void setup() {
        Settings settings = new SettingsImpl("client_settings.json");
        chatClient = new ChatClientImpl(settings);
    }

    @Test
    @DisplayName("Тест на подключение к серверу")
    public void testChatClientConnect() throws IOException {
        Thread server = new Thread(new Runnable()
        {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(31313)){
                    serverSocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        server.start();
        chatClient.connect("User");
        Assertions.assertTrue(chatClient.isConnected());
    }

    @Test
    @DisplayName("Тест на успешное присвоение имени пользователя")
    public void testGetUsername() {
        String input = "User";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        String username = chatClient.getUsername(scanner);
        Assertions.assertEquals(username, input);
    }
}
