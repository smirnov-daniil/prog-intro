import java.nio.file.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wspp {
    private static boolean isWordChar(char c) {
        return Character.isLetter(c) || c == '\'' || Character.getType(c) == Character.DASH_PUNCTUATION;
    }

    private static Map<String, IntList> getWordsStatFromFile(String filename) {
        Map<String, IntList> wordsStat = new LinkedHashMap<>();
        MyScanner scanner = new MyScanner(Path.of(filename));
        int wordCnt = 1;
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
                        String word = token.substring(start, i).toLowerCase();
                        IntList il = wordsStat.getOrDefault(word, new IntList());
                        il.add(wordCnt++);
                        wordsStat.put(word, il);
                        start = -1;
                    }
                }
                if (start != -1) {
                    String word = token.substring(start).toLowerCase();
                    IntList il = wordsStat.getOrDefault(word, new IntList());
                    il.add(wordCnt++);
                    wordsStat.put(word, il);
                }
            }
        } catch (Exception e) {
            System.err.println("Invalid input.");
        }
        scanner.close();
        return wordsStat;
    }

    private static void printMapToFile(Map<String, IntList> map, String filename) {
        try (PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, IntList> entry : map.entrySet()) {
                IntList val = entry.getValue();
                writer.print(entry.getKey() + " " + val.size());
                for (int i = 0; i < val.size(); i++) {
                    writer.print(" " + val.get(i));
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