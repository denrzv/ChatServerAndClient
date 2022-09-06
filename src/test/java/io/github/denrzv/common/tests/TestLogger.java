package io.github.denrzv.common.tests;

import io.github.denrzv.common.Logger;
import io.github.denrzv.common.LoggerImpl;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;


public class TestLogger {
    static Logger logger;
    static String fileName;

    @BeforeAll
    public static void setup() {
        fileName = "test.log";
        File file = new File(fileName);
        if (file.exists()) file.delete();
        logger = new LoggerImpl(fileName);
    }

    @Test
    @DisplayName("Добавление сообщения в лог")
    public void testAddLog() {
        String msg = "Test";

        logger.addLog(msg);
        Optional<String> result = Optional.empty();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            result = stream
                    .filter(line -> line.contains(msg))
                    .findAny();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Тест на невалидные значения")
    public void shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> logger.addLog(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new LoggerImpl(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new LoggerImpl(""));
    }

    @AfterAll
    public static void clean() {
        File file = new File(fileName);
        if (file.exists()) file.delete();
    }
}
