package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @co-author DS2
 */
public interface Player {
    Move move(final Position position, final Cell cell);
}
