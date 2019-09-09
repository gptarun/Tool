package com.assessment.assessment.answer;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assessment.assessment.answer.Answer;
import com.assessment.assessment.outcome.QuestionAnswerGroup;
import com.assessment.assessment.session.Session;
import com.assessment.assessment.session.SessionDAO;

@CrossOrigin(origins="${origin}")
@RestController
public class AnswerResource {
	@Autowired
	private AnswerService answerService;
	@Autowired
	private SessionDAO dao;
	
	@GetMapping("/{session}/answer")   //Note: the String session = urlKey in DB 
	public List<QuestionAnswerGroup> getAllAnswers(@PathVariable String session) throws SQLException{
		return answerService.retrieveAnswersBySession(session);
	}
	
	@GetMapping("/{session}/answer/history")
	public List<QuestionAnswerGroup> getAllAnswersHistory(@PathVariable String session) throws Exception{
		List<Session> sessions = dao.sessionHistory(session);
		return answerService.retrieveAnswersBySession(sessions, false);
	}
	@PutMapping("/{session}/answer") //It is called 'updateAnswer', but in reality it can both create and update rows on the database (in this one the session argument is equivalent to the sessionid
	public ResponseEntity<Answer> updateAnswer(@PathVariable int session, @RequestBody Answer answer) throws Exception{
		Answer newAns = answerService.save(session, answer);
		return new ResponseEntity<Answer>(newAns, HttpStatus.OK);
	}
}
