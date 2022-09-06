package io.github.denrzv.common.tests;

import io.github.denrzv.common.Settings;
import io.github.denrzv.common.SettingsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSettings {
    static Settings settings;

    @BeforeAll
    static void setup() {
        settings = new SettingsImpl("server_settings.json");
    }

    @Test
    @DisplayName("Получение значения настроек")
    public void testGetSettingValueByName() {
        int port = 31313;
        long portFromSettings = (long) settings.getSettingByName("port");
        Assertions.assertEquals(port, (int) portFromSettings);
    }

    @Test
    @DisplayName("Тест на невалидные значения")
    public void shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> settings.getSettingByName(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> settings.getSettingByName(""));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SettingsImpl(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SettingsImpl(""));
    }
}
