package ru.cft.shift.task4;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;

@Slf4j
@AllArgsConstructor
public class MultiThread implements Calculatable {
    private int numberOProcessorThreads;

    @Override
    public Double calculate(BiFunction<Long, Long, Double> function, Long n) throws ExecutionException, InterruptedException {
        long[] countRange = getCountRange(numberOProcessorThreads, n);

        ExecutorService service = Executors.newFixedThreadPool(numberOProcessorThreads);
        ArrayList<CompletableFuture<Double>> futures = new ArrayList<>(numberOProcessorThreads);

        for (int i = 0; i < countRange.length - 1; i++) {
            final long from = countRange[i];
            final long to = countRange[i + 1];
            futures.add(CompletableFuture
                    .supplyAsync(() -> function.apply(from, to), service));
        }
        service.shutdown();

        double result = 0.0;
        for (CompletableFuture<Double> future : futures) {
            result += future.get();
        }
        return result;
    }

    private long[] getCountRange(int numberOProcessorThreads, long n) {
        long[] countRange = new long[numberOProcessorThreads + 1];
        for (int i = 0; i < numberOProcessorThreads; i++) {
            countRange[i] = n / numberOProcessorThreads * i + 1;
        }
        countRange[numberOProcessorThreads] = n + 1;
        return countRange;
    }
}
