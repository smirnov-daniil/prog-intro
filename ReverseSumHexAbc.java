/*************************************************************
 * Copyright (C) 2023
 *    Daniil Smirnov a.k.a. DS2
 *************************************************************/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ReverseSumHexAbc {
    private static int parseIntHexAbc(String token) {
        if (token.length() > 2 && token.charAt(0) == '0' &&
                (token.charAt(1) == 'x' || token.charAt(1) == 'X')) {
            return Integer.parseUnsignedInt(token.substring(2), 16);
        }
        if (!token.isEmpty()) {
            int i = 0;
            boolean is_minus = false;
            if (token.charAt(i) == '-') {
                i++;
                is_minus = true;
            }
            if (Character.digit(token.charAt(i), 10) == -1) {
                StringBuilder sb = new StringBuilder(token.length());
                if (is_minus) {
                    sb.append('-');
                }
                for (; i < token.length(); i++) {
                    sb.append(Character.digit(token.charAt(i), 20) - 10);
                }
                token = sb.toString();
            }
            return Integer.parseInt(token, 10);
        }
        return 0;
    }
    private static List<IntList> parseInput() {
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
                        line.add(parseIntHexAbc(sb.toString()));
                        sb.setLength(0);
                    }
                    matrix.add(line);
                    line = new IntList();
                }
                if (Character.isWhitespace(ch)) {
                    if (sb.length() > 0) {
                        line.add(parseIntHexAbc(sb.toString()));
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
            line.add(parseIntHexAbc(sb.toString()));
            matrix.add(line);
            sb.setLength(0);
        }
        return matrix;
    }

    private static String toABC(int num) {
        final String alph = "abcdefghij";
        String numStr = String.valueOf(num);
        StringBuilder sb = new StringBuilder(numStr.length());
        int i = 0;
        if (numStr.charAt(i) == '-') {
            sb.append('-');
            i++;
        }
        for (; i < numStr.length(); i++) {
            sb.append(alph.charAt(numStr.charAt(i) - '0'));
        }
        return sb.toString();
    }
    private static void makeRectSumAndPrint(List<IntList> matrix) {
        IntList sumCol = new IntList(matrix.get(0).size());
        for (IntList intList : matrix) {
            int prefSum = 0;
            for (int j = 0; j < intList.size(); j++) {
                if (sumCol.size() <= j) {
                    sumCol.add(intList.get(j));
                } else {
                    sumCol.set(j, sumCol.get(j) + intList.get(j));
                }
                prefSum += sumCol.get(j);
                System.out.print(toABC(prefSum));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        makeRectSumAndPrint(parseInput());
    }
}
