package com.adaptionsoft.games.uglytrivia.provider;

import com.adaptionsoft.games.uglytrivia.Question;

public interface QuestionProvider {
	
	Question getNextQuestion(Category category);

}
