package com.adaptionsoft.games.uglytrivia;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class GameTest {

    private QuestionRepository questionRepository = Mockito.spy(QuestionRepository.class);
    private TestConsumer outputConsumer = new TestConsumer();;
    private Game game;

    @Before
    public void setup() {
        outputConsumer.clear();
        game = new Game(outputConsumer, questionRepository);
    }

    @Test
    public void howManyPlayers() {

        game.add("JayUnit");

        int players = game.howManyPlayers();
        assertEquals(1, players);
    }

    @Test
    public void testInitialSetup() {
        assertEquals(0, game.currentPlayer);
    }

    @Test
    public void testRollCorrectAnswer() {

        Player jay = game.add("Jay");
        Player unit = game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, jay.getPosition());

        game.roll(3);
        //after the roll, the current user should be a position 3
        assertEquals(3, jay.getPosition());

        //he answers correctly!
        game.wasCorrectlyAnswered();

        //and gets a golden coin!
        assertEquals(1, jay.getGoldCoins());
        //and he is not sent to penalty box
        assertFalse(jay.isInPenaltyBox());

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);
    }

    @Test
    public void testRollBadAnswer() {

        Player jay = game.add("Jay");
        Player unit = game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, jay.getPosition());

        game.roll(1);
        //after the roll, the current user should be a position 1
        assertEquals(1, jay.getPosition());

        //he answers incorrectly!
        game.wrongAnswer();

        //and gets no golden coin!
        assertEquals(0, jay.getGoldCoins());

        //and he is sent to penalty box
        assertTrue(jay.isInPenaltyBox());

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);
    }

    @Test
    public void testGetOutOfPenaltyBox() {

        Player jay = game.add("Jay");
        Player unit = game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, jay.getPosition());

        game.roll(1);
        //after the roll, the current user should be at position 1
        assertEquals(1, jay.getPosition());

        //he answers incorrectly!
        game.wrongAnswer();

        //and gets no golden coin!
        assertEquals(0, jay.getGoldCoins());

        //and he is sent to penalty box
        assertTrue(jay.isInPenaltyBox());

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);

        //the second player rolls a 2
        game.roll(2);
        assertEquals(2, unit.getPosition());
        //he answers correctly!
        game.wasCorrectlyAnswered();
        //and gets no golden coin!
        assertEquals(1, unit.getGoldCoins());

        //it's the first player's turn!
        assertEquals(0, game.currentPlayer);
        assertTrue(jay.isInPenaltyBox());

        //the first player rolls a 3 for a change to get out of the penalty box
        game.roll(3);

        assertTrue(game.isGettingOutOfPenaltyBox);

        //now, Jay answers correctly
        game.wasCorrectlyAnswered();

        //however, Jay remains in the penalty box (potential bug?)
        assertTrue(jay.isInPenaltyBox());


    }

    @Test
    public void testNotGettingOutOfPenaltyBox() {

        Player jay = game.add("Jay");
        Player unit = game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, jay.getPosition());

        game.roll(1);
        //after the roll, the current user should be at position 1
        assertEquals(1, jay.getPosition());

        //he answers incorrectly!
        game.wrongAnswer();

        //and gets no golden coin!
        assertEquals(0, jay.getGoldCoins());

        //and he is sent to penalty box
        assertTrue(jay.isInPenaltyBox());

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);

        //the second player rolls a 2
        game.roll(2);
        assertEquals(2, unit.getPosition());
        //he answers correctly!
        game.wasCorrectlyAnswered();
        //and gets no golden coin!
        assertEquals(1, unit.getGoldCoins());

        //it's the first player's turn!
        assertEquals(0, game.currentPlayer);
        assertTrue(jay.isInPenaltyBox());

        //the first player rolls a 2
        game.roll(2);

        assertFalse(game.isGettingOutOfPenaltyBox);

        //now, Jay answers correctly
        game.wasCorrectlyAnswered();

        //however, Jay remains in the penalty box (potential bug?)
        assertTrue(jay.isInPenaltyBox());
        assertEquals(0, jay.getGoldCoins());
    }

    @Test
    public void testGetNoCoinWhenInPenaltyBoxAndAnswerCorrect() {

        Player jay = game.add("Jay");
        Player unit = game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, jay.getPosition());

        game.roll(1);
        //after the roll, the current user should be at position 1
        assertEquals(1, jay.getPosition());

        //he answers incorrectly!
        game.wrongAnswer();

        //and gets no golden coin!
        assertEquals(0, jay.getGoldCoins());

        //and he is sent to penalty box
        assertTrue(jay.isInPenaltyBox());

        //it's the next player's turn!
        assertEquals(1, game.currentPlayer);

        //the second player rolls a 2
        game.roll(2);
        assertEquals(2, unit.getPosition());
        //he answers correctly!
        game.wasCorrectlyAnswered();
        //and gets no golden coin!
        assertEquals(1, unit.getGoldCoins());

        //it's the first player's turn!
        assertEquals(0, game.currentPlayer);
        assertTrue(jay.isInPenaltyBox());

        //the first player rolls a 2
        game.roll(2);

        assertFalse(game.isGettingOutOfPenaltyBox);

        //now, Jay answers correctly
        game.wasCorrectlyAnswered();

        //however, Jay remains in the penalty box (potential bug?)
        assertTrue(jay.isInPenaltyBox());
        assertEquals(0, jay.getGoldCoins());
    }

    @Test
    public void testCorrectAnswerNotGettingInPenaltyBox() {

    }

    @Test
    public void testWinGame() {

    }

    @Test
    public void testPopQuestion() {

        Player jay = game.add("Jay");
        Player unit = game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, jay.getPosition());

        game.roll(4);
        //after the roll, the current user should be a position 3
        assertEquals(4, jay.getPosition());

        Mockito.verify(questionRepository, Mockito.times(1)).retrieveQuestion(QuestionCategory.POP);

        List<String> output = outputConsumer.getOutput();
        assertEquals(9, output.size());
        String actualOutput = String.join("\n", output);
        String expectedOutput = loadExpected("expected-out-pop-question.txt");
        assertEquals(expectedOutput, actualOutput);
    }

    private String loadExpected(String fileName) {
        try {
            return Files.readString(Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private class TestConsumer implements Consumer<String> {

        private final List<String> output = new ArrayList<>();
        private void addOutput(String output) {
            this.output.add(output);
        }

        void clear() {
            this.output.clear();
        }

        List<String> getOutput() {
            return Collections.unmodifiableList(output);
        }

        @Override
        public void accept(String s) {
            System.out.println(s);
            this.addOutput(s);
        }
    }

}