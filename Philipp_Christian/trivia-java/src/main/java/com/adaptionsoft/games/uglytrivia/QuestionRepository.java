package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;

import static com.adaptionsoft.games.uglytrivia.QuestionCategory.values;

public class QuestionRepository {

    private Map<QuestionCategory, Queue<Question>> questions = new EnumMap<>(QuestionCategory.class);

    public QuestionRepository() {
        for (QuestionCategory category : values()) {
            questions.computeIfAbsent(category, c -> {
                Queue<Question> questionList = new ArrayDeque<>();
                for (int i = 0; i < 50; i++) {
                    questionList.add(c.createQuestion(i));
                }
                return questionList;
            });
        }
    }

    Question retrieveQuestion(QuestionCategory category) {
        Queue<Question> questionList = questions.get(category);
        return questionList.poll();
    }
}
