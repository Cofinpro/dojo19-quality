package com.adaptionsoft.games.uglytrivia.provider;

import java.util.HashMap;
import java.util.Map;

import com.adaptionsoft.games.uglytrivia.Question;

public class QuestionProviderImpl implements QuestionProvider{	
	
	final Map<Category,Integer> qmap = new HashMap<>();

	@Override
	public Question getNextQuestion(Category category) {
		Integer integer = qmap.get(category);
		
		Question q = new Question(category.name() + " Question " + integer);
		qmap.put(category, integer++);
		return q;
	}
	
	

}
