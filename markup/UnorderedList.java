package markup;

import java.util.List;

public class UnorderedList extends AbstractList {
    public UnorderedList(List<ListItem> items) {
        super(items);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        super.toMarkdown(stringBuilder, "*");
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        super.toBBCode(stringBuilder, "list");
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        super.toHtml(stringBuilder, "ul");
    }
}
