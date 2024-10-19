package markup;

import java.util.List;

public class OrderedList extends AbstractList {
    public OrderedList(List<ListItem> items) {
        super(items);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        super.toMarkdown(stringBuilder, "1.");
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        super.toBBCode(stringBuilder, "list=1");
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        super.toHtml(stringBuilder, "ol");
    }
}
