package game;

import java.util.Arrays;
import java.util.Map;

/**
 * @author DS2
 */
public class MNKBoard implements Board {

    private class MNKPosition implements Position {
         private final String firstLine;
         private final int ws;

         public MNKPosition(int ws, String firstLine) {
             this.ws = ws;
             this.firstLine = firstLine;
         }

         @Override
        public boolean isValid(final Move move) {
            return 0 <= move.row() && move.row() < m
                    && 0 <= move.column() && move.column() < n
                    && getCell(move.row(), move.column()) == Cell.E
                    && turn == move.value();
        }

        @Override
        public Cell getCell(final int r, final int c) {
            return cells[r][c];
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(firstLine);
            for (int r = 0; r < m; r++) {
                sb.append(System.lineSeparator());
                sb.append(r);
                sb.append(" ".repeat(ws - (String.valueOf(r)).length() + 1));
                for (int c = 0; c < n; c++) {
                    sb.append(SYMBOLS.get(cells[r][c]));
                    sb.append(" ".repeat(ws));
                }
            }
            return sb.toString();
        }
    }
    final Position position;
    private boolean isAdditionalMove = false;
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.Q, ' '
    );

    protected final Cell[][] cells;
    private final int m, n, k;
    private int maxMoves;

    int moves;
    private Cell turn;

    public MNKBoard() {
        this(3, 3, 3);
    }

    public MNKBoard(final int m, final int n, final int k) {
        this.m = Math.max(m, 1);
        this.n = Math.max(n, 1);
        this.k = Math.max(k, 1);
        this.maxMoves = m * n;
        this.moves = 0;
        this.cells = new Cell[this.m][this.n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        this.turn = Cell.X;

        final int ws = (String.valueOf(Math.max(m, n))).length();

        final StringBuilder sb = new StringBuilder(" ".repeat(ws + 1));
        for (int i = 0; i < n; i++) {
            sb.append(i);
            sb.append(" ".repeat(ws - (String.valueOf(i)).length() + 1));
        }
        this.position = new MNKPosition(ws, sb.toString());
    }

    protected void setMaxMoves(final int maxMoves) {
        this.maxMoves = maxMoves;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private int getIn(final Move move, final int xDir, final int yDir) {
        int x = move.column(), y = move.row();
        int inPos = 0;
        while (inPos < k && x < m && y < n && x >= 0 && y >= 0 && cells[y][x] == turn) {
            x += xDir;
            y += yDir;
            inPos++;
        }
        x = move.column();
        y = move.row();
        int inNeg = 0;
        while (inNeg < k && x < m && y < n && x >= 0 && y >= 0 && cells[y][x] == turn) {
            x -= xDir;
            y -= yDir;
            inNeg++;
        }
        if (inPos == 4 && inNeg == 1 ||
                inPos == 1 && inNeg == 4) {
            isAdditionalMove = true;
        } else if (inPos + inNeg > 4 && inPos <= 4 && inNeg <= 4) {
            isAdditionalMove = true;
        }
        return inPos + inNeg - 1;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!position.isValid(move)) {
            return Result.LOSE;
        }

        cells[move.row()][move.column()] = move.value();
        int
                diag1 = getIn(move, 1, 1),
                diag2 = getIn(move, 1, -1),
                row = getIn(move, 1, 0),
                col = getIn(move, 0, 1);
        if (diag1 == k
                || diag2 == k
                || row == k
                || col == k) {
            return Result.WIN;
        }
        if (!isAdditionalMove) {
            turn = turn == Cell.X ? Cell.O : Cell.X;
        }
        isAdditionalMove = false;

        if (++moves >= maxMoves) {
            return Result.DRAW;
        }

        return Result.UNKNOWN;
    }
}
