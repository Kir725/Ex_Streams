package com.kolmikra.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarAndPeace {
    private static List<String> largestWords = new ArrayList<>();

    public static List<String> largestWordsSequential(String path) {
        try {
            Stream<String> words = Pattern.compile("\\PL+").splitAsStream(Files.readString(Paths.get(path)));
            largestWords = getLargestWords(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return largestWords;
    }

    public static List<String> largestWordsParallel(String path) {
        try {
            Stream<String> wordsParallel = Pattern.compile("\\PL+").splitAsStream(Files.readString(Paths.get(path))).parallel();
            largestWords = getLargestWords(wordsParallel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return largestWords;
    }

    private static List<String> getLargestWords(Stream<String> words){
        return words
                .collect(Collectors.groupingBy(String::length))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, List<String>>comparingByKey().reversed())
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .limit(500)
                .distinct()
                .collect(Collectors.toList());
    }
}
