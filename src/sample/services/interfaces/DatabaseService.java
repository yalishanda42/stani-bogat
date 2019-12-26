package sample.services.interfaces;

import sample.models.QuestionsContainer;

public interface DatabaseService {
    QuestionsContainer fetchAllQuestionsFromDatabase();
}
