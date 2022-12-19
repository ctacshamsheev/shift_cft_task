package ru.cft.shift.task3.model;

import ru.cft.shift.task3.model.cell.BombGameCell;
import ru.cft.shift.task3.model.cell.EmptyGameCell;
import ru.cft.shift.task3.model.cell.GameCell;
import ru.cft.shift.task3.model.cell.NumberGameCell;
import ru.cft.shift.task3.view.GameType;

import java.util.Random;

public class GameGenerator {
    private final int[][] matrix;

    private final GameType gameType;

    public GameGenerator(GameType gameType) {
        this.gameType = gameType;
        matrix = new int[gameType.getHeight()][gameType.getWidth()];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("GameGenerator{" +
                ", gameType.getHeight()=" + gameType.getHeight() +
                ", gameType.getWidth()=" + gameType.getWidth() +
                ", bombCount=" + gameType.getBombTotal() +
                "}\n");

        for (int i = 0; i < gameType.getHeight(); i++) {
            for (int j = 0; j < gameType.getWidth(); j++) {
                if (matrix[i][j] != -1)
                    result.append(" ").append(matrix[i][j]);
                else
                    result.append(" *");
            }
            result.append("\n");
        }

        return result.toString();
    }

    private int getIJCell(int i, int j) {
        if (i < 0 || j < 0 || i >= gameType.getHeight() || j >= gameType.getWidth())
            return 0;
        if (matrix[i][j] == -1) {
            return 1;
        }
        return 0;
    }

    private int getBombCount(int i, int j) {
        if (matrix[i][j] == -1)
            return -1;
        return getIJCell(i - 1, j - 1) +
                getIJCell(i - 1, j) +
                getIJCell(i - 1, j + 1) +
                getIJCell(i, j - 1) +
                getIJCell(i, j + 1) +
                getIJCell(i + 1, j - 1) +
                getIJCell(i + 1, j) +
                getIJCell(i + 1, j + 1);

    }

    private void fillBombCount() {
        for (int i = 0; i < gameType.getHeight(); i++) {
            for (int j = 0; j < gameType.getWidth(); j++) {
                matrix[i][j] = getBombCount(i, j);
            }
        }
    }

    private void fillBombRandom(int i, int j) {
        Random random = new Random();
        int bombCount = gameType.getBombTotal();
        while (bombCount > 0) {
            int n = random.nextInt(gameType.getHeight());
            int m = random.nextInt(gameType.getWidth());
            if (!(n == i && m == j) && matrix[n][m] == 0) {
                matrix[n][m] = -1;
                bombCount--;
            }
        }
    }

    private void fillGameCells(GameCell[][] gameCells) {
        for (int i = 0; i < gameType.getHeight(); i++) {
            for (int j = 0; j < gameType.getWidth(); j++) {
                switch (matrix[i][j]) {
                    case -1 -> gameCells[i][j].setCell(new BombGameCell());
                    case 0 -> gameCells[i][j].setCell(new EmptyGameCell());
                    default -> gameCells[i][j].setCell(new NumberGameCell(matrix[i][j]));
                }
            }
        }
    }

    public void generate(int n, int m, GameCell[][] gameCells) {
        fillBombRandom(n, m);
        fillBombCount();
        System.out.println(this);
        fillGameCells(gameCells);
    }
}
