package com.assessment.assessment.outcome;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.assessment.answer.AnswerService;
import com.assessment.assessment.session.Session;

@Service
public class CategoryService {
	private static HashMap<Integer,List<List<Competency>>> categories = new HashMap<Integer,List<List<Competency>>>();
	@Autowired
	private AnswerService aService;
	@Autowired
	private CategoryDAO dao;
	
	
	public void createCategorySet(int session) throws Exception {
		List<List<Competency>> newCatSet = dao.getMapDimensions();
		
		categories.put(session, newCatSet);
		
	}
	public void clearCategorySet(int session) {
		categories.remove(session);
	}
	public void addToCategory(long area, long category, long score, float weight, int [] levels, int session) {
		categories.get(session).get((int)area).get((int)category).incSize(); //if scoring changed to a regular average later, move this into the if block
		if(score != -1) { //If user did not select N/A
			float currScore = categories.get(session).get((int)area).get((int)category).getTotal();
			float newScore = currScore + (score*weight);
			categories.get(session).get((int)area).get((int)category).setTotal(newScore);
			categories.get(session).get((int)area).get((int)category).setSubject(true);
		}
		else {//If user selected N/A
			//N/A responses are not reflected in the final maturity calculations, so their potential score needs to be accounted for
			float currentMaxScore = categories.get(session).get((int)area).get((int)category).getMaxScore();
			float currentMinScore = categories.get(session).get((int)area).get((int)category).getMinScore();
			categories.get(session).get((int)area).get((int)category).setMaxScore(currentMaxScore - Arrays.stream(levels).max().getAsInt());
			categories.get(session).get((int)area).get((int)category).setMinScore(currentMinScore - Arrays.stream(levels).min().getAsInt());
			categories.get(session).get((int)area).get((int)category).incNA();
			
		}
		return;
	}
	
	public void addUpTotals(int session, String sessionKey) throws SQLException { //Gets the answers for a session, and adds each answer's score to the respective category/competency
		
		List<QuestionAnswerGroup> ans = aService.retrieveAnswersBySession(sessionKey);
		for(QuestionAnswerGroup a: ans) {
			
			addToCategory(a.getAnswer().getCategory(), a.getAnswer().getCompetency(), a.getAnswer().getChoice(), a.getAnswer().getWeight(),a.getQuestion().getLevels(), session);
		}
		return;
	}
	public void addUpTotals(int session, List<Session> sessionList, boolean oldDataFlag) throws SQLException { //Gets the answers for a session, and adds each answer's score to the respective category/competency
		
		List<QuestionAnswerGroup> ans = aService.retrieveAnswersBySession(sessionList, oldDataFlag);
		for(QuestionAnswerGroup a: ans) {
			
			addToCategory(a.getAnswer().getCategory(), a.getAnswer().getCompetency(), a.getAnswer().getChoice(), a.getAnswer().getWeight(),a.getQuestion().getLevels(), session);
		}
		return;
	}
	
	public IndividualOutcome determineLevel(long category, long competency, int session, Outcome o) throws Exception {
	
		IndividualOutcome inOut = new IndividualOutcome(o.getCategory(),o.getCompetency(),o.getCategoryName(),o.getCompetencyName());
		
		if(categories.get(session).get((int)category).get((int)competency).getMaxScore() == 0 || !categories.get(session).get((int)category).get((int)competency).isSubject()) { //Max score of 0 indicates that there are no usable answers for a competency.
			inOut.setLevel(0); //Setting the level to 0 indicates to the frontend that information about this competency should not be displayed
			return inOut;
		}
		
		//Level calculation: determines the range between the minimum and maximum scores in a category, and divides that range into fifths.
		//The maturity level is determined by which slice the user's score falls into
		float scoreGap = categories.get(session).get((int)category).get((int)competency).getMaxScore() - categories.get(session).get((int)category).get((int)competency).getMinScore();
		float increment = scoreGap/5;
		for(int i = 1; i < 5; i++) {
			if(categories.get(session).get((int)category).get((int)competency).getMinScore() + (increment*i) > categories.get(session).get((int)category).get((int)competency).getTotal()) {
				inOut.setLevel(i);
				return inOut;
			}
		}
		inOut.setLevel(5);
		return inOut;
	}
}
