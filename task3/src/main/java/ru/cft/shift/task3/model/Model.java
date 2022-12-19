package ru.cft.shift.task3.model;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task3.controller.updateview.ViewUpdateListener;
import ru.cft.shift.task3.model.cell.CellState;
import ru.cft.shift.task3.model.cell.CellType;
import ru.cft.shift.task3.model.cell.GameCell;
import ru.cft.shift.task3.model.cell.NumberGameCell;
import ru.cft.shift.task3.view.GameType;

@Slf4j
public class Model {
    private GameType gameType;
    private GameState gameState;
    private GameCell[][] gameCells;
    private int openCount;
    private int bombCount;
    private ViewUpdateListener updateListener;


    public void runNew(GameType gameType) {
        log.trace("runNew: {}", gameType);
        this.gameType = gameType;
        gameState = GameState.READY;
        openCount = 0;
        bombCount = gameType.getBombTotal();
        gameCells = new GameCell[gameType.getHeight()][gameType.getWidth()];
        for (int i = 0; i < gameType.getHeight(); i++) {
            for (int j = 0; j < gameType.getWidth(); j++) {
                gameCells[i][j] = new GameCell();
            }
        }
        updateListener.newGame(gameType);
    }


    public void open(int x, int y) {
        log.debug("open ({}, {})", x, y);
        if (gameState == GameState.READY) {
            setGameState(GameState.START);
            new GameGenerator(gameType).generate(x, y, gameCells);
            checkTypeAndOpenCell(x, y);
        } else if (gameState == GameState.START) {
            checkTypeAndOpenCell(x, y);
        } else {
            log.info("game finish!");
        }
    }


    private void checkTypeAndOpenCell(int x, int y) {
        if (gameCells[x][y].getState() != CellState.FLAG_STATE) {
            switch (gameCells[x][y].getType()) {
                case BOMB_TYPE -> setGameState(GameState.FINISH);
                case NUMBER_TYPE -> openCellWithUpdateListener(x, y);
                case EMPTY_TYPE -> findEmptyRecursively(x, y);
            }
        }
    }

    private boolean check(int x, int y) {
        return (x >= 0 && y >= 0 && x < gameType.getHeight() && y < gameType.getWidth()
                && gameCells[x][y].getState() != CellState.OPEN_STATE);
    }

    private void findEmptyRecursively(int x, int y) {
        if (gameState == GameState.START) {
            if (gameCells[x][y].getType() == CellType.EMPTY_TYPE) {
                openCellWithUpdateListener(x, y);
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (check(i, j)) findEmptyRecursively(i, j);
                    }
                }
            } else if (gameCells[x][y].getType() == CellType.NUMBER_TYPE) {
                openCellWithUpdateListener(x, y);
            }
        }
    }

    private void openCellWithUpdateListener(int x, int y) {
        if (gameCells[x][y].getState() == CellState.FLAG_STATE) {
            updateListener.updateBombCount(++bombCount);
        }
        if (gameCells[x][y].open()) {
            openCount++;
        }
        updateListener.updateCell(gameCells[x][y], x, y);
        if (openCount == gameType.getOpenTotal() && gameState == GameState.START) {
            setGameState(GameState.WIN);
        }

    }

    private void setGameState(GameState gameState) {
        log.debug("setGameState = {}", gameState);
        this.gameState = gameState;
        if (gameState == GameState.FINISH) openAll();
        updateListener.updateGameState(gameState);
    }

    public void setUpdateListener(ViewUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void setFlag(int x, int y) {
        if (bombCount >= 0) {
            bombCount += gameCells[x][y].setFlag();
            if (bombCount < 0) {
                log.error("Can't use flag more then bombs");
                bombCount += gameCells[x][y].setFlag();
            } else {
                updateListener.updateBombCount(bombCount);
                updateListener.updateCell(gameCells[x][y], x, y);
            }
        }
    }

    public void openAroundFlag(int x, int y) {
        if (gameCells[x][y].getState() == CellState.OPEN_STATE &&
                gameCells[x][y].getType() == CellType.NUMBER_TYPE &&
                ((NumberGameCell) gameCells[x][y].getCell()).number() == getFlagCount(x, y)) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (check(i, j)
                            && gameCells[i][j].getState() != CellState.FLAG_STATE
                            && gameState == GameState.START) {
                        open(i, j);
                    }
                }
            }
        }
    }

    private int getFlagCount(int x, int y) {
        int result = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (check(i, j) && gameCells[i][j].getState() == CellState.FLAG_STATE) ++result;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Model{" + gameType + "}{" + openCount + "}\n");
        for (int i = 0; i < gameType.getHeight(); i++) {
            for (int j = 0; j < gameType.getWidth(); j++) {
                result.append(switch (gameCells[i][j].getState()) {
                            case CLOSE_STATE -> " ?";
                            case OPEN_STATE -> gameCells[i][j].toString();
                            case FLAG_STATE -> " F";
                        }
                );
            }
            result.append("\n");
        }
        return result.toString();
    }

    private void openAll() {
        for (int i = 0; i < gameType.getHeight(); i++) {
            for (int j = 0; j < gameType.getWidth(); j++) {
                if (check(i, j)) openCellWithUpdateListener(i, j);
            }
        }
    }
}
