package trivia.game;

import trivia.models.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManagement {

    private Player active;

    private List<Player> players = new ArrayList<>();

    public void addOne(Player player){
        if(active == null){
            active = player;
        }
        players.add(player);
    }

    public Player getActive() { // how about "getCurrentPlayer()" ?
        return active;
    }

    public int totalNumber() {
        return players.size();
    }

    public void nextPlayer() {
        int activePlayerIndex = players.indexOf(active);
        if (activePlayerIndex == totalNumber() - 1) {
            active = players.get(0);
        } else {
            active = players.get(activePlayerIndex + 1);
        }
    }
}
