package com.kolmikra;

import com.kolmikra.stream.WarAndPeace;

import java.io.File;

public class TestWarAndPeace {
    public static void main(String[] args) {
        long start;
        long stop;
        String pathWarAndPeace = new File("").getAbsolutePath() + "\\src\\com\\kolmikra\\resources\\War_and_Peace.txt";

        start = System.nanoTime();
        WarAndPeace.largestWordsSequential(pathWarAndPeace);
        stop = System.nanoTime();
        System.out.println("Sequential stream: " + (stop - start) + " ns");
        System.out.println(WarAndPeace.largestWordsSequential(pathWarAndPeace));
        start = System.nanoTime();
        WarAndPeace.largestWordsParallel(pathWarAndPeace);
        stop = System.nanoTime();
        System.out.println("Parallel stream:   " + (stop - start) + " ns");
        System.out.println(WarAndPeace.largestWordsParallel(pathWarAndPeace));
    }

}
