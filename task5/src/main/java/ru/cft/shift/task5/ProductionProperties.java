package ru.cft.shift.task5;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ProductionProperties {
    private static final Properties properties = new Properties();
    public static final String file = "production.properties";

    public static void setProperties(int producerCount, int consumerCount, int producerTime, int consumerTime, int storageSize) {
        properties.put("producerCount", String.valueOf(producerCount));
        properties.put("consumerCount", String.valueOf(consumerCount));
        properties.put("producerTime", String.valueOf(producerTime));
        properties.put("consumerTime", String.valueOf(consumerTime));
        properties.put("storageSize", String.valueOf(storageSize));
        saveSettings();
        log.trace("setProperties: {}", properties);
    }

    public static void setPropertiesFromFile() {
        loadProperties();
        log.trace("setPropertiesFromFile({}) {}", file, properties);
    }


    private static void saveSettings() {
        log.info(" properties. saveSettings: {}", properties);
        try (FileWriter fileWriter = new FileWriter(file)) {
            properties.store(fileWriter, "app properties");
            log.debug("here");
        } catch (Exception e) {
            log.error(" properties.save error: {}", e.getMessage());
        }
    }

    private static void loadProperties() {
        try (FileReader fileReader = new FileReader(file)) {
            properties.load(fileReader);
        } catch (IOException e) {
            log.error(" properties.load error in file: {}", file);
        }
    }

    public static int getProducerCount() {
        return Integer.parseInt((String) properties.getOrDefault("producerCount", 10));
    }

    public static int getConsumerCount() {
        return Integer.parseInt((String) properties.getOrDefault("consumerCount", 10));
    }

    public static int getProducerTime() {
        return Integer.parseInt((String) properties.getOrDefault("producerTime", 100));
    }

    public static int getConsumerTime() {
        return Integer.parseInt((String) properties.getOrDefault("consumerTime", 200));
    }

    public static int getStorageSize() {
        return Integer.parseInt((String) properties.getOrDefault("storageSize", 5));
    }
}
