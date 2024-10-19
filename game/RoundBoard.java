package game;

public class RoundBoard extends MNKBoard {
    public RoundBoard() {
        this(10, 5);
    }

    public RoundBoard(final int d, final int k) {
        super(d, d, k);
        int maxMoves = 0;
        final float center = ((float) d) / 2;
        final float r2 = center * center;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                float x = (0.5f + i - center) * (0.5f + i - center);
                float y = (0.5f + j - center) * (0.5f + j - center);
                if (x + y <= r2) {
                    maxMoves++;
                } else {
                    cells[i][j] = Cell.Q;
                }
            }
        }
        super.setMaxMoves(maxMoves);
    }

}
