package markup;

import java.util.List;

public class Paragraph implements Itemable, Block {
    private final List<Markup> elements;
    public Paragraph(List<Markup> elements) {
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
        for (BBCode element : elements) {
            element.toBBCode(stringBuilder);
        }
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append("<p>");
        for (Html element : elements) {
            element.toHtml(stringBuilder);
        }
        stringBuilder.append("</p>").append(System.lineSeparator());
    }
}
