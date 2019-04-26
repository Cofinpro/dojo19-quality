package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public ArrayList getPlayers() {
		return players;
	}

    ArrayList players = new ArrayList();
    int[] places = new int[6];

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	private ArrayList<Player> playerList = new ArrayList<Player>();
	private static int coinsToWin = 6;
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayerIndex = 0;
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

	public boolean add(String playerName) {
		
		//TODO
	    players.add(playerName);
		Player player = new Player();
		player.setName(playerName);
		player.setPurse(0);
		playerList.add(player);
	    places[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayerIndex) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayerIndex]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayerIndex) + " is getting out of the penalty box");
				places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
				if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;
				
				System.out.println(players.get(currentPlayerIndex)
						+ "'s new location is " 
						+ places[currentPlayerIndex]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayerIndex) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
			if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;
			
			System.out.println(players.get(currentPlayerIndex)
					+ "'s new location is " 
					+ places[currentPlayerIndex]);
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
		if (places[currentPlayerIndex] == 0) return "Pop";
		if (places[currentPlayerIndex] == 4) return "Pop";
		if (places[currentPlayerIndex] == 8) return "Pop";
		if (places[currentPlayerIndex] == 1) return "Science";
		if (places[currentPlayerIndex] == 5) return "Science";
		if (places[currentPlayerIndex] == 9) return "Science";
		if (places[currentPlayerIndex] == 2) return "Sports";
		if (places[currentPlayerIndex] == 6) return "Sports";
		if (places[currentPlayerIndex] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayerIndex]){
			if (isGettingOutOfPenaltyBox) {
				return incrementPurse();
			} else {
				currentPlayerIndex++;
				if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
				return true;
			}
		} else {
			return incrementPurse();
		}
	}

	private boolean incrementPurse() {
		System.out.println("Answer was correct!!!!");
		Player currentPlayer = playerList.get(currentPlayerIndex);
		currentPlayer.setPurse(currentPlayer.getPurse() + 1);
		System.out.println(players.get(currentPlayerIndex)
				+ " now has "
				+ playerList.get(currentPlayerIndex).getPurse()
				+ " Gold Coins.");
		boolean winner = didPlayerWin();
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) {
			currentPlayerIndex = 0;
		}
		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayerIndex)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayerIndex] = true;
		
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
		return true;
	}


	private boolean didPlayerWin() {
    	return !(playerList.get(currentPlayerIndex).getPurse() == coinsToWin);
	}
}
