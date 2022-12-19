package ru.cft.shift.task3.app;

import ru.cft.shift.task3.controller.Controller;
import ru.cft.shift.task3.controller.timer.GameTimer;
import ru.cft.shift.task3.controller.updateview.ViewUpdater;
import ru.cft.shift.task3.model.Model;
import ru.cft.shift.task3.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        Application.builder()
                .model(new Model())
                .mainWindow(new MainWindow())
                .controller(new Controller())
                .viewUpdate(new ViewUpdater())
                .gameTimer(new GameTimer())
                .build();
    }
}
