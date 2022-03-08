package trivia.models;

import lombok.Data;


@Data
public class Player {

    private long id;
    private final String name;
    private int place;
    private int coins;

    public Player(String name) {
        this.name = name;
    }

    public void move(int steps) {
        place += steps;
    }

    public void addCoins() {
        coins += 1;
    }

    public void checkPosition(int max){

            if (place > 11) {
                place -= max;
            }
        }

    public String toString(){
        return this.name;
    }
}
