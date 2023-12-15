package org.example.taro_notification_bot;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

@Slf4j
public class ConfigReader implements Supplier<Properties> {

    @Override
    public Properties get() {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }
}
