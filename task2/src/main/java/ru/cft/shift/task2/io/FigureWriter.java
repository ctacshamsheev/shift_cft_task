package ru.cft.shift.task2.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task2.model.Figure;

import java.io.FileWriter;
import java.io.IOException;

public class FigureWriter {
    private static final Logger logger = LoggerFactory.getLogger(FigureWriter.class.getName());

    /**
     * статический метод позволяет записать информацию о фигуре, в файл, либо вывести на консоль
     *
     * @param outputFile имя файла, либо null для вывода на консоль
     * @param figure     фигура
     */
    public static void write(String outputFile, Figure figure) {
        logger.trace("FigureWriter({})", ((outputFile == null) ? "CONSOLE" : outputFile));
        if (outputFile == null)
            logger.info("{}", figure.getInfo());
            //System.out.println(figure.getInfo());
        else
            try (FileWriter writer = new FileWriter(outputFile, false)) {
                writer.write(figure.getInfo());
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("figure is null");
            }
    }
}
