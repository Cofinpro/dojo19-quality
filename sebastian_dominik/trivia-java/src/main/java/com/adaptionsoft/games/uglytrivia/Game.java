package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.adaptionsoft.games.uglytrivia.category.Category;

public class Game {
	private static final String ROCK = "Rock";
	private static final String SPORTS = "Sports";
	private static final String SCIENCE = "Science";
	private static final String POP = "Pop";

	final ArrayList<Player> players = new ArrayList<>();

	final Collection<Player> playersInPenalty = new ArrayList<>();

	int[] places = new int[6];
	boolean[] inPenaltyBox = new boolean[6];

	private final Collection<Category> categories = new ArrayList<>();

	private final Map<Integer, String> fields = new HashMap<>();

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

	Consumer<String> outConsumer;

	private GameRandomGenerator gameRandomGenerator = new DefaultGameRandomGenerator();

	public Game() {
		categories.add(new Category(POP));
		categories.add(new Category(SCIENCE));
		categories.add(new Category(SPORTS));
		categories.add(new Category(ROCK));
		initFields();

	}

	public Game(Consumer<String> outConsumer) {
		this(outConsumer, new DefaultGameRandomGenerator());
	}

	public Game(Consumer<String> outConsumer, GameRandomGenerator gameRandomGenerator) {
		this();
		this.outConsumer = outConsumer;
		this.gameRandomGenerator = gameRandomGenerator;
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

	// TODO vorabprüfung vor spielstart
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {

		final Player player = new Player();
		player.setName(playerName);
		players.add(player);

		// TODO Player instanziieren

		places[howManyPlayers()] = 0;
	
		inPenaltyBox[howManyPlayers()] = false;

		out(playerName + " was added");
		out("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		out(getCurrentPlayerName() + " is the current player");
		out("They have rolled a " + roll);

		if (isPlayerInPenalty()) {
			rollForPlayerInPenalty(roll);
		} else {
			rollPlayerMoveForward(roll);
		}

	}

	private String getCurrentPlayerName() {
		return getCurrentPlayer().getName();
	}

	private void rollPlayerMoveForward(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11)
			places[currentPlayer] = places[currentPlayer] - 12;

		out(getCurrentPlayerName() + "'s new location is " + places[currentPlayer]);
		out("The category is " + currentCategory());
		askQuestion();
	}

	private void rollForPlayerInPenalty(int roll) {
		if (isReleasedFromPenalty(roll)) {
			isGettingOutOfPenaltyBox = true;

			out(getCurrentPlayerName() + " is getting out of the penalty box");
			rollPlayerMoveForward(roll);
		} else {
			out(getCurrentPlayerName() + " is not getting out of the penalty box");
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
			if (category.getName().equals(currentCategory)) {
				out(category.getNextQuestion().getQuestion());
			}
		}
	}

	private String currentCategory() {
		return fields.get(places[currentPlayer]);
	}

	public boolean wasCorrectlyAnswered() {
		if (isPlayerInPenalty()) {
			return wasCorrectlyAnsweredInPenalty();
		} else {

			return wasCorrectlyAnsweredDefault();
		}
	}

	private Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}

	private boolean wasCorrectlyAnsweredDefault() {
		out("Answer was corrent!!!!");
		final Player currPlayer = getCurrentPlayer();
		currPlayer.addCoins(1);

		

		out(getCurrentPlayerName() + " now has " + currPlayer.getCoins() + " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;

		return winner;
	}

	private boolean wasCorrectlyAnsweredInPenalty() {
		if (isGettingOutOfPenaltyBox) {
			out("Answer was correct!!!!");
			final Player currPlayer = getCurrentPlayer();
			currPlayer.addCoins(1);
			
			out(getCurrentPlayerName() + " now has " + currPlayer.getCoins() + " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size())
				currentPlayer = 0;

			return winner;
		}
		return setNextPlayer();

	}

	public boolean wrongAnswer() {
		out("Question was incorrectly answered");
		out(getCurrentPlayerName() + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		return setNextPlayer();
	}

	private boolean setNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(getCurrentPlayer().getCoins() == 6);
	}

	private void out(Object o) {
		if (outConsumer != null) {
			if (o != null) {
				outConsumer.accept(o.toString() + System.lineSeparator());
			}
		}
		System.out.println(o);
	}

	public void run() {
		boolean notAWinner = true;
		do {
			roll(gameRandomGenerator.randomStepsize() + 1);

			if (gameRandomGenerator.randomAnswer() == 7) {
				notAWinner = wrongAnswer();
			} else {
				notAWinner = wasCorrectlyAnswered();
			}

		} while (notAWinner);
	}

}
