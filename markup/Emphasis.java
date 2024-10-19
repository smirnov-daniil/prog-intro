package markup;

import java.util.List;

public class Emphasis extends AbstractMarkup {
    public Emphasis(List<Markup> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        super.toMarkdown(stringBuilder, "*");
    }
    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        super.toBBCode(stringBuilder, "i");
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        super.toHtml(stringBuilder, "em");
    }
}
