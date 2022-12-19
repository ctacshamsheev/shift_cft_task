package ru.cft.shift.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.cft.shift.task2.model.Circle;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CircleTest {

    @ParameterizedTest(name = "Неккоректное значение конструктора: {0}")
    @ValueSource(doubles = {-10, -5, 0})
    void build_fail_less_zero(double number) {
        assertThatThrownBy(() -> new Circle(number))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("circle radius must be greater than 0");
    }

    @ParameterizedTest(name = "Корректное значение конструктора: {0}")
    @ValueSource(doubles = {1e-100, 1.0, 2.0, 100, 1000, 10000, 1e10})
    void build_good_more_zero(double number) {
        Circle circle1 = new Circle(number);
        assertEquals(circle1.getDiameter(), number * 2);
        assertEquals(circle1.getRadius(), number);

    }

    private final Circle circle = new Circle(5);

    @Test
    @DisplayName("тест радиуса 5")
    void getRadius() {
        assertEquals(circle.getRadius(), 5);
    }

    @Test
    @DisplayName("тест диаметра при радиусе 5")
    void getDiameter() {
        assertEquals(circle.getDiameter(), 10);
    }

    @Test
    @DisplayName("тест выходных данных при радиусе 5")
    void getInfo() {
        assertEquals(circle.getInfo(), """
                Тип фигуры: Круг
                Площадь: 78,54 кв.мм
                Периметр: 31,42 мм
                Радиус: 5,00 мм
                Диаметр: 10,00 мм""");
        // todo: Радиус: 5 мм int? . или ,
    }

    @Test
    @DisplayName("тест имени при радиусе 5")
    void getTitle() {
        assertEquals(circle.getTitle(), "Круг");
    }

    @Test
    @DisplayName("тест площади при радиусе 5")
    void getArea() {
        assertEquals(circle.getArea(), 78.53, 0.01);
    }

    @Test
    @DisplayName("тест периметра при радиусе 5")
    void getPerimeter() {
        assertEquals(circle.getPerimeter(), 31.42, 0.01);
    }
}