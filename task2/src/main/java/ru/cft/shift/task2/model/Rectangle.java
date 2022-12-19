package ru.cft.shift.task2.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rectangle extends Figure {

    private static final Logger logger = LoggerFactory.getLogger(Rectangle.class.getName());
    private double length;
    private double width;
    private double diagonal;

    public Rectangle(double a, double b) {
        super(FigureType.RECTANGLE);
        logger.trace("Rectangle({},{})", a, b);
        if (a <= 0.0d || b <= 0.0d) {
            throw new IllegalArgumentException("rectangle side must be greater than 0");
        }
        reCalc(a, b);
    }

    public void setLength(double length) {
        reCalc(length, width);
    }

    public void setWidth(double width) {
        reCalc(length, width);
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getDiagonal() {
        return diagonal;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n" + String.format("""
                        Длина диагонали: %.2f мм
                        Длина: %.2f мм
                        Ширина: %.2f мм""",
                diagonal, length, width);
    }


    private double calcArea() {
        return length * width;
    }

    private double calcPerimeter() {
        return (length + width) * 2.0;
    }

    private double calcDiagonal() {
        return Math.sqrt(length * length + width * width);
    }

    private void reCalc(double a, double b) {
        this.length = Math.max(a, b);
        this.width = Math.min(a, b);
        diagonal = calcDiagonal();
        area = calcArea();
        perimeter = calcPerimeter();
    }
}
