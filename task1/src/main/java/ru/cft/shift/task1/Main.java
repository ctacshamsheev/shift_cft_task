package ru.cft.shift.task1;

public class Main {

    public static void main(String[] args) throws IllegalArgumentException {
        try (ConsoleReader reader = new ConsoleReader(System.in)) {
            System.out.println(new MultiplicationTable(reader.getNextInt("Enter size of multiplication table:")));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
