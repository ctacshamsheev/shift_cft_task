package ru.cft.shift.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.cft.shift.task2.model.Rectangle;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleTest {

    @ParameterizedTest(name = "Неккоректное значение конструктора, меньше нуля : Rectangle({0},{1})")
    @CsvSource({
            "2, -3",
            "2, 0",
            "0, 2",
            "-3, 0",
            "0, 0"
    })
    void build_fail_less_zero(double a, double b) {
        assertThatThrownBy(() -> new Rectangle(a, b))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rectangle side must be greater than 0");
    }

    private final Rectangle rectangle = new Rectangle(5, 6);

    @Test
    @DisplayName("тест длинны 6")
    void getLength() {
        assertEquals(rectangle.getLength(), 6);
    }

    @Test
    @DisplayName("тест ширины 5")
    void getWidth() {
        assertEquals(rectangle.getWidth(), 5);
    }

    @Test
    void getDiagonal() {
        assertEquals(rectangle.getDiagonal(), 7.81, 0.01);
    }

    @Test
    void getInfo() {
        assertEquals(rectangle.getInfo(), """
                Тип фигуры: Прямоугольник
                Площадь: 30,00 кв.мм
                Периметр: 22,00 мм
                Длина диагонали: 7,81 мм
                Длина: 6,00 мм
                Ширина: 5,00 мм""");
    }

    @Test
    void getTitle() {
        assertEquals(rectangle.getTitle(), "Прямоугольник");
    }

    @Test
    void getArea() {
        assertEquals(rectangle.getArea(), 30.0, 0.01);
    }

    @Test
    void getPerimeter() {
        assertEquals(rectangle.getPerimeter(), 22.0, 0.01);
    }
}