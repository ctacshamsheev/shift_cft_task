package ru.cft.shift.task3.model.cell;

public class BombGameCell implements Cell {

    @Override
    public String toString() {
        return " *";
    }

    @Override
    public CellType getType() {
        return CellType.BOMB_TYPE;
    }
}
