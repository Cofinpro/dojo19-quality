package com.adaptionsoft.games.trivia;

public class Player {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPurse() {
        return purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    private String name;
    private int purse;
    private int place;
}
