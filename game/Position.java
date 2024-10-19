package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @co-author DS2
 */
public interface Position {
    boolean isValid(final Move move);

    Cell getCell(final int r, final int c);
}
