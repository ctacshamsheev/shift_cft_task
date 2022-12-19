package ru.cft.shift.task3.model.cell;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameCell {
    private Cell cell;
    private CellState state;

    public GameCell() {
        this.state = CellState.CLOSE_STATE;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int setFlag() {
        if (state == CellState.FLAG_STATE) {
            this.state = CellState.CLOSE_STATE;
            return +1;
        } else if (state == CellState.CLOSE_STATE) {
            this.state = CellState.FLAG_STATE;
            return -1;
        } else {
            log.error("Can't use flag with open cell");
        }
        return 0;
    }

    public boolean open() {
        switch (state) {
            case FLAG_STATE, CLOSE_STATE -> state = CellState.OPEN_STATE;
            case OPEN_STATE -> {
                return false;
            }
        }
        return true;
    }

    public CellState getState() {
        return state;
    }

    public CellType getType() {
        return cell.getType();
    }

    public Cell getCell() {
        return cell;
    }

    @Override
    public String toString() {
        return "GameCell{" +
                "type=" + cell.getType() +
                ", state=" + state +
                '}';
    }
}
