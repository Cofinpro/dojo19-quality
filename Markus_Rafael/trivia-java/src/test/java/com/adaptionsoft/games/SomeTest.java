package com.adaptionsoft.games;

import static org.junit.Assert.*;

import com.adaptionsoft.games.trivia.Player;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class SomeTest {

	@Test
	public void true_is_true() throws Exception {
		assertTrue(false);
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
}
