package markup;

import java.util.List;
public class Strikeout extends AbstractMarkup {
    public Strikeout(List<Markup> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        toMarkdown(stringBuilder, "~");
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        toBBCode(stringBuilder, "s");
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        toHtml(stringBuilder, "s");
    }
}
