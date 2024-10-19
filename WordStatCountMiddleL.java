/*************************************************************
 * Copyright (C) 2023
 *    Daniil Smirnov a.k.a. DS2
 *************************************************************/

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class WordStatCountMiddleL {
    private static boolean isWordChar(char c) {
        return Character.isLetter(c) || c == '\'' || Character.getType(c) == Character.DASH_PUNCTUATION;
    }

    private static Map<String, Integer> getWordsStatFromFile(String filename) {
        Map<String, Integer> wordsStat = new LinkedHashMap<>();

        MyScanner scanner = new MyScanner(Path.of(filename));
        try {
            while (scanner.hasNext()) {
                String token = scanner.next();
                int start = -1;
                for (int i = 0; i < token.length(); i++) {
                    char c = token.charAt(i);
                    if (isWordChar(c)) {
                        if (start == -1) {
                            start = i;
                        }
                    } else if (start != -1) {
                        if (i - start >= 5) {
                            String word = token.substring(start + 2, i - 2).toLowerCase();
                            wordsStat.put(word, wordsStat.getOrDefault(word, 0) + 1);
                        }
                        start = -1;
                    }
                }
                if (start != -1 && token.length() - start >= 5) {
                    String word = token.substring(start + 2, token.length() - 2).toLowerCase();
                    wordsStat.put(word, wordsStat.getOrDefault(word, 0) + 1);
                }
            }
        } catch (Exception e) {
            System.err.println("Invalid input.");
        }
        scanner.close();
        return wordsStat;
    }

    private static void printMapToFile(Map<String, Integer> map, String filename) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((e1, e2) -> e1.getValue().compareTo(e2.getValue()));
        try (PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> entry : list) {
                writer.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Provide input and output file names as arguments");
            return;
        }
        printMapToFile(getWordsStatFromFile(args[0]), args[1]);
    }
}
