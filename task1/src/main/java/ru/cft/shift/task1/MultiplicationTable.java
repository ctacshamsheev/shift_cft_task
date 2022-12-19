package ru.cft.shift.task1;

/**
 * Класс позволяет генерировать таблицы умножения любого размера в диапазоне от 1 до 32 включительно
 */
public class MultiplicationTable {

    private final static char VERTICAL_SEPARATOR = '|';
    private final static char HORIZONTAL_SEPARATOR = '-';
    private final static char INTERSECTION_SEPARATOR = '+';
    private final static char SPACE_SEPARATOR = ' ';
    private final static String NEW_LINE = System.lineSeparator();

    private final int n;

    /**
     * Конструктор таблицы
     *
     * @param n размер таблицы
     */
    public MultiplicationTable(int n) {
        if (n <= 0 || n > 32) // ограничение размера по заданию от 1 до 32
            throw new IllegalArgumentException("Invalid argument n. Size must be between 1 and 32");
        this.n = n;
    }

    /**
     * Функция возвращает произведение двух чисел
     *
     * @param i первый множитель
     * @param j второй множитель
     * @return произведение i*j
     */
    public int getMultiplicationIJ(int i, int j) {
        return i * j; // TODO сделать универсальную таблицу, принимающую любые функции, лямбды
    }

    /**
     * Функция определяет необходимое число пробелов для выравнивания
     *
     * @param num текущее число
     * @param max максимальное число, до которого необходимо выравнять
     * @return строку с небоходимым числом пробелов
     */
    private String getSpace(int num, int max) {
        StringBuilder result = new StringBuilder();
        while (max > 0) {
            max /= 10;
            if (num == 0) {
                result.append(SPACE_SEPARATOR);
            }
            num /= 10;
        }
        return result.toString();
    }

    /**
     * Функция определяет количество горизонтальных символов для столбца
     *
     * @param max максимальное число, до которого необходимо выравнять
     * @return строку с небоходимым числом горизонтальных разделителей
     */
    private String getHorizontalPart(int max) {
        StringBuilder horizontalPart = new StringBuilder();
        while (max > 0) {
            max /= 10;
            horizontalPart.append(HORIZONTAL_SEPARATOR);
        }
        return horizontalPart.toString();
    }

    /**
     * Функция получает строку из горизонтальных разделителей таблицы
     *
     * @param maxMul максимальное число, до которого необходимо выравнять
     * @return строку состоящую из горизонтальных разделителей всей таблицы
     */
    private String getHorizontalLine(int maxMul) {
        StringBuilder horizontalLine = new StringBuilder();
        final String MIN_HORIZONTAL_SEPARATOR = getHorizontalPart(n);
        final String MAX_HORIZONTAL_SEPARATOR = getHorizontalPart(maxMul);

        horizontalLine.append(MIN_HORIZONTAL_SEPARATOR);
        for (int j = 1; j <= n; j++) {
            horizontalLine.append(INTERSECTION_SEPARATOR);
            horizontalLine.append(MAX_HORIZONTAL_SEPARATOR);
        }
        return horizontalLine.toString();
    }

    /**
     * Содержимое таблицы
     *
     * @return tаблицу умножения для заданного n
     */
    @java.lang.Override
    public java.lang.String toString() {
        final int MAX_MUL = getMultiplicationIJ(n, n);
        final String HORIZONTAL_LINE = getHorizontalLine(MAX_MUL);

        StringBuilder result = new StringBuilder(getSpace(0, n));
        for (int i = 1; i <= n; i++) {
            result.append(VERTICAL_SEPARATOR);
            result.append(getSpace(i, MAX_MUL));
            result.append(i);
        }
        for (int i = 1; i <= n; i++) {
            result.append(NEW_LINE);
            result.append(HORIZONTAL_LINE).append(NEW_LINE);
            result.append(getSpace(i, n));
            result.append(i);
            for (int j = 1; j <= n; j++) {
                int mul = getMultiplicationIJ(i, j);
                result.append(VERTICAL_SEPARATOR);
                result.append(getSpace(mul, MAX_MUL));
                result.append(mul);
            }
        }
        result.append(NEW_LINE);
        result.append(HORIZONTAL_LINE);
        return result.toString();
    }
}
