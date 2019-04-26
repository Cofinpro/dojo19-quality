package com.adaptionsoft.games.uglytrivia;

public class Player {
    private final String name;
    private int position;
    private int goldCoins;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.goldCoins = 0;
        this.inPenaltyBox = false;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addGoldCoin() {
        this.goldCoins++;
    }

    public int getGoldCoins() {
        return this.goldCoins;
    }

    public String getName() {
        return name;
    }

    public void sendToPenaltyBox() {
        this.inPenaltyBox = true;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", goldCoins=" + goldCoins +
                '}';
    }
}
