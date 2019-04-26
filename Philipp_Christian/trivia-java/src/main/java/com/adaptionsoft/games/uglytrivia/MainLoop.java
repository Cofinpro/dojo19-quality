package com.adaptionsoft.games.uglytrivia;

import java.util.Random;

public class MainLoop {

    private final Game aGame;
    private final Random rand;

    public MainLoop(Game aGame, Random rand) {
        this.aGame = aGame;
        this.rand = rand;
    }

    public void runGame() {

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        boolean notAWinner;
        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }

        } while (notAWinner);
    }
}
