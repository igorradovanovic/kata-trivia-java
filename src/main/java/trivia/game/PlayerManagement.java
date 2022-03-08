package trivia.game;

import trivia.models.Player;
import java.util.ArrayList;
import java.util.List;

public class PlayerManagement {

    private Player active;
    private long id = 1;
    private List<Player> players = new ArrayList<>();

    public void addOne(Player player){
        player.setId(id);
        players.add(player);
        id++;
    }

    public Player getCurrentPlayer() {
        checkId();
        players.stream()
                .forEach(player->{
                    if(player.getId() == id) active = player;
                });
        return active;
    }

    public int totalNumber() {
        return players.size();
    }

    public void nextPlayer() {
        id++;
        players.stream()
                .forEach(player -> {
                    if(player.getId() == id) active = player;
                });
    }

    private long checkId(){
        return id > totalNumber() ? id = 1 : id;
    }
}
