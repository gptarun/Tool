package com.assessment.assessment.answer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.assessment.outcome.QuestionAnswerGroup;
import com.assessment.assessment.question.Question;
import com.assessment.assessment.question.QuestionService;
import com.assessment.assessment.session.Session;

@Service
public class AnswerService {
	
	@Autowired
	private QuestionService qService;
	@Autowired
	private AnswerDAO dao;
	

	
	public List<QuestionAnswerGroup> retrieveAnswersBySession(String session) throws SQLException{
		List<QuestionAnswerGroup> sessionAns = new ArrayList();
		sessionAns = dao.answersBySession(session);
		return sessionAns;
	}
	public List<QuestionAnswerGroup> retrieveAnswersBySession(List<Session> session, boolean oldDataFlag) throws SQLException{
		List<QuestionAnswerGroup> sessionAns = new ArrayList();
		sessionAns = dao.answersBySession(session, oldDataFlag);
		return sessionAns;
	}
	
	public Answer retrieveById(long id, int session) throws Exception {
		Answer a = dao.answerById(id, session);
		return a;
	}
	
	public Answer save(int session, Answer answer) throws Exception {
		Question q = qService.findById(answer.getId());
		
		answer.setArea(q.getArea());
		answer.setCategory(q.getCategory());
		answer.setCompetency(q.getCompetency());
		answer.setWeight(q.getWeight());
		answer.setSessionID(session);
		
		if(retrieveById(answer.getId(), session) != null) { //When the answer already exists in the database, this function does not perform a true update
			//It deletes the preexisting answer before saving
			deleteById(answer.getId(), session);
		}
		dao.saveAnswer(answer);
		return answer;
	}
	
	public void deleteById(long id, int session) throws Exception {
		dao.deleteAnswer(id, session);
	}
}
