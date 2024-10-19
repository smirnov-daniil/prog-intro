package game;

import java.util.*;


/**
 * @author DS2
 */
abstract public class Tournament {
    public enum State {
        Unknown, Finished, Ongoing
    }

    private record IndexedPlayer(Player player, int index) {}

    private State state;
    private List<IndexedPlayer> players;
    private final Map<Integer, Integer> tournamentTable;
    private final Random random = new Random();

    public Tournament(final List<Player> players) {
        this.players = new ArrayList<>(players.size());
        for (int i = 0; i < players.size(); i++) {
            this.players.add(new IndexedPlayer(players.get(i), i + 1));
        }

        this.tournamentTable = new HashMap<>();
        for (int i = 0; i < players.size(); i++) {
            tournamentTable.put(i + 1, 0);
        }
        this.state = State.Unknown;
    }

    private State play() {
        if (state == State.Finished) {
            return state;
        }
        if (players.size() == 1) {
            return state = State.Finished;
        }
        if (state == State.Unknown) {
            state = State.Ongoing;
        }
        Collections.shuffle(players, random);
        ArrayList<IndexedPlayer> winners = new ArrayList<>();
        for (int i = 0; i < players.size() - 1; i += 2) {
            IndexedPlayer p1 = players.get(i), p2 = players.get(i + 1);
            final Game game = new Game(false, p1.player(), p2.player());
            int result;
            do {
                result = game.play(getNewBoard());
                switch (result) {
                    case 1 -> {
                        tournamentTable.put(p1.index(), tournamentTable.get(p1.index()) + 1);
                        winners.add(p1);
                    }
                    case 2 -> {
                        tournamentTable.put(p2.index(), tournamentTable.get(p2.index()) + 1);
                        winners.add(p2);
                    }
                }
            } while (result == 0);
        }
        if (players.size() % 2 == 1) {
            winners.add(players.get(players.size() - 1));
        }
        players = winners;
        return state;
    }

    public void run() {
        while (play() == State.Ongoing) {
            System.out.println(tournamentTable);
        }
        System.out.println("============");
        List<Map.Entry<Integer, Integer>> table = new ArrayList<>(tournamentTable.entrySet());
        Comparator<Map.Entry<Integer, Integer>> comparator = (c1, c2) -> c2.getValue().compareTo(c1.getValue());
        table.sort(comparator);

        int place = 0, prevWins = -1;
        for (var player : table) {
            if (prevWins != player.getValue()) {
                place++;
                prevWins = player.getValue();
                System.out.println("Place " + place + " (" + prevWins + " wins): ");
            }
            System.out.println("Player " + player.getKey());
        }

    }

    protected abstract Board getNewBoard();
}
