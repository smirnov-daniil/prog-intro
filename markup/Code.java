package markup;

import java.util.List;

public class Code extends AbstractMarkup {
    public Code(List<Markup> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        super.toMarkdown(stringBuilder, "`");
    }
    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        super.toBBCode(stringBuilder, "code");
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        super.toHtml(stringBuilder, "code");
    }
}
