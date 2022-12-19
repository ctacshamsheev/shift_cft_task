package ru.cft.shift.task4;

import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

public interface Calculatable {
    Double calculate(BiFunction<Long, Long, Double> function, Long n) throws ExecutionException, InterruptedException;
}
