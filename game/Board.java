package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @co-author DS2
 */
public interface Board {
    Position getPosition();
    Cell getCell();
    Result makeMove(final Move move);
}
