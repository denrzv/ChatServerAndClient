package io.github.denrzv.chatserver;

import io.github.denrzv.common.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class ClientHandlerImpl implements ClientHandler, Runnable {
    private final Socket clientSocket;
    private final ChatServer chatServer;
    private PrintWriter out;
    private final Logger logger;

    public ClientHandlerImpl(Socket clientSocket, ChatServer chatServer, Logger logger) {
        Optional<Object> optClient = Optional.ofNullable(clientSocket);
        Optional<Object> optServer = Optional.ofNullable(clientSocket);
        Optional<Object> optLogger= Optional.ofNullable(clientSocket);
        if (optClient.isPresent() & optServer.isPresent() & optLogger.isPresent()) {
            this.clientSocket = clientSocket;
            this.chatServer = chatServer;
            this.logger = logger;
        } else {
            throw new IllegalArgumentException("Некорректно заданы переменные конструктора");
        }
    }

    @Override
    public void broadcastMessage(String input) {
        Optional<Object> optional = Optional.ofNullable(input);
        if (optional.isPresent()) {
            ChatServerImpl chatServerImpl = (ChatServerImpl) chatServer;
            chatServerImpl.getClientHandlerList()
                    .parallelStream()
                    .forEach(clientHandler -> clientHandler.sendMessage(input));
        } else {
            throw new IllegalArgumentException("Некорректно задано сообщение!");
        }
    }

    @Override
    public void sendMessage(String msg) {
        Optional<Object> optional = Optional.ofNullable(msg);
        if (optional.isPresent()) {
            out.println(msg);
        } else {
            throw new IllegalArgumentException("Некорректно задано сообщение!");
        }
    }

    @Override
    public void run() {
        try(
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            boolean announced = false;
            String username = null;
            String input;
            while ((input = in.readLine()) != null) {
                if (!announced) {
                    username = input.split(":")[0];
                    broadcastMessage("К чату присоединяется " + username);
                    logger.addLog("К чату присоединяется " + username);
                    announced = true;
                    continue;
                }
                if (input.contains("/exit")) {
                    out.println(username + " выходит из чата");
                    logger.addLog(username + " выходит из чата");
                    break;
                }
                logger.addLog(input);
                broadcastMessage(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
