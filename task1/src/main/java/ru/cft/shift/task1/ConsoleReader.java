package ru.cft.shift.task1;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Класс позволяет выводить приглашение на ввод и считывать целые числа
 */
public class ConsoleReader implements AutoCloseable {
    Scanner sc;

    /**
     * Конструктор, открывает поток на чтение
     *
     * @param in входной поток
     */
    public ConsoleReader(InputStream in) {
        sc = new Scanner(in);
    }

    /**
     * Функция выводит приглашение на ввод, и считывает целое число из потока
     *
     * @param msg сообщение пользователю
     * @return целое число из потока
     */
    public int getNextInt(String msg) {
        if (msg != null) System.out.println(msg);
        try {
            return sc.nextInt();
        } catch (NoSuchElementException | IllegalStateException e) {
            throw new IllegalArgumentException("Invalid argument. Enter number");
        }
    }

    /**
     * Функция закрвает поток, переопределение метода close интерфейса AutoCloseable.
     */
    @Override
    public void close() {
        sc.close();
    }
}
