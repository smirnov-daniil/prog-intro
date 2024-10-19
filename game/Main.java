package game;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("d, k, p");
            return;
        }
        int d, k, p;
        try {
            d = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
            p = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid d, k, p");
            return;
        }

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < p; i++) {
            players.add(new RandomPlayer(d, d));
        }

        Tournament RoundTournament = new Tournament(players) {
            @Override
            public Board getNewBoard() {
                return new RoundBoard(d, k);
            }
        };

        RoundTournament.run();
    }
}
