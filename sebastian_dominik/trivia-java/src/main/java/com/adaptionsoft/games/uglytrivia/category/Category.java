package com.adaptionsoft.games.uglytrivia.category;

public class Category {
	
	private String name;
	
	private int counter = 0;

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
	
	public Question getNextQuestion() {
		return new Question(name + " Question " + counter++);
	}

}
