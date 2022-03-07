package trivia.game;


import trivia.models.Player;
import trivia.types.QuestionCategory;
import static trivia.util.CustomLogger.log;

// REFACTORED
public class GameBetter implements IGame {
    public static final int WINNING_COINS = 6;
    public static final int QUESTIONS_NUMBER = 50;
    QuestionManagement questions = new QuestionManagement();
    Jail jail = new Jail();
    Board board = new Board();
    PlayerManagement players = new PlayerManagement();

    public GameBetter() {
        this.questions.generateRandom(QUESTIONS_NUMBER);
    }

    public boolean add(String playerName) {
        players.addOne(new Player(playerName));
        log(playerName + " was added");
        log("They are player number " + players.totalNumber());
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
        Player currentPlayer = players.getActive();
        boolean gameFinished;
        if (currentPlayer.isInPenaltyBox()) {
            return checkIfIsGettingOut(currentPlayer);
        } else {
            log("Answer was correct!!!!");
            currentPlayer.addCoins();
            log(currentPlayer.getName() + " now has " + currentPlayer.getCoins() + " Gold Coins.");
            gameFinished = !isGameFinished(currentPlayer);
            players.nextPlayer();
            return gameFinished;
        }
    }

    public boolean wrongAnswer() {
        Player currentPlayer = players.getActive();
        currentPlayer.setInPenaltyBox(true);
        log("Question was incorrectly answered");
        jail.addPrisoner(currentPlayer);
        players.nextPlayer();
        return true;
    }

    private boolean isGameFinished(Player currentPlayer) {
        return currentPlayer.getCoins() == WINNING_COINS;
    }

    private void askQuestion(QuestionCategory category) {
        log("The category is " + category.getLabel());
        log(questions.showNextQuestion(category));
    }

    private boolean isPlayable() {
        return players.totalNumber() >= 2;
    }

    private boolean checkIfIsGettingOut(Player player) {
        if (jail.isPlayerIsGettingOut()) {
            log("Answer was correct!!!!");
            player.addCoins();
            log(player.getName() + " now has " + player.getCoins() + " Gold Coins.");
            boolean gameFinished = !isGameFinished(player);
            players.nextPlayer();
            return gameFinished;
        } else {
            players.nextPlayer();
            return true;
        }
    }

    //TODO refactor
    private void play(int roll) {

        Player currentPlayer = players.getActive();
        log(currentPlayer.getName() + " is the current player");
        log("They have rolled a " + roll);

        if (!currentPlayer.isInPenaltyBox()) {
            nextStep(currentPlayer,roll);
            return;
        }


        if(jail.tryToGetOut(currentPlayer,roll)){
            nextStep(currentPlayer,roll);
        }

    }

    private void nextStep(Player player, int roll){
        player.move(roll);
        player.checkPosition(Board.PLACES);
        log(player.getName() + "'s new location is " + player.getPlace());
        askQuestion(board.getCurrentCategory(player.getPlace()));
    }

}
