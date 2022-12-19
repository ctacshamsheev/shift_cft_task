package ru.cft.shift.task2.model;

public abstract class Figure {
    private final FigureType figureType;
    protected double area;
    protected double perimeter;

    protected Figure(FigureType name) {
        this.figureType = name;
    }

    public String getTitle() {
        return figureType.getTitle();
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public String getInfo() {
        return String.format("""
                        Тип фигуры: %s
                        Площадь: %.2f кв.мм
                        Периметр: %.2f мм""",
                getTitle(), area, perimeter);
    }
}
