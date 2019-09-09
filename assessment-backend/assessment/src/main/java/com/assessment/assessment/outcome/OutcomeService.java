package com.assessment.assessment.outcome;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.assessment.session.Session;

@Service
public class OutcomeService {
	private final String level5 = "Your team is highly mature in this subject. We recommend that you educate other teams on your practices.";
	private static List<Outcome> outcomes = new ArrayList();
	@Autowired
	private CategoryService catService;
	@Autowired
	private OutcomeDAO dao;
	
	
	

	public List<Outcome> allOutcomes() throws Exception{
		return dao.getAllOutcomes();
	}
	
	public List<Outcome> allModels() throws Exception{
		return dao.getAllModels();
	}
	
	public void addUpTotals(int session, String sessionKey) throws Exception { //The heavy duty work is handled by the category service
		catService.createCategorySet(session);
		catService.addUpTotals(session, sessionKey);
	}
	
	public void addUpTotals(int session, List<Session> sessionList, boolean oldDataFlag) throws Exception { //The heavy duty work is handled by the category service
		catService.createCategorySet(session);
		catService.addUpTotals(session, sessionList, oldDataFlag);
	}
	
	public void clearCategory(int session) {
		catService.clearCategorySet(session);
	}
	
	public IndividualOutcome determineOutcome(long area, long category, long competency, String [] outText, int session, int type, Outcome o) throws Exception {
		
		IndividualOutcome indiv = catService.determineLevel(category, competency, session, o); //get a level between 0 and 5
		System.out.println(category+" "+competency+" "+indiv.getLevel());
		if(indiv.getLevel() == 0) { //level = 0 means there is no calculation to be made for that competency
			//either there are no questions in a competency, or user answered N/A for all questions in a competency
			indiv.setOutcomeText("There are currently no questions tied to this competency in the assessment.");
		}
		else{
			String outcome;
			outcome = (indiv.getLevel() == 5 && type == 0 ? level5 : outText[indiv.getLevel()-1]); //type = 0 means this is an Outcome(learning action), which doesn't have a level 5 value
			indiv.setOutcomeText(outcome);
		}
		return indiv;
		
	}
	
	
	public OutcomeLoader newOutcome(OutcomeLoader outLoader) throws Exception {
		return dao.newOutcome(outLoader);
	}
	public OutcomeLoader newModel(OutcomeLoader outLoader) throws Exception {
		return dao.newModel(outLoader);
	}
	
	public OutcomeLoader updateOutcome(OutcomeLoader outLoader) throws Exception{
		return dao.updateOutcome(outLoader);
	}
}
