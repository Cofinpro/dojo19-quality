package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.adaptionsoft.games.uglytrivia.QuestionCategory.*;

public class Game {
    private List<Player> players = new ArrayList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    private final Consumer<String> outputConsumer;
    private final QuestionRepository questionRepository;

    public Game(Consumer<String> outputConsumer, QuestionRepository questionRepository) {
        this.outputConsumer = outputConsumer;
        this.questionRepository = questionRepository;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public Player add(String playerName) {


        Player player = new Player(playerName);
        players.add(player);

        println(playerName + " was added");
        println("They are player number " + players.size());
        return player;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        println(players.get(currentPlayer) + " is the current player");
        println("They have rolled a " + roll);
        Player player = players.get(currentPlayer);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                println(players.get(currentPlayer) + " is getting out of the penalty box");
                int newPosition = (player.getPosition() + roll) % 12;
                player.setPosition(newPosition);

                println(players.get(currentPlayer)
                        + "'s new location is "
                        + player.getPosition());
                println("The category is " + currentCategory());
                askQuestion();
            } else {
                println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            int newPosition = (player.getPosition() + roll) % 12;
            player.setPosition(newPosition);

            println(players.get(currentPlayer)
                    + "'s new location is "
                    + player.getPosition());
            println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        QuestionCategory questionCategory = currentCategory();
        Question question = questionRepository.retrieveQuestion(questionCategory);
        println(question);
    }


    private QuestionCategory currentCategory() {
        Player player = players.get(currentPlayer);
        int pos = player.getPosition();
        if (pos == 0) return POP;
        if (pos == 4) return POP;
        if (pos == 8) return POP;
        if (pos == 1) return SCIENCE;
        if (pos == 5) return SCIENCE;
        if (pos == 9) return SCIENCE;
        if (pos == 2) return SPORTS;
        if (pos == 6) return SPORTS;
        if (pos == 10) return SPORTS;
        return ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        Player player = players.get(currentPlayer);

        if (player.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!");
                player.addGoldCoin();
                println(players.get(currentPlayer)
                        + " now has "
                        + player.getGoldCoins()
                        + " Gold Coins.");

                boolean winner = didPlayerWin(player);
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            println("Answer was corrent!!!!");
            player.addGoldCoin();
            println(players.get(currentPlayer)
                    + " now has "
                    + player.getGoldCoins()
                    + " Gold Coins.");

            boolean winner = didPlayerWin(player);
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayer);
        println("Question was incorrectly answered");
        println(players.get(currentPlayer) + " was sent to the penalty box");
        player.sendToPenaltyBox();

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin(Player player) {

        return !(player.getGoldCoins() == 6);
    }

    private void println(Object line) {
        outputConsumer.accept(String.valueOf(line));
    }

    private void println(String line) {
        outputConsumer.accept(line);
    }

}
