package ru.cft.shift.task3.app;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task3.controller.ControllerListener;
import ru.cft.shift.task3.controller.records.Records;
import ru.cft.shift.task3.controller.timer.TimerUpdateListener;
import ru.cft.shift.task3.controller.updateview.ViewUpdateListener;
import ru.cft.shift.task3.model.Model;
import ru.cft.shift.task3.view.*;

@Slf4j
@Builder
public class Application {
    MainWindow mainWindow;
    ViewUpdateListener viewUpdate;
    Model model;
    ControllerListener controller;
    TimerUpdateListener gameTimer;

    public Application(MainWindow mainWindow, ViewUpdateListener viewUpdate, Model model, ControllerListener controller, TimerUpdateListener gameTimer) {
        controller.setModel(model);
        model.setUpdateListener(viewUpdate);
        gameTimer.setUpdateListener(viewUpdate);

        SettingsWindow settingsWindow = new SettingsWindow(mainWindow, controller);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
        Records records = new Records(highScoresWindow);
        // TODO эти классы зависимы от других, их не удобно выносить в билдер,
        //  и в конструкторе завязана логика, придется переделывать конструкторы и логику

        viewUpdate
                .setMainWindow(mainWindow)
                .setGameTimer(gameTimer)
                .setRecord(records);

        mainWindow
                .setNewGameMenuAction(controller)
                .setCellListener(controller)
                .setSettingsMenuAction(e -> settingsWindow.setVisible(true))
                .setHighScoresMenuAction(e -> highScoresWindow.setVisible(true))
                .setExitMenuAction(e -> mainWindow.dispose());

        WinWindow.setNewGameListener(controller);
        LoseWindow.setNewGameListener(controller);
        RecordsWindow.setNameListener(records);

        controller.updateModel(GameType.NOVICE);
    }
}
