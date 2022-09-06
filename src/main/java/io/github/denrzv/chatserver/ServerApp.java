package io.github.denrzv.chatserver;

import io.github.denrzv.common.Settings;
import io.github.denrzv.common.SettingsImpl;


public class ServerApp {
    public static void main(String[] args) {
        Settings settings = new SettingsImpl("server_settings.json");
        ChatServer chatServer = new ChatServerImpl(settings);
        chatServer.start();
    }
}
