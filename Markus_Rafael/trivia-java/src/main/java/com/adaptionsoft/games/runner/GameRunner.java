
package com.adaptionsoft.games.runner;
import java.util.ArrayList;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");
		
		Random rand = new Random();
		if(args != null && args.length > 0 && args[0] != null) {
			int seed = Integer.valueOf(args[0]);
			rand.setSeed(seed);
		}

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
