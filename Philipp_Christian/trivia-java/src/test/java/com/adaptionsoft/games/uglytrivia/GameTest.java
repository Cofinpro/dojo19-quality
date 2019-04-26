package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void howManyPlayers() {
        Game game = new Game();
        game.add("JayUnit");

        int players = game.howManyPlayers();
        assertEquals(1, players);
    }

    @Test
    public void testInitialSetup() {
        Game game = new Game();

        assertEquals(0, game.currentPlayer);

        assertEquals(50, game.popQuestions.size());
        assertEquals(50, game.rockQuestions.size());
        assertEquals(50, game.scienceQuestions.size());
        assertEquals(50, game.sportsQuestions.size());
    }

    @Test
    public void testRollCorrectAnswer() {

        Game game = new Game();
        game.add("Jay");
        game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, game.places[0]);

        game.roll(3);
        //after the roll, the current user should be a position 3
        assertEquals(3, game.places[0]);

        //he answers correctly!
        game.wasCorrectlyAnswered();

        //and gets a golden coin!
        assertEquals(1, game.purses[0]);
        //and he is not sent to penalty box
        assertFalse(game.inPenaltyBox[0]);

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);
    }

    @Test
    public void testRollBadAnswer() {

        Game game = new Game();
        game.add("Jay");
        game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, game.places[0]);

        game.roll(1);
        //after the roll, the current user should be a position 3
        assertEquals(1, game.places[0]);

        //he answers incorrectly!
        game.wrongAnswer();

        //and gets no golden coin!
        assertEquals(0, game.purses[0]);

        //and he is sent to penalty box
        assertTrue(game.inPenaltyBox[0]);

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);
    }

    @Test
    public void testGetOutOfPenaltyBox() {

        Game game = new Game();
        game.add("Jay");
        game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, game.places[0]);

        game.roll(1);
        //after the roll, the current user should be at position 1
        assertEquals(1, game.places[0]);

        //he answers incorrectly!
        game.wrongAnswer();

        //and gets no golden coin!
        assertEquals(0, game.purses[0]);

        //and he is sent to penalty box
        assertTrue(game.inPenaltyBox[0]);

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);

        //the second player rolls a 2
        game.roll(2);
        assertEquals(2, game.places[1]);
        //he answers correctly!
        game.wasCorrectlyAnswered();
        //and gets no golden coin!
        assertEquals(1, game.purses[1]);

        //it's the first player's turn!
        assertEquals(0, game.currentPlayer);
        assertTrue(game.inPenaltyBox[0]);

        //the first player rolls a 3 for a change to get out of the penalty box
        game.roll(3);

        assertTrue(game.isGettingOutOfPenaltyBox);

        //now, Jay answers correctly
        game.wasCorrectlyAnswered();

        //however, Jay remains in the penalty box (potential bug?)
        assertTrue(game.inPenaltyBox[0]);


    }

    @Test
    public void testNotGettingOutOfPenaltyBox() {
        Game game = new Game();
        game.add("Jay");
        game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, game.places[0]);

        game.roll(1);
        //after the roll, the current user should be at position 1
        assertEquals(1, game.places[0]);

        //he answers incorrectly!
        game.wrongAnswer();

        //and gets no golden coin!
        assertEquals(0, game.purses[0]);

        //and he is sent to penalty box
        assertTrue(game.inPenaltyBox[0]);

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);

        //the second player rolls a 2
        game.roll(2);
        assertEquals(2, game.places[1]);
        //he answers correctly!
        game.wasCorrectlyAnswered();
        //and gets no golden coin!
        assertEquals(1, game.purses[1]);

        //it's the first player's turn!
        assertEquals(0, game.currentPlayer);
        assertTrue(game.inPenaltyBox[0]);

        //the first player rolls a 2
        game.roll(2);

        assertFalse(game.isGettingOutOfPenaltyBox);

        //now, Jay answers correctly
        game.wasCorrectlyAnswered();

        //however, Jay remains in the penalty box (potential bug?)
        assertTrue(game.inPenaltyBox[0]);
        assertEquals(0, game.purses[0]);
    }

    @Test
    public void testCorrectAnswerNotGettingInPenaltyBox() {

    }

    @Test
    public void testWinGame() {

    }

    @Test
    public void testCategories() {

    }
}