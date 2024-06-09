package model;

import java.io.Serializable;
import java.util.Vector;

public class Leaderboard implements Serializable {

    private static long serialVersionUID;

    private Vector<Player> players;

    public Leaderboard() {

    }

    public Leaderboard(Vector<Player> players) {
        this.players = players;
    }


    public void add(Player player) {
        players.add(player);
    }



}
