package ru.cft.shift.task3.view;

public enum GameType {
    NOVICE(9, 9, 10),
    MEDIUM(16, 16, 40),
    EXPERT(16, 30, 99);


    private final int height;
    private final int width;
    private final int bombTotal;
    private final int openTotal;

    GameType(int height, int width, int bombTotal) {
        this.height = height;
        this.width = width;
        this.bombTotal = bombTotal;
        openTotal = height * width - bombTotal;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBombTotal() {
        return bombTotal;
    }

    public int getOpenTotal() {
        return openTotal;
    }

}
