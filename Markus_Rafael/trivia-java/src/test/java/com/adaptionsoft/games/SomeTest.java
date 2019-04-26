package com.adaptionsoft.games;

import static org.junit.Assert.*;

import com.adaptionsoft.games.runner.GameRunner;
import com.adaptionsoft.games.trivia.Player;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class SomeTest {

	private static String seed = "1000";
	private static String expectedOutput = "Chet was added\r\n" +
			"They are player number 1\r\n" +
			"Pat was added\r\n" +
			"They are player number 2\r\n" +
			"Sue was added\r\n" +
			"They are player number 3\r\n" +
			"Chet is the current player\r\n" +
			"They have rolled a 3\r\n" +
			"Chet's new location is 3\r\n" +
			"The category is Rock\r\n" +
			"Rock Question 0\r\n" +
			"Answer was correct!!!!\r\n" +
			"Chet now has 1 Gold Coins.\r\n" +
			"Pat is the current player\r\n" +
			"They have rolled a 2\r\n" +
			"Pat's new location is 2\r\n" +
			"The category is Sports\r\n" +
			"Sports Question 0\r\n" +
			"Question was incorrectly answered\r\n" +
			"Pat was sent to the penalty box\r\n" +
			"Sue is the current player\r\n" +
			"They have rolled a 3\r\n" +
			"Sue's new location is 3\r\n" +
			"The category is Rock\r\n" +
			"Rock Question 1\r\n" +
			"Answer was correct!!!!\r\n" +
			"Sue now has 1 Gold Coins.\r\n" +
			"Chet is the current player\r\n" +
			"They have rolled a 2\r\n" +
			"Chet's new location is 5\r\n" +
			"The category is Science\r\n" +
			"Science Question 0\r\n" +
			"Answer was correct!!!!\r\n" +
			"Chet now has 2 Gold Coins.\r\n" +
			"Pat is the current player\r\n" +
			"They have rolled a 5\r\n" +
			"Pat is getting out of the penalty box\r\n" +
			"Pat's new location is 7\r\n" +
			"The category is Rock\r\n" +
			"Rock Question 2\r\n" +
			"Answer was correct!!!!\r\n" +
			"Pat now has 1 Gold Coins.\r\n" +
			"Sue is the current player\r\n" +
			"They have rolled a 5\r\n" +
			"Sue's new location is 8\r\n" +
			"The category is Pop\r\n" +
			"Pop Question 0\r\n" +
			"Question was incorrectly answered\r\n" +
			"Sue was sent to the penalty box\r\n" +
			"Chet is the current player\r\n" +
			"They have rolled a 3\r\n" +
			"Chet's new location is 8\r\n" +
			"The category is Pop\r\n" +
			"Pop Question 1\r\n" +
			"Answer was correct!!!!\r\n" +
			"Chet now has 3 Gold Coins.\r\n" +
			"Pat is the current player\r\n" +
			"They have rolled a 2\r\n" +
			"Pat is not getting out of the penalty box\r\n" +
			"Sue is the current player\r\n" +
			"They have rolled a 2\r\n" +
			"Sue is not getting out of the penalty box\r\n" +
			"Question was incorrectly answered\r\n" +
			"Sue was sent to the penalty box\r\n" +
			"Chet is the current player\r\n" +
			"They have rolled a 4\r\n" +
			"Chet's new location is 0\r\n" +
			"The category is Pop\r\n" +
			"Pop Question 2\r\n" +
			"Answer was correct!!!!\r\n" +
			"Chet now has 4 Gold Coins.\r\n" +
			"Pat is the current player\r\n" +
			"They have rolled a 3\r\n" +
			"Pat is getting out of the penalty box\r\n" +
			"Pat's new location is 10\r\n" +
			"The category is Sports\r\n" +
			"Sports Question 1\r\n" +
			"Answer was correct!!!!\r\n" +
			"Pat now has 2 Gold Coins.\r\n" +
			"Sue is the current player\r\n" +
			"They have rolled a 5\r\n" +
			"Sue is getting out of the penalty box\r\n" +
			"Sue's new location is 1\r\n" +
			"The category is Science\r\n" +
			"Science Question 1\r\n" +
			"Answer was correct!!!!\r\n" +
			"Sue now has 2 Gold Coins.\r\n" +
			"Chet is the current player\r\n" +
			"They have rolled a 4\r\n" +
			"Chet's new location is 4\r\n" +
			"The category is Pop\r\n" +
			"Pop Question 3\r\n" +
			"Answer was correct!!!!\r\n" +
			"Chet now has 5 Gold Coins.\r\n" +
			"Pat is the current player\r\n" +
			"They have rolled a 5\r\n" +
			"Pat is getting out of the penalty box\r\n" +
			"Pat's new location is 3\r\n" +
			"The category is Rock\r\n" +
			"Rock Question 3\r\n" +
			"Answer was correct!!!!\r\n" +
			"Pat now has 3 Gold Coins.\r\n" +
			"Sue is the current player\r\n" +
			"They have rolled a 1\r\n" +
			"Sue is getting out of the penalty box\r\n" +
			"Sue's new location is 2\r\n" +
			"The category is Sports\r\n" +
			"Sports Question 2\r\n" +
			"Answer was correct!!!!\r\n" +
			"Sue now has 3 Gold Coins.\r\n" +
			"Chet is the current player\r\n" +
			"They have rolled a 3\r\n" +
			"Chet's new location is 7\r\n" +
			"The category is Rock\r\n" +
			"Rock Question 4\r\n" +
			"Answer was correct!!!!\r\n" +
			"Chet now has 6 Gold Coins.\r\n";

	@Test
	public void testPlayers() throws Exception {
		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		System.setOut(new java.io.PrintStream(out));
		String[] args = new String[1];
		args[0] = seed;
		GameRunner.main(args);
		assertEquals(expectedOutput, out.toString());
	}

	@Test
	public void simpleTest() throws Exception {
		Game aGame = new Game();

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		Random rand = new Random();
		int numberCounter = 1;
		boolean notAWinner;

		do {
			int number = numberCounter % 6;
			numberCounter++;
			aGame.roll(number);
			if (number == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}

		} while (notAWinner);

		int index = 0;
		Player chet = aGame.getPlayerList().get(index);
		assertEquals("Chet", chet.getName());
		assertEquals(6, chet.getPurse());
	}

	@Test
	public void testPenaltyBox() throws Exception {
		Game aGame = new Game();

		aGame.add("Winner");
		aGame.add("Loser");
		int numberCounter = 1;
		boolean notAWinner;

		do {
			int number = numberCounter % 6;
			numberCounter++;
			aGame.roll(number);
			if (number == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);
		int index = 0;
		Player chet = aGame.getPlayerList().get(index);
	}
}
