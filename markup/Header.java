package markup;

import java.util.List;

public class Header implements Block {
    private List<Markup> elements;
    private final int level;

    public Header(List<Markup> elements, int level) {
        this.elements = elements;
        this.level = level;
    }

    public Header(List<Markup> elements) {
        this.elements = elements;
        this.level = 0;
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        stringBuilder.append("[b]");
        for (BBCode element : elements) {
            element.toBBCode(stringBuilder);
        }
        stringBuilder.append("[/b]").append(System.lineSeparator());
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.
                append("<h").append(level).append(">");
        for (Html element : elements) {
            element.toHtml(stringBuilder);
        }
        stringBuilder.
                append("</h").append(level).append(">").
                append(System.lineSeparator());
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (int i = 0; i < level; i++) {
            stringBuilder.append("#");
        }
        stringBuilder.append(" ");
        for (Markdown element : elements) {
            element.toMarkdown(stringBuilder);
        }
        stringBuilder.append(System.lineSeparator());
    }
}
