package md2html;

import markup.Document;
import markup.parser.MarkdownParser;
import markup.parser.Parser;

import java.io.*;
import java.nio.file.Path;

public class Md2Html {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Invalid input. Usage: In Out");
            return;
        }
        MarkdownParser parser = new MarkdownParser();
        try {
            try (Writer writer = new BufferedWriter(new FileWriter(args[1]))) {
                for (String s : parser.parse2(Path.of(args[0]))) {
                    writer.write(s);
                    writer.write(System.lineSeparator());
                }
            } catch (IOException e) {
                System.err.println("Error while writing to file: " + args[1]);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }
}
