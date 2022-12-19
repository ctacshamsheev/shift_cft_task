package ru.cft.shift.task2.model;

public enum FigureType {

    CIRCLE("Круг"),
    RECTANGLE("Прямоугольник"),
    TRIANGLE("Треугольник");
    private final String title;

    FigureType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
