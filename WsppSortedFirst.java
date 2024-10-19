import java.io.*;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppSortedFirst {
    private static class WordStat {
        public int count;
        public IntList positions;

        public WordStat(int count, IntList positions) {
            this.count = count;
            this.positions = positions;
        }
    }

    private static boolean isWordChar(char c) {
        return Character.isLetter(c) || c == '\'' || Character.getType(c) == Character.DASH_PUNCTUATION;
    }

    private static Map<String, WordStat> getWordsStatFromFile(String filename) {
        Map<String, WordStat> wordsStat = new TreeMap<>();
        MyScanner scanner = new MyScanner(Path.of(filename));
        int wordCnt = 1;
        try {
            Set<String> wasInLine = new HashSet<>();
            while (scanner.hasNextLine()) {
                MyScanner subScanner = new MyScanner(scanner.nextLine());
                while (subScanner.hasNext()) {
                    String token = subScanner.next();

                    int start = -1;
                    for (int i = 0; i < token.length(); i++) {
                        char c = token.charAt(i);
                        if (isWordChar(c) && start == -1) {
                            start = i;
                        }
                        if (start != -1 && (!isWordChar(c) || i == token.length() - 1)) {
                            if (i == token.length() - 1 && isWordChar(c)) {
                                i++;
                            }
                            String word = token.substring(start, i).toLowerCase();
                            WordStat ws = wordsStat.get(word);
                            if (ws == null) {
                                ws = new WordStat(0, new IntList());
                            }
                            ws.count++;
                            if (!wasInLine.contains(word)) {
                                ws.positions.add(wordCnt);
                                wasInLine.add(word);
                            }
                            wordsStat.put(word, ws);
                            wordCnt++;
                            start = -1;
                        }
                    }
                }
                wasInLine.clear();
            }
        } catch (Exception e) {
            System.err.println("Failed to count words.");
        } finally {
            scanner.close();
        }
        return wordsStat;
    }

    private static void printMapToFile(Map<String, WordStat> map, String filename) {
        try (PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, WordStat> entry : map.entrySet()) {
                WordStat val = entry.getValue();
                writer.print(entry.getKey() + " " + val.count);
                for (int i = 0; i < val.positions.size(); i++) {
                    writer.print(" " + val.positions.get(i));
                }
                writer.println();
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
