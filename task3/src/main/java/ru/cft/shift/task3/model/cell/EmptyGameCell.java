package ru.cft.shift.task3.model.cell;

public class EmptyGameCell implements Cell {

    @Override
    public String toString() {
        return "  ";
    }

    @Override
    public CellType getType() {
        return CellType.EMPTY_TYPE;
    }
}
