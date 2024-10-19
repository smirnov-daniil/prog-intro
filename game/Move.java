package game;

/**
 * @author DS2
 */
public record Move(int row, int column, Cell value) {

    @Override
    public String toString() {
        return "row=" + row + ", column=" + column + ", value=" + value;
    }
}
