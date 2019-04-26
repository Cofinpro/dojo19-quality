package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
       
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public Player add(String playerName) {


		Player player = new Player(playerName);
		players.add(player);
	    	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return player;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		Player player = players.get(currentPlayer);
		
		if (player.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				int newPosition = (player.getPosition() + roll)%12;
				player.setPosition(newPosition);
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ player.getPosition());
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			int newPosition = (player.getPosition() + roll)%12;
			player.setPosition(newPosition);
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ player.getPosition());
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
		Player player = players.get(currentPlayer);
		int pos = player.getPosition();
		if (pos == 0) return "Pop";
		if (pos == 4) return "Pop";
		if (pos == 8) return "Pop";
		if (pos == 1) return "Science";
		if (pos == 5) return "Science";
		if (pos == 9) return "Science";
		if (pos == 2) return "Sports";
		if (pos == 6) return "Sports";
		if (pos == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		Player player = players.get(currentPlayer);

		if (player.isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				player.addGoldCoin();
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ player.getGoldCoins()
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin(player);
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			player.addGoldCoin();
			System.out.println(players.get(currentPlayer)
					+ " now has "
					+ player.getGoldCoins()
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin(player);
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
    	Player player = players.get(currentPlayer);
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		player.sendToPenaltyBox();
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin(Player player) {

		return !(player.getGoldCoins() == 6);
	}
}
