package ru.cft.shift.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.cft.shift.task2.io.Parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParserTest {

    @ParameterizedTest(name = "Неккоректное значение агрументов входной строки: {0}")
    @ValueSource(strings = {
            "-i -o out.txt",
            "-i in.txt -o",
            "-i -o",
            "-i",
            "-o",
            "-i in.txt -o -e",
            "-p -p -p",
            "-p -p",
            "-p"

    })
    void build_fail_with_error(String args) {
        System.out.println(args);
        assertThatThrownBy(() -> new Parser(args.split(" ")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Use: -i inputFile.txt -o outputFile.txt");
    }

    @ParameterizedTest(name = "Неккоректное значение агрументов входной строки: {0}")
    @ValueSource(strings = {
            "-e in -o out.txt",
            "-i in.txt -e out",
            "-e in ",
            "-e out"
    })
    void build_fail_with_error_two(String args) {
        System.out.println(args);
        assertThatThrownBy(() -> new Parser(args.split(" ")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown flag: -e");
    }


    @Test
    @DisplayName("тест корректных имен")
    void getFileInOut() {
        String[] args = {"-i", "in", "-o", "out"};
        final Parser parser = new Parser(args);
        assertThat(parser.getInputFile()).isEqualTo("in");
        assertThat(parser.getOutputFile()).isEqualTo("out");
    }

    @Test
    @DisplayName("тест пустых имен чтение и записи в консоль")
    void getNullFile() {
        String[] args = {};
        final Parser parser = new Parser(args);
        assertThat(parser).hasAllNullFieldsOrProperties();
    }

    @Test
    @DisplayName("тест консольный ввод, вывод в файл")
    void getNullInFileOut() {
        String[] args = {"-i", "in"};
        final Parser parser = new Parser(args);
        assertThat(parser.getInputFile()).isEqualTo("in");
        assertThat(parser.getOutputFile()).isNull();
    }

    @Test
    @DisplayName("тест консольный вывод, чтение из файла")
    void getFileInNullOut() {
        String[] args = {"-o", "out"};
        final Parser parser = new Parser(args);
        assertThat(parser.getInputFile()).isNull();
        assertThat(parser.getOutputFile()).isEqualTo("out");
    }
}