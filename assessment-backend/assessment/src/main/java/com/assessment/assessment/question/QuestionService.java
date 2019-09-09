package com.assessment.assessment.question;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
	@Autowired
	private QuestionDAO dao;
	
	
	public List<Question> findAll() throws Exception{
		
		return dao.getAllQuestions();
	}
	
	public Question findById(long id) throws Exception {
		Question q = dao.getQuestion(id);
		return q;
	}
	
	public Question getNextQuestionInCategory(long id, long category, String direction, String order) throws Exception {
		return dao.getNextInCategory(id, category, direction, order);
	}
	
	public Question save(Question question) throws Exception {
		Question q = dao.updateQuestion(question);
		return q;
	}
	
	
}
