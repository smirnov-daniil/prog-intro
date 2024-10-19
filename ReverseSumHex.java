/*************************************************************
 * Copyright (C) 2023
 *    Daniil Smirnov a.k.a. DS2
 *************************************************************/

/*************************************************************
 * File name   : ReverseSumHex.java
 * Purpose     : ReverseSumHex HW3 mod.
 * Programmer  : DS2.
 * Created     : 19.09.2023.
 * Last update : 19.09.2023.
 *************************************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReverseSumHex {
    private static List<IntList> parseInput() {
        Scanner scanner = new Scanner(System.in);
        List<IntList> matrix = new ArrayList<>();

        while (scanner.hasNextLine()) {
            Scanner subScanner = new Scanner(scanner.nextLine());
            IntList line = new IntList();

            while (subScanner.hasNext()) {
                line.add(Integer.parseUnsignedInt(subScanner.next(), 16));
            }
            subScanner.close();
            line.shrinkToFit();
            matrix.add(line);
        }
        return matrix;
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
                System.out.print(Integer.toHexString(prefSum));
                System.out.print(" ");
            }
            System.out.print("\n");
        }
   /*     for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                int sum = matrix.get(i).get(j);
                int upperRect = i - 1;
                int leftRect = j - 1;

                if (j > 0) {
                    sum += matrix.get(i).get(leftRect);
                }
                while (upperRect >= 0 && matrix.get(upperRect).size() <= j) {
                    upperRect--;
                }
                if (upperRect >= 0) {
                    sum += matrix.get(upperRect).get(j);
                    if (j > 0) {
                        sum -= matrix.get(upperRect).get(leftRect);
                    }
                }
                matrix.get(i).set(j, sum);
                System.out.print(Integer.toHexString(sum));
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    */    /*
        for (ArrayList<Integer> integers : sumMatrix) {
            for (Integer integer : integers) {
                String hex = Integer.toHexString(integer);
                System.out.print(hex);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
        */
    }
    public static void main(String[] args) {
        makeRectSumAndPrint(parseInput());
    }
}

/* END OF 'ReverseSumHex.java' FILE. */
