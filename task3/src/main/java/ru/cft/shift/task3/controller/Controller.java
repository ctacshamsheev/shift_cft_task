package ru.cft.shift.task3.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task3.model.Model;
import ru.cft.shift.task3.view.ButtonType;
import ru.cft.shift.task3.view.GameType;

import java.awt.event.ActionEvent;

@Slf4j
@NoArgsConstructor
public class Controller implements ControllerListener {
    Model model;
    GameType gameType;

    public void updateModel(GameType gameType) {
        model.runNew(gameType);
        this.gameType = gameType;
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> model.open(y, x);
            case RIGHT_BUTTON -> model.openAroundFlag(y, x);
            case MIDDLE_BUTTON -> model.setFlag(y, x);
            // TODO предупреждение идеи.
            // Но модель построенна на класическом виде матрицы, когда первый параметр это номер строки, а второй столбца, во вью перепутано
        }
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        log.trace("onGameTypeChanged {}", gameType.toString());
        updateModel(gameType);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Game")) {
            updateModel(gameType);
            log.trace("actionPerformed (new game): {}", e.getActionCommand());
        } else {
            log.error("actionPerformed: else {}", e);
        }
    }

    /**
     * @param model
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}
