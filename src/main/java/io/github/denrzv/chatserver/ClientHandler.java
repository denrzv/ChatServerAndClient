package io.github.denrzv.chatserver;



public interface ClientHandler {
    void broadcastMessage(String msg);
    void sendMessage(String msg);
}
