import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class MyScanner {
    private static final int EOLSize = System.lineSeparator().length();
    private BufferedReader reader;
    private String nextToken, nextLine;
    private int nextInt, nextIntHexAbc;
    private boolean isNextInt, isNextIntHexAbc, isNextLine, isNewLine;

    public MyScanner(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        nextToken = "";
        nextLine = "";
        isNextInt = false;
        isNextIntHexAbc = false;
        isNextLine = false;
        isNewLine = false;
    }

    public MyScanner(Path path) {
        try {
            reader = new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("File not found: " + path.getFileName());
        }
        nextToken = "";
        nextLine = "";
        isNextInt = false;
        isNextIntHexAbc = false;
        isNextLine = false;
        isNewLine = false;
    }

    public MyScanner(String str) {
        reader = new BufferedReader(new StringReader(str));
        nextToken = "";
        nextLine = "";
        isNextInt = false;
        isNextIntHexAbc = false;
        isNextLine = false;
        isNewLine = false;
    }

    private void skipWhitespaces() {
        final int lookAhead = 512;
        try {
            int c;
            int lengthSkip = 0;
            reader.mark(lookAhead);
            while ((c = reader.read()) != -1 && Character.isWhitespace((char) c)) {
                if (Character.getType((char) c) == Character.LINE_SEPARATOR ||
                        (EOLSize == 1 && (char) c == '\r') || (char) c == '\n') {
                    isNewLine = true;
                }
                lengthSkip++;
                if (lookAhead == lengthSkip) {
                    reader.mark(lookAhead);
                    lengthSkip = 0;
                }
            }
            reader.reset();
            final long skipped = reader.skip(lengthSkip);
        } catch (IOException e) {
            System.err.println("I/O exception occurred.");
            System.err.println(e.getMessage());
        }
    }

    private String nextToken(TokenValidator tokenValidator) {
        StringBuilder token = new StringBuilder();
        int c;
        int lookAhead = 0, length = 0;
        final int bufferSize = 512;
        char[] buffer = new char[bufferSize];
        try {
            skipWhitespaces();
            reader.mark(bufferSize);
            while ((c = reader.read()) != -1 && tokenValidator.run((char) c)) {
                lookAhead++;
                length++;
                if (lookAhead == bufferSize) {
                    reader.reset();
                    int charRead = reader.read(buffer);
                    if (charRead == -1) {
                        break;
                    }
                    token.append(buffer);
                    length = lookAhead = 0;
                    reader.mark(bufferSize);
                }
            }
            if (length > 0) {
                reader.reset();
                int charRead = reader.read(buffer, 0, length);
                if (charRead != -1) {
                    token.append(buffer, 0, charRead);
                }
            }
        } catch (IOException e) {
            System.err.println("I/O exception occurred.");
            System.err.println(e.getMessage());
        }
        return token.toString();
    }

    private String nextLine1() throws Exception {
        StringBuilder token = new StringBuilder();
        int c;
        int lookAhead = 0, length = 0;
        final int bufferSize = 2048;
        char[] buffer = new char[bufferSize];
        try {
            reader.mark(bufferSize);
            while (true) {
                lookAhead++;
                length++;

                c = reader.read();
                if (c == -1) {
                    throw new Exception("Failed to read line.");
                }
                if (Character.getType((char) c) == Character.LINE_SEPARATOR) {
                    break;
                }
                if (EOLSize == 1 && ((char) c == '\n' || (char) c == '\r')) {
                    break;
                }
                if (EOLSize == 2 && (char) c == '\r') {
                    c = reader.read();
                    if (c == -1) {
                        throw new Exception("Failed to read line.");
                    }
                    if ((char) c == '\n') {
                        length++;
                        break;
                    }
                }
                if (lookAhead == bufferSize) {
                    reader.reset();
                    int charRead = reader.read(buffer);
                    if (charRead == -1) {
                        break;
                    }
                    token.append(buffer);
                    length = lookAhead = 0;
                    reader.mark(bufferSize);
                }
            }
            if (length > 0) {
                reader.reset();
                int charRead = reader.read(buffer, 0, length);
                if (charRead != -1) {
                    token.append(buffer, 0, charRead);
                }
            }
        } catch (IOException e) {
            throw new Exception("Failed to read line.");
        }
        return token.toString();
    }

    public boolean isNewLine() {
        if (isNewLine) {
            isNewLine = false;
            return true;
        }
        return false;
    }

    public boolean hasNextLine() {
        try {
            if (!isNextLine) {
                nextLine = nextLine();
                isNextLine = true;
            }
        } catch (Exception e) {
            isNextLine = false;
        }
        return isNextLine;
    }
    public boolean hasNext() {
        if (!nextToken.isEmpty() || isNextInt || isNextIntHexAbc || isNextLine) {
            return true;
        }
        nextToken = next();
        return !nextToken.isEmpty();
    }

    public String nextLine() throws Exception {
        if (isNextLine) {
            String line = String.valueOf(nextLine);
            isNextLine = false;
            nextLine = "";
            return line;
        }
        return nextLine1();
    }

    public String next() {
        if (!nextToken.isEmpty()) {
            String token = String.valueOf(nextToken);
            nextToken = "";
            return token;
        }
        if (isNextInt) {
            isNextInt = false;
            return String.valueOf(nextInt);
        }
        if (isNextIntHexAbc) {
            isNextIntHexAbc = false;
            return String.valueOf(nextInt);
        }
        return nextToken(MyScanner.TokenValidator::token);
    }
    public boolean hasNextInt() {
        try {
            if (isNextInt) {
                return true;
            }
            nextInt = nextInt();
            isNextInt = true;
        } catch (Exception e) {
            isNextInt = false;
        }
        return isNextInt;
    }

   public int nextInt() throws Exception {
        try {
            if (!nextToken.isEmpty()) {
                int integer = Integer.parseInt(nextToken);
                nextToken = "";
                return integer;
            }
            if (isNextInt) {
                isNextInt = false;
                return nextInt;
            }
            return Integer.parseInt(nextToken(TokenValidator::integer));
        } catch (NumberFormatException e) {
            throw new Exception("Invalid token request.");
        }
    }

    public boolean hasNextIntHexAbc() {
        try {
            if (isNextIntHexAbc) {
                return true;
            }
            nextIntHexAbc = nextIntHexAbc();
            isNextIntHexAbc = true;
        } catch (Exception e) {
            isNextIntHexAbc = false;
        }
        return isNextIntHexAbc;
    }

    public int nextIntHexAbc() throws Exception {
        if (!nextToken.isEmpty()) {
            int integer = parseIntHexAbc(nextToken);
            nextToken = "";
            return integer;
        }
        if (isNextIntHexAbc) {
            isNextIntHexAbc = false;
            return nextIntHexAbc;
        }
        return parseIntHexAbc(nextToken(MyScanner.TokenValidator::intHexAbc));
    }

    private int parseIntHexAbc(String token) throws Exception {
        try {
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
        } catch (NumberFormatException e) {
            throw new Exception("Invalid token.");
        }
        throw new Exception("Invalid token.");
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Failed to close.");
            System.err.println(e.getMessage());
        }
    }

    private interface TokenValidator {
        boolean run(char c);

        private static boolean token(char c) {
            return !Character.isWhitespace(c) && Character.getType(c) != Character.SPACE_SEPARATOR && Character.getType(c) != Character.LINE_SEPARATOR;
        }

        private static boolean integer(char c) {
            return c == '-' || Character.digit(c, 10) != -1;
        }

        private static boolean intHexAbc(char c) {
            return c == '-' || Character.digit(c, 20) != -1 || c == 'x' || c == 'X';
        }
    }
}
