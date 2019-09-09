package com.assessment.assessment.outcome;

import com.assessment.assessment.answer.Answer;
import com.assessment.assessment.question.Question;

public class QuestionAnswerGroup { //the question, the user's response, and the string matching the user's response

	private Question question;
	private Answer answer;
	private String choiceText;
	
	public QuestionAnswerGroup(Question question, Answer answer) {
		super();
		this.question = question;
		this.answer = answer;
	}
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public String getChoiceText() {
		return choiceText;
	}

	public void setChoiceText(String choiceText) {
		this.choiceText = choiceText;
	}
	
	
}
