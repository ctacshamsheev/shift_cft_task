package ru.cft.shift.task2.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * класс парсит аргументы командной строки
 * позволяет получить значение входного и выходного файла,
 * либо null в случае отсутствия
 */
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class.getName());
    String inputFile = null;
    String outputFile = null;

    public Parser(String[] args) {
        logger.trace("Parser({})", Arrays.stream(args).reduce((x, y) -> x + " " + y));
        if (args.length % 2 != 0 || args.length > 4) {
            logger.error("Use: -i inputFile.txt -o outputFile.txt");
            throw new IllegalArgumentException("Use: -i inputFile.txt -o outputFile.txt");
        }

        for (int i = 0; i < args.length; i += 2) {
            if (args[i + 1].charAt(0) == '-') {
                throw new IllegalArgumentException("Use: -i inputFile.txt -o outputFile.txt");
            } else if (args[i].equals("-i")) {
                inputFile = args[i + 1];
            } else if (args[i].equals("-o")) {
                outputFile = args[i + 1];
            } else {
                logger.error("Unknown flag: " + args[i]);
                throw new IllegalArgumentException("Unknown flag: " + args[i] + " Use: -i inputFile.txt -o outputFile.txt");
            }
        }
        logger.debug("input file: " + ((inputFile == null) ? "CONSOLE" : inputFile) + " output file: " + ((outputFile == null) ? "CONSOLE" : outputFile));
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

}
