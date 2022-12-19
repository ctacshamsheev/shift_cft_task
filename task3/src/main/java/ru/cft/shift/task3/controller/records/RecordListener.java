package ru.cft.shift.task3.controller.records;

import ru.cft.shift.task3.view.GameType;
import ru.cft.shift.task3.view.RecordNameListener;

public interface RecordListener extends RecordNameListener {

    void run(int timerCount, GameType gameType);

    void saveSettings();

    Boolean isHighScore();
}
