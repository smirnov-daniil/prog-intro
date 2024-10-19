/*************************************************************
 * Copyright (C) 2023
 *    Daniil Smirnov a.k.a. DS2
 *************************************************************/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Reverse {
    public static void main(String[] args) {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        List<IntList> matrix = new ArrayList<>();
        int lss = System.lineSeparator().length();

        int c;
        StringBuilder sb = new StringBuilder();
        IntList line = new IntList();
        try {
            while ((c = bfr.read()) != -1) {
                char ch = (char) c;
                if (lss == 1 && (ch == '\n' || ch == '\r') ||
                        lss == 2 && ch == '\r' && ((char) bfr.read()) == '\n') {
                    if (sb.length() > 0) {
                        line.add(Integer.parseInt(sb.toString()));
                        sb.setLength(0);
                    }
                    matrix.add(line);
                    line = new IntList();
                }
                if (Character.isWhitespace(ch)) {
                    if (sb.length() > 0) {
                        line.add(Integer.parseInt(sb.toString()));
                        sb.setLength(0);
                    }
                } else {
                    sb.append(ch);
                }
            }
        } catch (Exception e) {
            System.err.println("Invalid input.");
        }
        if (sb.length() > 0) {
            line.add(Integer.parseInt(sb.toString()));
            matrix.add(line);
            sb.setLength(0);
        }
        for (int i = matrix.size() - 1; i >= 0; i--) {
            for (int j = matrix.get(i).size() - 1; j >= 0; j--) {
                System.out.print(matrix.get(i).get(j));
                System.out.print(" ");
            }
            System.out.print(System.lineSeparator());
        }
    }
}
