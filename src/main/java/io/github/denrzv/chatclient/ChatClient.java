package io.github.denrzv.chatclient;

import java.util.Scanner;

public interface ChatClient {
    void start();
    void connect(String username);
    void disconnect();
    boolean isConnected();
    String getUsername(Scanner scanner);
}
