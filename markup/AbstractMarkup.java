package markup;

import java.util.List;

public abstract class AbstractMarkup implements Markup {
    protected final List<Markup> elements;

    public AbstractMarkup(List<Markup> elements) {
        this.elements = elements;
    }

    protected void toMarkdown(StringBuilder stringBuilder, String border) {
        stringBuilder.append(border);
        for (Markdown element : elements) {
            element.toMarkdown(stringBuilder);
        }
        stringBuilder.append(border);
    }

    protected void toBBCode(StringBuilder stringBuilder, String tag) {
        stringBuilder.append("[").append(tag).append("]");
        for (BBCode element : elements) {
            element.toBBCode(stringBuilder);
        }
        stringBuilder.append("[/").append(tag).append("]");
    }

    protected void toHtml(StringBuilder stringBuilder, String tag) {
        stringBuilder.append("<").append(tag).append(">");
        for (Html element : elements) {
            element.toHtml(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append(">");
    }
}
