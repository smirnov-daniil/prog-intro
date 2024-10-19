package markup.parser;

import markup.*;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class MarkdownParser implements Parser {
    private static class OpenTag {
        private final String tag;
        private final List<Markup> elements = new ArrayList<>();

        public OpenTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public List<Markup> getElements() {
            return elements;
        }

        public boolean tagEquals(String tag) {
            return this.tag.equals(tag);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof OpenTag other) {
                return tag.equals(other.tag);
            }
            return false;
        }
    }
    private static final Set<String> markdownTags = Set.of("_", "*", "`", "__", "**", "--");
    private static final Set<String> shielded = Set.of("\\_", "\\*");
    private static final String markupBlockSeparator = System.lineSeparator() + System.lineSeparator();
    private static final int markupBlockSeparatorSize = markupBlockSeparator.length();
    private final Stack<OpenTag> openTags = new Stack<>();
    private Map<String, Boolean> openT;

    public MarkdownParser() {
        openT = new HashMap<>();
        for (String markdownTag : markdownTags) {
            openT.put(markdownTag, false);
        }
    }

    private static String getBlockPrefix(String block) {
        if (block.startsWith("#")) {
            int i = 1;
            while (block.charAt(i) == '#') {
                i++;
            }
            if (block.charAt(i) == ' ') {
                i++;
                return block.substring(0, i);
            }
        }

        return "";
    }

    private int processText(String block, int pos) {
        int i = pos;
        while (i < block.length()) {
            if (!markdownTags.contains(block.substring(i, i + 1))) {
                if ((i + 1 < block.length() && markdownTags.contains(block.substring(i, i + 2)))) {
                    break;
                }
                i++;
            } else if (checkSingle(block, i)) {
                break;
            } else {
                i++;
            }
        }
        return i;
    }

    private boolean checkSingle(String block, int pos) {
        String tag = block.substring(pos, pos + 1);
        if (!markdownTags.contains(tag)) {
            return false;
        }
        boolean isSameOpenTag = openTags.peek().tagEquals(tag);
        String shieldedTag = block.substring(Math.max(pos - 1, 0), pos + 1);
        if (shielded.contains(shieldedTag)) {
            return false;
        }
        if (isSameOpenTag && !Character.isWhitespace(block.charAt(pos - 1))) {
            return true;
        }
        if (pos + 1 == block.length()) {
            return false;
        }
        if (!isSameOpenTag && !Character.isWhitespace(block.charAt(pos + 1))) {
            return true;
        }
        return false;
    }

    private Markup toMarkup(String tag, List<Markup> children) throws IllegalArgumentException {
        return switch (tag) {
            case "__", "**" -> new Strong(children);
            case "_", "*" -> new Emphasis(children);
            case "--" -> new Strikeout(children);
            case "`" -> new Code(children);
            default -> throw new IllegalArgumentException("Unknown tag " + tag);
        };
    }

    private Block parseBlock(String block) throws IllegalArgumentException {
        String prefix = getBlockPrefix(block);
        openTags.push(new OpenTag(""));
        int pos = prefix.length();
        while (pos < block.length()) {
            if (pos + 1 < block.length() && markdownTags.contains(block.substring(pos, pos + 2))) {
                String tag = block.substring(pos, pos + 2);
                if (openTags.peek().tagEquals(tag)) {
                    OpenTag last = openTags.pop();
                    openTags.peek().getElements().add(toMarkup(tag, last.getElements()));
                } else {
                    openTags.push(new OpenTag(tag));
                }
                pos += 2;
            } else if (checkSingle(block, pos)) {
                String tag = block.substring(pos, pos + 1);
                if (openTags.peek().tagEquals(tag)) {
                    OpenTag last = openTags.pop();
                    openTags.peek().getElements().add(toMarkup(last.getTag(), last.getElements()));
                } else {
                    openTags.push(new OpenTag(tag));
                }
                pos++;
            } else {
                int textEnd = processText(block, pos);
                String text = block.substring(pos, textEnd);
                text = text.
                        replace("&", "&amp;").
                        replace("<", "&lt;").
                        replace(">", "&gt;").
                        replace("\\_", "_").
                        replace("\\*", "*");
                openTags.peek().getElements().add(new Text(text));
                pos = textEnd;
            }
        }
        if (openTags.size() != 1) {
            throw new IllegalArgumentException("Invalid block");
        }
        if (!prefix.isEmpty()) {
            return new Header(openTags.pop().getElements(), prefix.length() - 1);
        }
        return new Paragraph(openTags.pop().getElements());
    }

    void smartReplace(List<StringBuilder> stringBuilders, String from, String to) {
        String open = "<" + to + ">";
        String close = "</" + to + ">";

        if ("_".equals(from)) {
            int count = 0;
            for (StringBuilder stringBuilder : stringBuilders) {
                for (int i = 0; i < stringBuilder.length(); i++) {
                    if (stringBuilder.charAt(i) == '_') {
                        if (i > 0 && stringBuilder.charAt(i - 1) == '\\') {
                            continue;
                        }
                        count++;
                    }
                }
            }
            if (count == 1) {
                return;
            }
        }
        for (StringBuilder stringBuilder : stringBuilders) {
            boolean shield = false;
            int pos = 0;
            while (pos + from.length() <= stringBuilder.length()) {
                if (stringBuilder.charAt(pos) == '\\') {
                    if ("*".equals(from) || "\\".equals(from) || "`".equals(from)) {
                        shield = true;
                    }
                    pos++;
                    continue;
                }
                if (stringBuilder.substring(pos, pos + from.length()).equals(from) && !shield) {
                    if (openT.get(from)) {
                        stringBuilder.replace(pos, pos + from.length(), close);
                        pos += close.length();
                        openT.put(from, false);
                    } else {
                        stringBuilder.replace(pos, pos + from.length(), open);
                        pos += open.length();
                        openT.put(from, true);
                    }
                } else {
                    pos++;
                    shield = false;
                }
            }
        }
    }

    private int findNextCode(String block, int pos) {
        int count = 0;
        while (count < 3 && pos < block.length()) {
            if (block.charAt(pos) == '`') {
                count++;
            } else if (count != 0) {
                count = 0;
            }
            pos++;
        }
        return pos;
    }

    private String parseBlock2(String block) throws IllegalArgumentException {
        String prefix = getBlockPrefix(block);
        int pos = 0;
        int next = 0;
        boolean pre = false;
        String postfix = block.substring(prefix.length());
        StringBuilder stringBuilder =
                new StringBuilder(postfix);
        StringBuilder result = new StringBuilder();

        List<String> preParts = new ArrayList<>();
        List<StringBuilder> nonPreParts = new ArrayList<>();

        while (pos < stringBuilder.length()) {
            next = findNextCode(postfix, pos);
            if (!pre) {
                if (next == stringBuilder.length()) {
                    next += 3;
                }
                nonPreParts.add(new StringBuilder(stringBuilder.substring(pos, next - 3).
                                    replace("&", "&amp;").
                                    replace("<", "&lt;").
                                    replace(">", "&gt;")));
                pre = true;
            } else {
                preParts.add(stringBuilder.substring(pos, next - 3));
                pre = false;
            }
            pos = next;
        }
        smartReplace(nonPreParts, "__", "strong");
        smartReplace(nonPreParts, "**", "strong");
        if (getCount(stringBuilder, nonPreParts, '_') > 1) {
            smartReplace(nonPreParts, "_", "em");
        }
        if (getCount(stringBuilder, nonPreParts, '*') > 1) {
            smartReplace(nonPreParts, "*", "em");
        }
        smartReplace(nonPreParts, "--", "s");
        smartReplace(nonPreParts, "`", "code");
        Iterator<String> prePartsIterator = preParts.iterator();
        for (StringBuilder nonPrePart : nonPreParts) {
            result.append(nonPrePart.toString().
                    replace("\\_", "_").
                    replace("\\*", "*").
                    replace("\\`", "`"));
            if (prePartsIterator.hasNext()) {
                result.append("<pre>").append(prePartsIterator.next()).append("</pre>");
            }
        }
        if (!prefix.isEmpty()) {
            String level = String.valueOf(prefix.length() - 1);
            return "<h" + level + ">" + result + "</h" + level + ">";
        }
        return "<p>" + result + "</p>";
    }

    private int getCount(StringBuilder stringBuilder, List<StringBuilder> nonPreParts, char val) {
        int count = 0;
        for (StringBuilder nonPrePart : nonPreParts) {
            for (int i = 0; i < stringBuilder.length(); i++) {
                if (stringBuilder.charAt(i) == val) {
                    if (!(i > 0 && stringBuilder.charAt(i - 1) == '\\')) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private List<String> splitToBlocs(BufferedReader reader) throws IOException {
        int c;
        int curBlSep = 0;
        StringBuilder block = new StringBuilder();
        List<String> blocks = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (!block.isEmpty() && !line.isBlank()) {
                block.append(System.lineSeparator());
            }
            block.append(line);
            if (line.isBlank()) {
                String blockStr = block.toString();
                if (!blockStr.isBlank()) {
                    blocks.add(blockStr);
                }
                block.setLength(0);
            }
        }
        if (!block.isEmpty()) {
            blocks.add(block.toString());
        }
        return blocks;
    }

    @Override
    public Document parse(Path file) throws FileNotFoundException, IOException, IllegalArgumentException {
        List<Block> blocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            for (String block : splitToBlocs(reader)) {
                blocks.add(parseBlock(block));
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + file.getFileName() + " not found");
        } catch (IOException e) {
            throw new IOException("Failed to read file.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid file content.");
        }
        return new Document(blocks);
    }

    public List<String> parse2(Path file) throws FileNotFoundException, IOException, IllegalArgumentException { // NOTE: parse2
        List<String> blocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            for (String block : splitToBlocs(reader)) {
                blocks.add(parseBlock2(block));
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + file.getFileName() + " not found.");
        } catch (IOException e) {
            throw new IOException("Failed to read file.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid file content.");
        }
        return blocks;
    }
}
