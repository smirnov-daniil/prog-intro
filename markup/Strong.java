package markup;

import java.util.List;

public class Strong extends AbstractMarkup {
    public Strong(List<Markup> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        toMarkdown(stringBuilder, "__");
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        toBBCode(stringBuilder, "b");
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        toHtml(stringBuilder, "strong");
    }
}
