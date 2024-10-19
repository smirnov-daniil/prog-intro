package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @co-author DS2
 */
public class SequentialPlayer implements Player {
    private final int m, n;

    public SequentialPlayer(final int m, final int n) {
        this.m = m;
        this.n = n;
    }

    public SequentialPlayer() {
        this(3, 3);
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
