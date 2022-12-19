package ru.cft.shift.task2.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Triangle extends Figure {
    private static final Logger logger = LoggerFactory.getLogger(Triangle.class.getName());
    private double a;
    private double b;
    private double c;
    private double angleA;
    private double angleB;
    private double angleC;


    public Triangle(double a, double b, double c) {
        super(FigureType.TRIANGLE);
        logger.trace("Triangle({},{},{})", a, b, c);
        if (a <= 0.0d || b <= 0.0d || c <= 0.0) {
            throw new IllegalArgumentException("triangle side must be greater than 0");
        }
        if (!(a < b + c && b < a + c && c < a + b)) {
            throw new IllegalArgumentException("it is not triangle");
        }
        reCalc(a, b, c);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\n" + String.format("""
                        Длина стороны A: %.2f мм.
                        противолежащий угол: %.2f град.
                        Длина стороны B: %.2f мм.
                        противолежащий угол: %.2f град.
                        Длина стороны C: %.2f мм.
                        противолежащий угол: %.2f град.""",
                a, angleA, b, angleB, c, angleC);
    }


    public void setA(double a) {
        reCalc(a, b, c);
    }

    public void setB(double b) {
        reCalc(a, b, c);
    }

    public void setC(double c) {
        reCalc(a, b, c);
    }


    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getAngleA() {
        return angleA;
    }

    public double getAngleB() {
        return angleB;
    }

    public double getAngleC() {
        return angleC;
    }


    private double calcArea() {
        double p = (a + b + c) / 2.0;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    private double calcPerimeter() {
        return a + b + c;
    }


    private double calcAngle(double a, double b, double c) {
        return Math.toDegrees(
                Math.acos(
                        (b * b + c * c - a * a) / (2.0 * b * c)
                ));
    }

    private void reCalc(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        angleA = calcAngle(a, b, c);
        angleB = calcAngle(b, c, a);
        angleC = calcAngle(c, a, b);
        area = calcArea();
        perimeter = calcPerimeter();
    }
}
