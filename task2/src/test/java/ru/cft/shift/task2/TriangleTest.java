package ru.cft.shift.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.cft.shift.task2.model.Triangle;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTest {

    @ParameterizedTest(name = "Неккоректное значение конструктора, меньше нуля : Triangle({0},{1},{2})")
    @CsvSource({
            "1, 2, -3",
            "1, 2, 0",
            "1, -3, 2",
            "1, 0, 2",
            "-3, 0, 1 ",
            "0, 1, 2",
            "0, 0, 0"
    })
    void build_fail_less_zero(double a, double b, double c) {
        assertThatThrownBy(() -> new Triangle(a, b, c))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("triangle side must be greater than 0");
    }

    @ParameterizedTest(name = "Неккоректное значение конструктора, не треугольник : Triangle({0},{1},{2})")
    @CsvSource({
            "1, 2, 3",
            "3, 1, 2",
            "1, 5, 10",
            "10, 5, 1",
            "1, 1, 2",
            "1, 1, 3"
    })
    void build_fail_not_triangle(double a, double b, double c) {
        assertThatThrownBy(() -> new Triangle(a, b, c))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("it is not triangle");
    }

    public static Triangle triangle = new Triangle(3, 4, 5);

    @Test
    @DisplayName("тест выходных данных при радиусе 5")
    void getInfo() {
        assertEquals(triangle.getInfo(), """
                Тип фигуры: Треугольник
                Площадь: 6,00 кв.мм
                Периметр: 12,00 мм
                Длина стороны A: 3,00 мм.
                противолежащий угол: 36,87 град.
                Длина стороны B: 4,00 мм.
                противолежащий угол: 53,13 град.
                Длина стороны C: 5,00 мм.
                противолежащий угол: 90,00 град.""");
    }

    @Test
    @DisplayName("тест имени при радиусе 5")
    void getTitle() {
        assertEquals(triangle.getTitle(), "Треугольник");
    }

    @Test
    @DisplayName("тест площади при радиусе 5")
    void getArea() {
        assertEquals(triangle.getArea(), 6.00, 0.01);
    }

    @Test
    @DisplayName("тест периметра при радиусе 5")
    void getPerimeter() {
        assertEquals(triangle.getPerimeter(), 12.00, 0.01);
    }

    @Test
    @DisplayName("тест угла A")
    void getAngleA() {
        assertEquals(triangle.getAngleA(), 36.87, 0.01);
    }

    @Test
    @DisplayName("тест угла B")
    void getAngleB() {
        assertEquals(triangle.getAngleB(), 53.13, 0.01);
    }

    @Test
    @DisplayName("тест угла C")
    void getAngleC() {
        assertEquals(triangle.getAngleC(), 90.00, 0.01);
    }

}