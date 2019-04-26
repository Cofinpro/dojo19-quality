package com.adaptionsoft.games.uglytrivia;

public enum QuestionCategory {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private final String name;

    QuestionCategory(String name) {
        this.name = name;
    }

    public Question createQuestion(int id) {
        return new Question(this.name + " Question " + id);
    }

    @Override
    public String toString() {
        return name;
    }
}
