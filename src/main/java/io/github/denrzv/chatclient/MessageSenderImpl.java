package io.github.denrzv.chatclient;


import java.io.PrintWriter;
import java.util.Scanner;

public class MessageSenderImpl implements MessageSender, Runnable {
    private final PrintWriter out;
    private final String username;
    private final Scanner scanner;
    private final ChatClient chatClient;

    public MessageSenderImpl(PrintWriter out, String username, Scanner scanner, ChatClient chatClient) {
        this.out = out;
        this.username = username;
        this.scanner = scanner;
        this.chatClient = chatClient;
    }

    @Override
    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        System.out.println("Для выхода из чата наберите /exit");
        sendMessage(username);
        while (!Thread.currentThread().isInterrupted()) {
            String input = scanner.nextLine();
            if (input.equals("/exit")) {
                out.println(username + ": " + input);
                break;
            }
            sendMessage(username + ": " + input);
        }
        chatClient.disconnect();
    }
}
