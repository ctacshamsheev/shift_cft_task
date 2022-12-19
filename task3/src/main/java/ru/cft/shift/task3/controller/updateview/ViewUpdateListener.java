package ru.cft.shift.task3.controller.updateview;

import ru.cft.shift.task3.controller.records.RecordListener;
import ru.cft.shift.task3.controller.timer.TimerUpdateListener;
import ru.cft.shift.task3.model.GameState;
import ru.cft.shift.task3.model.cell.GameCell;
import ru.cft.shift.task3.view.GameType;
import ru.cft.shift.task3.view.MainWindow;

public interface ViewUpdateListener {

    void newGame(GameType gameType);

    void updateGameState(GameState gameState);

    void updateCell(GameCell gameCell, int i, int j);

    void updateGameTimer(int timer);

    void updateBombCount(int bombCount);

    ViewUpdateListener setGameTimer(TimerUpdateListener gameTimer);

    ViewUpdateListener setRecord(RecordListener records);

    ViewUpdateListener setMainWindow(MainWindow mainWindow);

}
