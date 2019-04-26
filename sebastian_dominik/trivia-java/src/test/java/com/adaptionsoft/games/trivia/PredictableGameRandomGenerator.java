package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.GameRandomGenerator;

public class PredictableGameRandomGenerator implements GameRandomGenerator{
	
	private final int[] moveFieldsArray = new int[16];
	private final int[] answersArray = new int[16];
	
	private int moveFieldCounter = 0;
	private int answerCounter = 0;

	public PredictableGameRandomGenerator() {
		for (int i = 0; i < moveFieldsArray.length; i++) {
			moveFieldsArray[i] = i % 5;
		}

		for (int i = 0; i < answersArray.length; i++) {
			answersArray[i] = i % 9;
		}
	}

	@Override
	public int randomStepsize() {
		return moveFieldsArray[moveFieldCounter++];
	}

	@Override
	public int randomAnswer() {
		return answersArray[answerCounter++];
	}

}
