package com.adaptionsoft.games.uglytrivia;

public class Player {
	
	private String name;
	
	private int coins = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	public void addCoins(int amount) {
		coins += amount;
	}

}
