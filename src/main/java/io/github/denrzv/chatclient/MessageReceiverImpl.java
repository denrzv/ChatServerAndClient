package io.github.denrzv.chatclient;

import io.github.denrzv.common.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReceiverImpl implements MessageReceiver, Runnable {
    private final BufferedReader in;
    private final Logger logger;

    public MessageReceiverImpl(BufferedReader in, Logger logger) {
        this.in = in;
        this.logger = logger;
    }

    @Override
    public void receiveMessage() {
        String input;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                input = in.readLine();
                logger.addLog(input);
                System.out.println(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        receiveMessage();
    }
}
