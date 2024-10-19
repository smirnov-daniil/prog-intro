/*************************************************************
 * Copyright (C) 2023
 *    Daniil Smirnov a.k.a. DS2
 *************************************************************/

public class Sum {
    public static void main(String[] args) {
        int result = eval(args);
        System.out.println(result);
    }

    private static int eval(String[] expr) {
        int summary = 0;
        for (String s : expr) {
            boolean isNumber = false;
            int start = 0;
            for (int k = 0; k < s.length(); k++) { // Replace all whitespaces with whitespace
                if (!Character.isWhitespace(s.charAt(k)) && Character.getType(s.charAt(k)) != Character.SPACE_SEPARATOR && s.charAt(k) != '+') {
                    if (!isNumber) {
                        start = k;
                        isNumber = true;
                    }
                } else if (isNumber) {
                    summary += Integer.parseInt(s.substring(start, k));
                    isNumber = false;
                }
            }
            if (isNumber) {
                summary += Integer.parseInt(s.substring(start));
            }
        }
        return summary;
    }
}
