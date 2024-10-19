package markup;

import java.util.List;

public abstract class AbstractList implements Itemable, Block {
    protected final List<ListItem> items;

    public AbstractList(List<ListItem> items) {
        this.items = items;
    }

    protected void toMarkdown(StringBuilder stringBuilder, String itemTag) {
        for (Markdown item : items) {
            stringBuilder.append(itemTag).append(" ");
            item.toMarkdown(stringBuilder);
        }
    }

    protected void toBBCode(StringBuilder stringBuilder, String tag) {
        stringBuilder.append("[").append(tag).append("]");
        for (BBCode item : items) {
            item.toBBCode(stringBuilder);
        }
        stringBuilder.append("[/list]");
    }

    protected void toHtml(StringBuilder stringBuilder, String tag) {
        stringBuilder.append("<").append(tag).append(">");
        for (Html item : items) {
            item.toHtml(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append(">");
    }
}
