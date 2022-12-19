package ru.cft.shift.task3.controller.records;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task3.view.GameType;
import ru.cft.shift.task3.view.HighScoresWindow;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Records implements RecordListener {

    private int timerCountWin;
    private GameType gameType;
    private final HighScoresWindow highScoresWindow;
    private final Properties properties;
    private static final String file = "app.properties";

    public Records(HighScoresWindow highScoresWindow) {
        this.highScoresWindow = highScoresWindow;
        properties = new Properties();
        loadProperties();
        setRecords();
    }

    @Override
    public void run(int timerCount, GameType gameType) {
        this.gameType = gameType;
        this.timerCountWin = timerCount;
        log.trace("record timerCount: {}", timerCount);
    }

    @Override
    public void onRecordNameEntered(String name) {
        log.trace("record name: {}", name);
        setRecord(name, timerCountWin, gameType);
        saveRecord(name, timerCountWin, gameType);
    }

    @Override
    public void saveSettings() {
        log.info(" properties. saveSettings: {}", properties.toString());
        try (FileWriter fileWriter = new FileWriter(file)) {
            properties.store(fileWriter, "app properties");
        } catch (IOException e) {
            log.error(" properties.save error: {}", e.getMessage());
        }
    }

    @Override
    public Boolean isHighScore() {
        return getTimerMin(gameType) > timerCountWin;
    }

    private int getTimerMin(GameType gameType) {
        String result = switch (gameType) {
            case NOVICE -> properties.getProperty("minNOVICE", "999");
            case MEDIUM -> properties.getProperty("minMEDIUM", "999");
            case EXPERT -> properties.getProperty("minEXPERT", "999");
        };
        return Integer.parseInt(result);
    }

    private void setRecord(String name, int timerCount, GameType gameType) {
        switch (gameType) {
            case NOVICE -> highScoresWindow.setNoviceRecord(name, timerCount);
            case MEDIUM -> highScoresWindow.setMediumRecord(name, timerCount);
            case EXPERT -> highScoresWindow.setExpertRecord(name, timerCount);
        }
    }

    private void saveRecord(String name, int timerCount, GameType gameType) {
        switch (gameType) {
            case NOVICE -> {
                properties.put("minNOVICE", String.valueOf(timerCount));
                properties.put("nameNOVICE", name);
            }
            case MEDIUM -> {
                properties.put("minMEDIUM", String.valueOf(timerCount));
                properties.put("nameMEDIUM", name);
            }
            case EXPERT -> {
                properties.put("minEXPERT", String.valueOf(timerCount));
                properties.put("nameEXPERT", name);
            }
        }
        saveSettings();
    }

    private void loadProperties() {
        try (FileReader fileReader = new FileReader(file)) {
            properties.load(fileReader);
        } catch (IOException e) {
            log.error(" properties.load error in file: {}", file);
        }
    }

    private void setRecords() {
        setRecord(
                properties.getOrDefault("nameNOVICE", "Unknown").toString(),
                Integer.parseInt(properties.getOrDefault("minNOVICE", "999").toString()),
                GameType.NOVICE);
        setRecord(
                properties.getOrDefault("nameMEDIUM", "Unknown").toString(),
                Integer.parseInt(properties.getOrDefault("minMEDIUM", "999").toString()),
                GameType.MEDIUM);
        setRecord(
                properties.getOrDefault("nameEXPERT", "Unknown").toString(),
                Integer.parseInt(properties.getOrDefault("minEXPERT", "999").toString()),
                GameType.EXPERT);
    }
}
