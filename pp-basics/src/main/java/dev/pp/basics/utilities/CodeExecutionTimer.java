package dev.pp.basics.utilities;

import dev.pp.basics.annotations.NotNull;

import java.util.concurrent.Callable;

public class CodeExecutionTimer {

/*
    public static void writeMilliseconds (
        long startNanoseconds,
        @NotNull String label ) {

        long finish = System.nanoTime();
        long nanoSeconds = finish - startNanoseconds;
        long milliseconds = nanoSeconds / 1000 / 1000;
        System.out.println ( label + ": " + milliseconds + " milliseconds");
    }
 */

    public static void writeMilliseconds (
        long startNanoseconds,
        long stopNanoseconds,
        @NotNull String label ) {

        long nanoSeconds = stopNanoseconds - startNanoseconds;
        long milliseconds = nanoSeconds / 1000 / 1000;
        System.out.println ( label + ": " + milliseconds + " milliseconds");
    }


    public static void measureAndWriteMilliseconds (
        @NotNull Runnable runnable,
        @NotNull String label ) {

        long milliseconds = measureMilliseconds ( runnable );
        System.out.println ( label + ": " + milliseconds + " milliseconds");
    }

    public static void measureAndWriteMicroseconds (
        @NotNull Runnable runnable,
        @NotNull String label ) {

        long microseconds = measureMicroseconds ( runnable );
        System.out.println ( label + ": " + microseconds + " microseconds");
    }

    public static long measureMilliseconds ( @NotNull Runnable runnable ) {
        return measureMicroseconds ( runnable ) / 1000;
    }

    public static long measureMicroseconds ( @NotNull Runnable runnable ) {
        return measureNanoseconds ( runnable ) / 1000;
    }

    public static long measureNanoseconds ( @NotNull Runnable runnable ) {

        long start = System.nanoTime();
        runnable.run();
        long finish = System.nanoTime();
        return finish - start;
    }

/*
    public static <T> T measureNanoseconds ( @NotNull Callable<T> callable ) {

        long start = System.nanoTime();
        T result = callable.call();
        long finish = System.nanoTime();
        return finish - start;
    }
 */
}
