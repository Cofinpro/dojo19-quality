package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private ArrayList players = new ArrayList();

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	private Player getCurrentPlayer() {
		return playerList.get(currentPlayerIndex);
	}

	private ArrayList<Player> playerList = new ArrayList<Player>();
	private static int coinsToWin = 6;
    private boolean[] inPenaltyBox  = new boolean[6];
    
    private LinkedList popQuestions = new LinkedList();
	private LinkedList scienceQuestions = new LinkedList();
	private LinkedList sportsQuestions = new LinkedList();
	private LinkedList rockQuestions = new LinkedList();

	private int currentPlayerIndex = 0;
	private boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast(createQuestion(i, "Pop"));
			scienceQuestions.addLast(createQuestion(i, "Science"));
			sportsQuestions.addLast(createQuestion(i, "Sports"));
			rockQuestions.addLast(createQuestion(i, "Rock"));
    	}
    }

	private String createQuestion(int index, String type){
		return type + " Question " + index;
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
		player.setPlace(0);
		playerList.add(player);
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
				changeLocationForPlayer(roll);
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayerIndex) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			changeLocationForPlayer(roll);
			askQuestion();
		}
	}

	private void changeLocationForPlayer(int roll) {
		getCurrentPlayer().setPlace(getCurrentPlayer().getPlace() + roll);
		if (getCurrentPlayer().getPlace() > 11) {
			getCurrentPlayer().setPlace(getCurrentPlayer().getPlace() - 12);
		}

		System.out.println(players.get(currentPlayerIndex)
				+ "'s new location is "
				+ getCurrentPlayer().getPlace());
		System.out.println("The category is " + currentCategory());
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
		if (getCurrentPlayer().getPlace() == 0) return "Pop";
		if (getCurrentPlayer().getPlace() == 4) return "Pop";
		if (getCurrentPlayer().getPlace() == 8) return "Pop";
		if (getCurrentPlayer().getPlace() == 1) return "Science";
		if (getCurrentPlayer().getPlace() == 5) return "Science";
		if (getCurrentPlayer().getPlace() == 9) return "Science";
		if (getCurrentPlayer().getPlace() == 2) return "Sports";
		if (getCurrentPlayer().getPlace() == 6) return "Sports";
		if (getCurrentPlayer().getPlace() == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayerIndex]){
			if (isGettingOutOfPenaltyBox) {
				return incrementPurse();
			} else {
				currentPlayerIndex++;
				if (currentPlayerIndex == players.size()) {
					currentPlayerIndex = 0;
				}
				return true;
			}
		} else {
			return incrementPurse();
		}
	}

	private boolean incrementPurse() {
		System.out.println("Answer was correct!!!!");
		Player currentPlayer = getCurrentPlayer();
		currentPlayer.setPurse(currentPlayer.getPurse() + 1);
		System.out.println(players.get(currentPlayerIndex)
				+ " now has "
				+ getCurrentPlayer().getPurse()
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
		if (currentPlayerIndex == players.size()) {
			currentPlayerIndex = 0;
		}
		return true;
	}


	private boolean didPlayerWin() {
    	return !(getCurrentPlayer().getPurse() == coinsToWin);
	}
}
