package com.adaptionsoft.games.uglytrivia;

import java.util.Random;

public class DefaultGameRandomGenerator implements GameRandomGenerator {
	
	private Random random = new Random();
	
	@Override
	public int randomStepsize() {
		return random.nextInt(5);
	}
	
	@Override
	public int randomAnswer() {
		return random.nextInt(9);
	}
	
}
