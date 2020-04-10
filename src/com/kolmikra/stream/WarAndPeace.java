package com.kolmikra.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarAndPeace {
    public static List<String> largestWordsSequential(String path) {
        List<String> largestWords = new ArrayList<>();
        try {
            Stream<String> words = Pattern.compile("\\PL+").splitAsStream(Files.readString(Paths.get(path)));
            largestWords = words.collect(Collectors.groupingBy(String::length))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<Integer, List<String>>comparingByKey().reversed())
                    .map(Map.Entry::getValue)
                    .flatMap(Collection::stream)
                    .limit(500)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return largestWords;
    }

    public static List<String> largestWordsParallel(String path) {
        List<String> largestWords = new ArrayList<>();
        try {
            Stream<String> words = Pattern.compile("\\PL+").splitAsStream(Files.readString(Paths.get(path)));
            largestWords = words.parallel().collect(Collectors.groupingBy(String::length))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<Integer, List<String>>comparingByKey().reversed())
                    .map(Map.Entry::getValue)
                    .flatMap(Collection::stream)
                    .limit(500)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return largestWords;
    }
}
