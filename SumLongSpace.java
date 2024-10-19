/*************************************************************
 * Copyright (C) 2023
 *    Daniil Smirnov a.k.a. DS2
 *************************************************************/

public class SumLongSpace {
    public static void main(String[] args) {
        long result = eval(args);
        System.out.println(result);
    }

    private static long eval(String[] expr) {
        long summary = 0;

        for (String s : expr) {
            boolean isNumber = false;
            int start = 0;
            for (int k = 0; k < s.length(); k++) {
                if (!(Character.getType(s.charAt(k)) == Character.SPACE_SEPARATOR || s.charAt(k) == '+')) {
                    if (!isNumber) {
                        start = k;
                        isNumber = true;
                    }
                } else if (isNumber) {
                    summary += Long.parseLong(s.substring(start, k));
                    isNumber = false;
                }
            }
            if (isNumber) {
                summary += Long.parseLong(s.substring(start));
            }
        }
        return summary;
    }
}
