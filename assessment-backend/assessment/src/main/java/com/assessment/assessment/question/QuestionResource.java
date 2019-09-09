package com.assessment.assessment.question;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assessment.assessment.question.Question;


@CrossOrigin(origins="${origin}")
@RestController
public class QuestionResource {
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/question")
	public List<Question> getAllQuestions() throws Exception{
		return questionService.findAll();
	}
	
	@GetMapping("/question/{id}")
	public Question getQuestion(@PathVariable long id) throws Exception {
		
		return questionService.findById(id);
	}
	
	@GetMapping("/question/{id}/{category}")
	public Question getQuestionByCategory(@PathVariable long id, @PathVariable long category) throws Exception {
		
		return questionService.getNextQuestionInCategory(id, category, ">", "ASC");
	}
	
	@GetMapping("/question/{id}/{category}/prev")
	public Question getPrevQuestionByCategory(@PathVariable long id, @PathVariable long category) throws Exception {
		
		return questionService.getNextQuestionInCategory(id, category, "<", "DESC");
				
	}
	
	@PutMapping("/question")
	public ResponseEntity<Question> insertQuestion(@RequestBody Question question) throws Exception {
		Question returnQuestion = questionService.save(question);
		return new ResponseEntity<Question>(returnQuestion, HttpStatus.OK);
	}
}
