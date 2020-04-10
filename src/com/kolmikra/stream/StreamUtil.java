package com.kolmikra.stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtil {
    public static double getAvgLength(List<String> src) {
        return src.stream().mapToInt(String::length).average().orElse(0.0);
    }

    public static List<String> maxLengthStrings(List<String> src) {
        List<String> maxLengthStrings = src.stream().collect(Collectors.groupingBy(String::length))
                .entrySet().stream().max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue).orElse(Collections.emptyList());

        return maxLengthStrings;
    }

    public static List<BigInteger> primeNumbers() {
        String number = Stream.iterate("1", n -> n + "0").limit(50).reduce((s1, s2) -> s2).orElse("");
        List<BigInteger> primeNumbers = Stream.iterate(new BigInteger(number), n -> n.add(BigInteger.ONE))
                .filter(b -> b.isProbablePrime(1))
                .limit(500)
                .collect(Collectors.toList());
        return primeNumbers;
    }

    public static List<BigInteger> primeNumbersParallel() {
        String number = Stream.iterate("1", n -> n + "0").limit(50).reduce((s1, s2) -> s2).orElse("");
        List<BigInteger> primeNumbers = Stream.iterate(new BigInteger(number), n -> n.add(BigInteger.ONE))
                .parallel()
                .filter(b -> b.isProbablePrime(1))
                .limit(500)
                .collect(Collectors.toList());
        return primeNumbers;
    }

    public static double getAvgWithReduce() {
        final AtomicInteger count = new AtomicInteger();
        Stream<Double> randomsDouble = Stream.generate(Math::random).limit(100);
        Double avg = randomsDouble.reduce(0.0, (x, y) -> {
            int number = count.incrementAndGet();
            return (x * (number - 1) + y) / number;
        });
        return avg;
    }

    public static List<String> findFirstHundredTokens(String path) {
        List<String> tokens = new ArrayList<>();
        try {
            Stream<String> words = Pattern.compile(" ").splitAsStream(Files.readString(Paths.get(path)));
            tokens = words.filter(StreamUtil::isToken).limit(100).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

    public static void useFlatMapping() {
        List<Optional<String>> listOfOptionals = Arrays.asList(
                Optional.empty(), Optional.of("not Empty"), Optional.empty(), Optional.of("not Empty"));

        List<String> filteredList = listOfOptionals.stream().collect(Collectors.flatMapping(Optional::stream, Collectors.toList()));
        System.out.println(filteredList);
    }

    private static boolean isToken(String str) {
        return str.codePoints().allMatch(Character::isAlphabetic);
    }

    public static List<String> findTenMostFrequentWords(String path) {
        List<String> tenMostFrequentWords = new ArrayList<>();
        try {
            Stream<String> words = Pattern.compile("\\PL+").splitAsStream(Files.readString(Paths.get(path)));
            Map<String, Integer> frequentCount = words.collect(Collectors.toMap(String::toLowerCase, w -> 1, Integer::sum));
            tenMostFrequentWords = frequentCount.entrySet()
                    .stream()
                    .sorted((x, y) -> y.getValue() == x.getValue()
                            ? x.getKey().compareTo(y.getKey())
                            : (y.getValue() - x.getValue()))
                    .limit(10)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tenMostFrequentWords;
    }

    public static String[] findWordsWithVowels(String path) {
        String[] wordsWithVowels = new String[0];
        try {
            Stream<String> words = Pattern.compile("\\PL+").splitAsStream(Files.readString(Paths.get(path)));
            wordsWithVowels = words.filter(StreamUtil::stringHasFiveVowels).toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsWithVowels;
    }

    private static boolean stringHasFiveVowels(String str) {
        int count = 0;
        for (int i = 1; i < str.length() - 1; i++) {
            if (isVowel(str.charAt(i)) && !isVowel(str.charAt(i + 1)) && !isVowel(str.charAt(i - 1))) {
                count++;
            }
        }
        if (isVowel(str.charAt(str.length() - 1)))
            count++;
        if (isVowel(str.charAt(0)))
            count++;
        return count == 5;
    }

    private static boolean isVowel(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch == 'A' || ch == 'E' || ch == 'I' ||
                ch == 'O' || ch == 'U');
    }
}
