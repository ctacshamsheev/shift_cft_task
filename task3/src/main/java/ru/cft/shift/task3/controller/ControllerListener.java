package ru.cft.shift.task3.controller;

import ru.cft.shift.task3.model.Model;
import ru.cft.shift.task3.view.ButtonType;
import ru.cft.shift.task3.view.CellEventListener;
import ru.cft.shift.task3.view.GameType;
import ru.cft.shift.task3.view.GameTypeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface ControllerListener extends CellEventListener, GameTypeListener, ActionListener {
    void updateModel(GameType gameType);

    void onMouseClick(int x, int y, ButtonType buttonType);

    void onGameTypeChanged(GameType gameType);

    void actionPerformed(ActionEvent e);

    void setModel(Model model);
}
