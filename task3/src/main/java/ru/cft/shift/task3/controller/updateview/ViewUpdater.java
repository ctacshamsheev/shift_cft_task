package ru.cft.shift.task3.controller.updateview;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task3.controller.records.RecordListener;
import ru.cft.shift.task3.controller.timer.TimerUpdateListener;
import ru.cft.shift.task3.model.GameState;
import ru.cft.shift.task3.model.cell.GameCell;
import ru.cft.shift.task3.model.cell.NumberGameCell;
import ru.cft.shift.task3.view.*;

@Slf4j
@NoArgsConstructor
public class ViewUpdater implements ViewUpdateListener {
    private MainWindow mainWindow;
    private TimerUpdateListener gameTimer;
    private RecordListener records;
    private GameType gameType;

    public void newGame(GameType gameType) {
        this.gameType = gameType;
        mainWindow.setVisible(false);
        mainWindow.createGameField(gameType.getHeight(), gameType.getWidth());
        mainWindow.setBombsCount(gameType.getBombTotal());
        mainWindow.setVisible(true);
        gameTimer.reset();
    }

    @Override
    public void updateGameState(GameState gameState) {
        switch (gameState) {
            case READY -> gameTimer.reset();
            case START -> gameTimer.run();
            case FINISH -> {
                gameTimer.stop();
                new LoseWindow(mainWindow).setExitListener(e -> mainWindow.dispose());
            }
            case WIN -> {
                gameTimer.stop();
                records.run(gameTimer.getTimer(), gameType);
                if (records.isHighScore()) {
                    new RecordsWindow(mainWindow);
                }
                new WinWindow(mainWindow).setExitListener(e -> mainWindow.dispose());
            }
        }
    }

    public void updateCell(GameCell gameCell, int i, int j) {
        GameImage gameImage = switch (gameCell.getState()) {
            case CLOSE_STATE -> GameImage.CLOSED;
            case FLAG_STATE -> GameImage.MARKED;
            case OPEN_STATE -> switch (gameCell.getType()) {
                case BOMB_TYPE -> GameImage.BOMB;
                case EMPTY_TYPE -> GameImage.EMPTY;
                case NUMBER_TYPE -> switch (((NumberGameCell) gameCell.getCell()).number()) {
                    case 1 -> GameImage.NUM_1;
                    case 2 -> GameImage.NUM_2;
                    case 3 -> GameImage.NUM_3;
                    case 4 -> GameImage.NUM_4;
                    case 5 -> GameImage.NUM_5;
                    case 6 -> GameImage.NUM_6;
                    case 7 -> GameImage.NUM_7;
                    case 8 -> GameImage.NUM_8;
                    default -> throw new IllegalStateException("Unexpected value");
                };
            };
        };
        mainWindow.setCellImage(j, i, gameImage);
    }

    @Override
    public void updateGameTimer(int timer) {
        mainWindow.setTimerValue(timer);
    }

    @Override
    public void updateBombCount(int bombCount) {
        mainWindow.setBombsCount(bombCount);
    }

    @Override
    public ViewUpdateListener setGameTimer(TimerUpdateListener gameTimer) {
        this.gameTimer = gameTimer;
        return this;
    }

    @Override
    public ViewUpdateListener setRecord(RecordListener records) {
        this.records = records;
        return this;
    }

    @Override
    public ViewUpdateListener setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        return this;
    }
}
