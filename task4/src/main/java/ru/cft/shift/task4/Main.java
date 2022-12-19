package ru.cft.shift.task4;


import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;


@Slf4j
public class Main {
    /**
     * функция 1/(n^2) = (Pi^2)/6
     */
    private static double function(long fromInclusion, long toExclusion) {
        double result = 0;
        for (long i = fromInclusion; i < toExclusion; i++) {
            result += 1.0 / ((double) (i * i));
        }
        if (fromInclusion < toExclusion) {
            log.debug("from {} to {} = {}", fromInclusion, toExclusion - 1, result);
        } else {
            log.debug("nothing to do");
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            long n = getN();

            int availableProcessors = Runtime.getRuntime().availableProcessors();
            double resultExpected = Math.PI * Math.PI / 6;

            double resultMulti = countThreadsTime(new MultiThread(availableProcessors), n, "Multi");
            log.info("Future result: {} delta = {}", resultMulti, Math.abs(resultMulti - resultExpected));

            double resultSingle = countThreadsTime(new SingleThread(), n, "Single");
            log.info("Single result: {} delta = {}", resultSingle, Math.abs(resultSingle - resultExpected));
        } catch (IllegalArgumentException | ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private static long getN() {
        try {
            System.out.println("Enter N:");
            return new Scanner(System.in).nextLong();
        } catch (Exception e) {
            throw new IllegalArgumentException("Input error, please enter a long integer");
        }
    }

    private static double countThreadsTime(Calculatable calculatable, long n, String msg) throws ExecutionException, InterruptedException {
        log.trace("start count{}Thread", msg);
        long m = System.currentTimeMillis();
        double result = calculatable.calculate(Main::function, n);
        log.trace("finish count{}Thread {} millis", msg, System.currentTimeMillis() - m);
        return result;
    }
}
