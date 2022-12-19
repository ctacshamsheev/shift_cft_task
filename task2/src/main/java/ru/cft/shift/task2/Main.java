package ru.cft.shift.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task2.io.FigureReader;
import ru.cft.shift.task2.io.FigureWriter;
import ru.cft.shift.task2.io.Parser;
import ru.cft.shift.task2.model.Figure;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * для запуска приложения, в аргументы командной строки необходимо передать
     * следующие параметры входного и выходного файла, используя соответствующие необязательные ключи:
     * -i inputFileName входной файл или считывание из консоли в случае отсутствия
     * -o outputFileName выходной файл или запись в консоль в случае отсутствия
     * например: "-i in.txt -o out.txt"
     * Если пропустить флаг, то вместо него считываение или записи будет происходить в консоль
     * например: "-i in.txt"  считывает файл in.txt и печатает результат в консоль
     * например: "-o out.txt"  считывает параметры из консоли и печатает результат в файл out.txt
     * при запуске без параметров, считываение и вывод будут происходить через консоль.
     *
     * @param args аргументы командной строки например: "-i in.txt -o out.txt"
     */
    public static void main(String[] args) {
        logger.trace("start");
        try {
            Parser parser = new Parser(args);
            Figure figure = FigureReader.getFigure(parser.getInputFile());
            FigureWriter.write(parser.getOutputFile(), figure);

            logger.debug(figure.getInfo().replace("\n", " "));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.trace("stop");
    }
}
