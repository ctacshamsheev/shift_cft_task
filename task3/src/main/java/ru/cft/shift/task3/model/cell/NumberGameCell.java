package ru.cft.shift.task3.model.cell;

public record NumberGameCell(int number) implements Cell {


    @Override
    public String toString() {
        return " " + number;
    }

    @Override
    public CellType getType() {
        return CellType.NUMBER_TYPE;
    }
}
