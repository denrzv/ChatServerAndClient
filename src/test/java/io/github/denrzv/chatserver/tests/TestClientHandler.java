package io.github.denrzv.chatserver.tests;

import io.github.denrzv.chatserver.ClientHandlerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class TestClientHandler {

    @Test
    @DisplayName("Тест на невалидные значения")
    public void shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new ClientHandlerImpl(null, null, null));
    }
}
