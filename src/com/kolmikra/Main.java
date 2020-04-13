package com.kolmikra;

import com.kolmikra.stream.StreamUtil;
import com.kolmikra.stream.WarAndPeace;

import javax.sound.midi.SoundbankResource;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long start;
        long stop;
        List<String> testList = new ArrayList<>(Arrays.asList(
                "always", "never", "also",
                "just", "only", "again",
                "often", "still", "already",
                "almost", "enough", "very",
                "now", "usually",
                "quickly", "slowly"));


        String pathTestToken = new File("").getAbsolutePath() + "\\src\\com\\kolmikra\\resources\\testToken.txt";
        String pathTestFrequent = new File("").getAbsolutePath() + "\\src\\com\\kolmikra\\resources\\testFrequentWords.txt";
        String pathTestVowels = new File("").getAbsolutePath() + "\\src\\com\\kolmikra\\resources\\testVowels.txt";


        System.out.println("=========AvgLength===========");
        System.out.println(StreamUtil.getAvgLength(testList) + "\n");
        System.out.println("=========All strings with max length===========");
        System.out.println(StreamUtil.maxLengthStrings(testList) + "\n");
        System.out.println("=========First 100 tokens===========");
        System.out.println(StreamUtil.findFirstHundredTokens(pathTestToken) + "\n");
        System.out.println("=======Words with five distinct vowels==========");
        System.out.println(Arrays.toString(StreamUtil.findWordsWithVowels(pathTestVowels)) + "\n");
        System.out.println("=======10 most frequent words==========");
        System.out.println(StreamUtil.findTenMostFrequentWords(pathTestFrequent) + "\n");
        System.out.println("Avg with reduce: " + StreamUtil.getAvgWithReduce());


        System.out.println("Use Collectors.flatMapping");
        StreamUtil.useFlatMapping();

        start = System.nanoTime();
        StreamUtil.primeNumbers();
        stop = System.nanoTime();
        System.out.println("\n" + "Sequential :" + (stop - start) + " ns");
        System.out.println(StreamUtil.primeNumbers());
        start = System.nanoTime();
        StreamUtil.primeNumbersParallel();
        stop = System.nanoTime();
        System.out.println("Parallel :" + (stop - start) + " ns");
        System.out.println(StreamUtil.primeNumbersParallel());

    }
}
