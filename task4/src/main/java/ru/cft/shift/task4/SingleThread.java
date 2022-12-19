package ru.cft.shift.task4;

import lombok.NoArgsConstructor;

import java.util.function.BiFunction;

@NoArgsConstructor
public class SingleThread implements Calculatable {
    @Override
    public Double calculate(BiFunction<Long, Long, Double> function, Long n) {
        return function.apply(1L, n + 1);
    }
}
