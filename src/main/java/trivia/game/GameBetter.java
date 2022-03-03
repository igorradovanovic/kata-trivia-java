package trivia.game;


import trivia.models.Player;
import trivia.models.Questions;
import trivia.types.QuestionCategory;

import java.util.ArrayList;
import java.util.List;

import static trivia.util.CustomLogger.log;

// REFACTORED
//missed two important abstractions:
public class GameBetter implements IGame {
    List<Player> players = new ArrayList<>();
    Questions questions;
    QuestionCategory category;

    int currentPlayerIndex = 0;
    int maxGameSteps = 12;
    boolean isGettingOutOfPenaltyBox = true;

    public GameBetter() {
        this.questions = new Questions();
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));
        log(playerName + " was added");
        log("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        if (!isPlayable()) {
            log("Not enough players to start the game!");
            return;
        }
        play(roll);
    }


    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean gameFinished;
        if (currentPlayer.isInPenaltyBox()) {
            return checkIfIsGettingOut(currentPlayer);
        } else {
            log("Answer was correct!!!!");
            currentPlayer.addCoins();
            log(currentPlayer.getName() + " now has " + currentPlayer.getCoins() + " Gold Coins.");
            gameFinished = !isGameFinished(currentPlayer);
            nextPlayer();
            return gameFinished;
        }
    }

    public boolean wrongAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.setInPenaltyBox(true);
        log("Question was incorrectly answered");
        log(currentPlayer.getName() + " was sent to the penalty box");
        nextPlayer();
        return true;
    }


    private boolean isGameFinished(Player currentPlayer) {
        return currentPlayer.getCoins() == 6;
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
        }
    }

    private void askQuestion(QuestionCategory category) {
        log("The category is " + category.getLabel());
        log(questions.showNextQuestion(category));
    }

    private boolean isPlayable() {
        return players.size() >= 2;
    }

    private boolean checkIfIsGettingOut(Player player) {
        if (isGettingOutOfPenaltyBox) {
            log("Answer was correct!!!!");
            player.addCoins();
            log(player.getName() + " now has " + player.getCoins() + " Gold Coins.");
            boolean gameFinished = !isGameFinished(player);
            nextPlayer();
            return gameFinished;
        } else {
            nextPlayer();
            return true;
        }
    }

    private boolean isRolledNumberEven(int roll) {
        return roll % 2 == 0;
    }

    //consider all the knowledge it has:
    //the rule for getting out of jail (even or odd number)
    //specific of tracking active player, (index and that they are stored in players)
    //details of getting current category, that it is stored in questions, and picked by place of a current player
    //TODO add Jail class
    //try moving some of these things to its own methods, or its own classes
    //can you spot what's one class that's missing here (hint: it is in the picture)
    private void play(int roll) {

        boolean moveFromJail = !isRolledNumberEven(roll);
        Player currentPlayer = players.get(currentPlayerIndex);
        log(currentPlayer.getName() + " is the current player");
        log("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            jailAction(currentPlayer, roll, moveFromJail);
            return;
        }
        currentPlayer.move(roll);
        if (currentPlayer.getPlace() > 11) {
            currentPlayer.setPlace(currentPlayer.getPlace() - maxGameSteps);
        }
        category = questions.getCurrentCategory(currentPlayer.getPlace());
        log(currentPlayer.getName() + "'s new location is " + currentPlayer.getPlace());
        askQuestion(category);

    }

    //not descriptive name
    //one would expect that Jail has to do with Jail responsibilities,
    //   not moving player on the board or pulling question from the deck
    private void jailAction(Player player, int roll, boolean moveFromJail) {

        if (!moveFromJail) {
            log(player.getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
            return;
        }

        isGettingOutOfPenaltyBox = true;
        log(player.getName() + " is getting out of the penalty box");
        player.move(roll);
        if (player.getPlace() > 11) {
            player.setPlace(player.getPlace() - maxGameSteps);
        }
        category = questions.getCurrentCategory(player.getPlace());
        log(player.getName() + "'s new location is " + player.getPlace());
        askQuestion(category);
    }
}
