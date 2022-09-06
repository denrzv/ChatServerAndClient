package io.github.denrzv.chatclient;

import io.github.denrzv.common.Settings;
import io.github.denrzv.common.SettingsImpl;


public class ClientApp {
    public static void main(String[] args) {
        Settings settings = new SettingsImpl("client_settings.json");
        ChatClient chatClient = new ChatClientImpl(settings);
        chatClient.start();
    }
}
