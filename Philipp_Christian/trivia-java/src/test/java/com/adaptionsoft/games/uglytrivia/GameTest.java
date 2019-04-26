package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        Map<QuestionCategory, LinkedList<Question>> questions = game.getQuestions();
        LinkedList<Question> popQuestions = questions.get(QuestionCategory.POP);
        LinkedList<Question> rockQuestions = questions.get(QuestionCategory.ROCK);
        LinkedList<Question> scienceQuestions = questions.get(QuestionCategory.SCIENCE);
        LinkedList<Question> sportsQuestions = questions.get(QuestionCategory.SPORTS);

        assertEquals(50, popQuestions.size());
        assertEquals(50, rockQuestions.size());
        assertEquals(50, scienceQuestions.size());
        assertEquals(50, sportsQuestions.size());
    }

    @Test
    public void testRollCorrectAnswer() {

        Game game = new Game();
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

        Game game = new Game();
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

        Game game = new Game();
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
        Game game = new Game();
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
        Game game = new Game();
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
        TestConsumer outputConsumer = new TestConsumer();

        Game game = new Game(outputConsumer);
        Player jay = game.add("Jay");
        Player unit = game.add("Unit");

        assertEquals(0, game.currentPlayer);
        assertEquals(0, jay.getPosition());

        game.roll(4);
        //after the roll, the current user should be a position 3
        assertEquals(4, jay.getPosition());

        Map<QuestionCategory, LinkedList<Question>> questions = game.getQuestions();

        LinkedList<Question> popQuestions = questions.get(QuestionCategory.POP);
        assertEquals(49, popQuestions.size());
        assertEquals("Pop Question 1", popQuestions.get(0).getText());

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