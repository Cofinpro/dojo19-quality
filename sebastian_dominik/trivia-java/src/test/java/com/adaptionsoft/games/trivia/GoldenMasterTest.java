package com.adaptionsoft.games.trivia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Game;

import junit.framework.Assert;

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
	public void test1() {

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
	
	@Test
	public void test2() {

		boolean notAWinner = false;

		final StringBuilder sb = new StringBuilder();
		
		final Game aGame = new Game(new Consumer<String>() {
			
			@Override
			public void accept(String t) {
				sb.append(t);
				
			}
		});

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
		
		String expected = readFromResourcesFile("golden-master-expected.txt");
		String current = sb.toString();
				
		Assert.assertEquals(expected, current);
	}
	
	private String readFromResourcesFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        
        File file = new File(classLoader.getResource(filename).getFile());
         
      
        try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
