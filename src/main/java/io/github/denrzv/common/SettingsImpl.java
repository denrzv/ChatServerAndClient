package io.github.denrzv.common;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class SettingsImpl implements Settings {
    private final JSONObject settings;

    public SettingsImpl(String fileName) {
        Optional<Object> optional = Optional.ofNullable(fileName);
        if (optional.isPresent()) {
            settings  = readJson(fileName).orElseThrow(IllegalArgumentException::new);
        } else {
            throw new IllegalArgumentException("Некорректно задано имя файла настроек!");
        }
    }

    @Override
    public Object getSettingByName(String name) {
        Optional<Object> optional = Optional.ofNullable(name);
        Object value;
        if (optional.isPresent() && settings.containsKey(name)) {
            value = settings.get(name);
        } else {
            throw new IllegalArgumentException("Некорректно задан параметр настроек!");
        }
        return value;
    }

    private Optional<JSONObject> readJson(String fileName) {
        Optional<JSONObject> json = Optional.empty();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            json = Optional.ofNullable(jsonObject);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return json;
    }
}
