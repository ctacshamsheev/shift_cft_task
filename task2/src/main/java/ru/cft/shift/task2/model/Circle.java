package ru.cft.shift.task2.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Circle extends Figure {
    private static final Logger logger = LoggerFactory.getLogger(Circle.class.getName());
    private double radius;
    private double diameter;

    public Circle(double radius) {
        super(FigureType.CIRCLE);
        logger.trace("Circle({})", radius);
        if (radius <= 0.0) {
            throw new IllegalArgumentException("circle radius must be greater than 0");
        }
        reCalc(radius);
    }

    public void setRadius(double radius) {
        reCalc(radius);
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n" + String.format("""
                        Радиус: %.2f мм
                        Диаметр: %.2f мм""",
                radius, diameter);
    }


    private double calcDiameter() {
        return 2.0 * radius;
    }

    private double calcArea() {
        return Math.PI * radius * radius;
    }

    private double calcPerimeter() {
        return 2.0d * Math.PI * radius;
    }

    private void reCalc(Double radius) {
        this.radius = radius;
        diameter = calcDiameter();
        area = calcArea();
        perimeter = calcPerimeter();
    }
}
