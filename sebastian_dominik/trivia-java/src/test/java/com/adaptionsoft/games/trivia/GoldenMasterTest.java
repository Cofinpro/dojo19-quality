package com.adaptionsoft.games.trivia;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Game;

public class GoldenMasterTest {

	int[] moveFieldsArray = new int[16];
	int[] answersArray = new int[16];

	@Before
	public void setup() {
		for (int i = 0; i < moveFieldsArray.length; i++) {
			moveFieldsArray[i] = i % 5;
		}

		for (int i = 0; i < answersArray.length; i++) {
			answersArray[i] = i % 9;
		}
	}

	@Test
	public void test() {

		boolean notAWinner = false;

		final Game aGame = new Game();

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		int round = 0;
		do {
			System.out.println("Round: " + round);
			aGame.roll(moveFieldsArray[round] + 1);

			if (answersArray[round] == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}

			round++;
		} while (notAWinner);
	}

}
