
package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.MainLoop;
import com.adaptionsoft.games.uglytrivia.QuestionRepository;

import java.util.Random;


public class GameRunner {

	public static void main(String[] args) {
		QuestionRepository repository = new QuestionRepository();
		Game game = new Game(System.out::println, repository);
		Random random = new Random();
		MainLoop mainLoop = new MainLoop(game, random);

		mainLoop.runGame();
	}


}
