
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.MainLoop;


public class GameRunner {

	public static void main(String[] args) {
		Game game = new Game();
		Random random = new Random();
		MainLoop mainLoop = new MainLoop(game, random);

		mainLoop.runGame();
	}


}
