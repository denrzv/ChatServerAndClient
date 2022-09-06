package io.github.denrzv.common;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

public class LoggerImpl implements Logger {
    private FileWriter fw;

    public LoggerImpl(String logFileName) {
        Optional<Object> optional = Optional.ofNullable(logFileName);
        if (optional.isPresent() && !logFileName.isBlank()) {
            try {
                fw = new FileWriter(logFileName, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Некорректно задано имя файла!");
        }
    }

    @Override
    public void addLog(String msg) {
        Optional<Object> optional = Optional.ofNullable(msg);
        if (optional.isPresent()) {
            try {
                fw.write(new Timestamp(System.currentTimeMillis()) + ": " + msg + "\n");
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Некорректно задано сообщение для записи в лог!");
        }
    }
}
