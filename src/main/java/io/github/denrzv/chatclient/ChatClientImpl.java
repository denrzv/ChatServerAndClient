package io.github.denrzv.chatclient;

import io.github.denrzv.common.Logger;
import io.github.denrzv.common.LoggerImpl;
import io.github.denrzv.common.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClientImpl implements ChatClient {
    private final Settings settings;
    private Thread messageSenderThread;
    private Thread messageReceiverThread;
    private final Logger logger;
    private boolean isConnected;


    public ChatClientImpl(Settings settings) {
        this.settings = settings;
        this.logger = new LoggerImpl((String) settings.getSettingByName("logFileName"));
        logger.addLog("Запуск клиента");
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String username = getUsername(scanner);
        connect(username);
    }

    @Override
    public void connect(String username) {
        String host = (String) settings.getSettingByName("host");
        long port = (Long) settings.getSettingByName("port");

        try {
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket(host, (int) port);
            isConnected = true;
            logger.addLog("Подключение к серверу " + socket.getRemoteSocketAddress());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            startSenderAndReader(scanner, out, in, username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startSenderAndReader(Scanner scanner, PrintWriter out, BufferedReader in, String username) {
        MessageSender messageSender = new MessageSenderImpl(out, username, scanner, this);
        MessageReceiver messageReceiver = new MessageReceiverImpl(in, logger);
        messageSenderThread = new Thread((Runnable) messageSender);
        messageReceiverThread = new Thread((Runnable) messageReceiver);
        messageSenderThread.start();
        messageReceiverThread.start();
    }

    @Override
    public void disconnect() {
        logger.addLog("Завершение работы клиента");
        messageSenderThread.interrupt();
        messageReceiverThread.interrupt();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getUsername(Scanner scanner) {
        System.out.println("Введите имя для общения в чате");
        logger.addLog("Введите имя для общения в чате");

        String username = null;
        while (username == null) {
            String input = scanner.nextLine();
            if (!input.isBlank()) {
                username = input;
                scanner.reset();
            }
        }
        return username;
    }
}
