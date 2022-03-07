package trivia.game;

import lombok.Data;
import trivia.models.Player;


import java.util.HashSet;
import java.util.Set;

import static trivia.util.CustomLogger.log;

@Data
public class Jail {

    private Set<Player> prisoners = new HashSet<>();
    private boolean playerIsGettingOut;

    public boolean hasImprisoned(Player player) {
        return prisoners.contains(player);
    }

    public void addPrisoner(Player prisoner){
        prisoners.add(prisoner);
        log(prisoner.getName() + " was sent to the penalty box");
    }

    public void removePrisoner(Player prisoner){
        prisoners.remove(prisoner);
        log(prisoner.getName() + " is getting out of the penalty box");
    }

    public boolean tryToGetOut(Player player, int roll) {
        if (this.releaseCondition(roll)) {
            this.removePrisoner(player);
            this.playerIsGettingOut = true;
            return true;
        } else {
            log(player + " is not getting out of the penalty box");
            this.playerIsGettingOut = false;
            return false;
        }
    }

    public boolean releaseCondition(int roll) {
        return roll % 2 != 0;
    }
}
