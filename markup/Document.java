package markup;

import java.util.List;

public class Document implements Element {
    private final List<Block> blocks;

    public Document(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        for (BBCode block : blocks) {
            block.toBBCode(stringBuilder);
        }
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        for (Html block : blocks) {
            block.toHtml(stringBuilder);
        }
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (Markdown block : blocks) {
            block.toMarkdown(stringBuilder);
        }
    }
}
