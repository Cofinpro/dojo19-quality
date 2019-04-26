package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import com.adaptionsoft.games.uglytrivia.category.Category;

public class Game {
	private static final String ROCK = "Rock";
	private static final String SPORTS = "Sports";
	private static final String SCIENCE = "Science";
	private static final String POP = "Pop";
	
	ArrayList players = new ArrayList();
	int[] places = new int[6];
	int[] purses = new int[6];
	boolean[] inPenaltyBox = new boolean[6];

//	LinkedList popQuestions = new LinkedList();
//	LinkedList scienceQuestions = new LinkedList();
//	LinkedList sportsQuestions = new LinkedList();
//	LinkedList rockQuestions = new LinkedList();
	
	private final  Collection<Category> categories = new ArrayList<>();
	
	private final Map<Integer,String> fields = new HashMap<>();

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

	Consumer<String> outConsumer;

	public Game() {		
		categories.add(new Category(POP));
		categories.add(new Category(SCIENCE));
		categories.add(new Category(SPORTS));
		categories.add(new Category(ROCK));
		
		initFields();
		
	}

	public Game(Consumer<String> outConsumer) {
		this();
		this.outConsumer = outConsumer;
	}

	// TODO vorabprüfung vor spielstart
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		// TODO Player instanziieren
		players.add(playerName);
		places[howManyPlayers()] = 0;
		purses[howManyPlayers()] = 0;
		inPenaltyBox[howManyPlayers()] = false;

		out(playerName + " was added");
		out("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		out(players.get(currentPlayer) + " is the current player");
		out("They have rolled a " + roll);

		if (isPlayerInPenalty()) {
			rollForPlayerInPenalty(roll);
		} else {
			rollPlayerMoveForward(roll);
		}

	}

	private void rollPlayerMoveForward(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11)
			places[currentPlayer] = places[currentPlayer] - 12;

		out(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
		out("The category is " + currentCategory());
		askQuestion();
	}

	private void rollForPlayerInPenalty(int roll) {
		if (isReleasedFromPenalty(roll)) {
			isGettingOutOfPenaltyBox = true;

			out(players.get(currentPlayer) + " is getting out of the penalty box");
			rollPlayerMoveForward(roll);
		} else {
			out(players.get(currentPlayer) + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
		}
	}

	private boolean isPlayerInPenalty() {
		return inPenaltyBox[currentPlayer];
	}

	private boolean isReleasedFromPenalty(int roll) {
		return roll % 2 != 0;
	}

	private void askQuestion() {
		final String currentCategory = currentCategory();
		
		
		for (Category category : categories) {
			if(category.getName().equals(currentCategory)) {
				out(category.getNextQuestion().getQuestion());
			}
		}
	}
	
	private void initFields() {
		fields.put(0, POP);
		fields.put(4, POP);
		fields.put(8, POP);
		
		fields.put(1, SCIENCE);
		fields.put(5, SCIENCE);
		fields.put(9, SCIENCE);
		
		fields.put(2, SPORTS);
		fields.put(6, SPORTS);
		fields.put(10, SPORTS);
		
		fields.put(3, ROCK);
		fields.put(7, ROCK);
		fields.put(11, ROCK);		
	}

	private String currentCategory() {
		return fields.get(places[currentPlayer]);
	}

	public boolean wasCorrectlyAnswered() {
		if (isPlayerInPenalty()) {
			if (isGettingOutOfPenaltyBox) {
				out("Answer was correct!!!!");
				purses[currentPlayer]++;
				out(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");

				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size())
					currentPlayer = 0;

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size())
					currentPlayer = 0;
				return true;
			}

		} else {

			out("Answer was corrent!!!!");
			purses[currentPlayer]++;
			out(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size())
				currentPlayer = 0;

			return winner;
		}
	}

	public boolean wrongAnswer() {
		out("Question was incorrectly answered");
		out(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}

	private void out(Object o) {
		if (outConsumer != null) {
			if (o != null) {
				outConsumer.accept(o.toString() + System.lineSeparator());
			}
		}
		System.out.println(o);
	}
	
	private void setNextPlayer() {
		// TODO implement
	}

}
