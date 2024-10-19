package markup;

import java.util.List;

public class ListItem implements Element {
    private final List<Itemable> elements;
    public ListItem(List<Itemable> elements) {
        this.elements = elements;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (Markdown element : elements) {
            element.toMarkdown(stringBuilder);
        }
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        stringBuilder.append("[*]");
        for (BBCode element : elements) {
            element.toBBCode(stringBuilder);
        }
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append("<li>");
        for (Html element : elements) {
            element.toHtml(stringBuilder);
        }
        stringBuilder.append("</li>");
    }
}
