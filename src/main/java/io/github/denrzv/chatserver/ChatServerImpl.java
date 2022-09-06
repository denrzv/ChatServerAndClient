package io.github.denrzv.chatserver;

import io.github.denrzv.common.Logger;
import io.github.denrzv.common.LoggerImpl;
import io.github.denrzv.common.Settings;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChatServerImpl implements ChatServer {
    private boolean running;
    private final Settings settings;
    protected List<ClientHandler> clientHandlerList;
    private final Logger logger;

    public ChatServerImpl(Settings settings) {
        Optional<Object> optional = Optional.ofNullable(settings);
        if (optional.isPresent()) {
            this.settings = settings;
            clientHandlerList = new ArrayList<>();
            logger = new LoggerImpl((String) settings.getSettingByName("logFileName"));
        } else {
            throw new IllegalArgumentException("Некорректно задано имя файла настроек!");
        }
    }

    public List<ClientHandler> getClientHandlerList() {
        return Collections.unmodifiableList(clientHandlerList);
    }

    @Override
    public void start() {
        long port = (long) settings.getSettingByName("port");
        int backlog = 50;
        String host = (String) settings.getSettingByName("host");
        running = true;
        try(ServerSocket serverSocket = new ServerSocket((int) port, backlog, InetAddress.getByName(host))) {
            System.out.println("Сервер запущен " + serverSocket.getLocalSocketAddress());
            logger.addLog("Сервер запущен " + serverSocket.getLocalSocketAddress());
            Socket socket;
            while (running) {
                socket = serverSocket.accept();

                System.out.println("Новое подключение: " + socket);
                logger.addLog("Новое подключение: " + socket);
                ClientHandler clientHandler = new ClientHandlerImpl(socket, this, logger);
                clientHandlerList.add(clientHandler);

                new Thread((Runnable) clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    @Override
    public void stop() {
        running = false;
        Thread.currentThread().interrupt();
    }

    public boolean isRunning() {
        return running;
    }
}
