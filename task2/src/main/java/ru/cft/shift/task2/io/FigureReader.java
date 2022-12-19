package ru.cft.shift.task2.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task2.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class FigureReader {
    private static final Logger logger = LoggerFactory.getLogger(FigureReader.class.getName());

    public static Figure getFigure(String fileName) {
        return (fileName == null) ? getFigureFromConsole() : getFigureFromFile(fileName);
    }

    /**
     * статический метод позволяет получить экземпляр фигуры, из входного файла, либо консоли
     *
     * @param fileName имя входного файла, либо null для чтения из консоли
     * @return экземпляр фигуры
     */
    private static Figure getFigureFromFile(String fileName) {
        logger.trace("getFigureFromFile({})", ((fileName == null) ? "CONSOLE" : fileName));
        try (Scanner scanner = new Scanner(new File(Objects.requireNonNull(fileName)))) {
            if (!scanner.hasNext()) {
                throw new IllegalArgumentException("file: " + fileName + " empty");
            }
            return getFigure(scanner, false);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("file: " + fileName + " not found");
        }
    }

    private static Figure getFigureFromConsole() {
        logger.trace("getFigureFromConsole()");
        try (Scanner scanner = new Scanner(System.in)) {
            return getFigure(scanner, true);
        }
    }

    private static Figure getFigure(Scanner scanner, boolean isConsoleInput) {
        if (isConsoleInput) System.out.println("Enter figure type (CIRCLE, RECTANGLE, TRIANGLE):");
        FigureType figureType = FigureType.valueOf(scanner.next());
        try {
            switch (figureType) {
                case CIRCLE -> {
                    if (isConsoleInput) System.out.println("Enter radius:");
                    return new Circle(scanner.nextDouble());
                }
                case RECTANGLE -> {
                    if (isConsoleInput) System.out.println("Enter length and width:");
                    return new Rectangle(scanner.nextDouble(), scanner.nextDouble());
                }
                case TRIANGLE -> {
                    if (isConsoleInput) System.out.println("Enter a b c length:");
                    return new Triangle(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
                }
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("error in input value, must be double number!");
        }
        throw new IllegalArgumentException("Something wrong in reader");
    }
}
