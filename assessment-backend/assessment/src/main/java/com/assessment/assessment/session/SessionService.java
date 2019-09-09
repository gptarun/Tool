package com.assessment.assessment.session;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.assessment.outcome.CategoryService;

@Service
public class SessionService {
	
	
	private static List<Session> sessions = new ArrayList();
	@Autowired
	private CategoryService cService;
	@Autowired
	private SessionDAO dao;
	private int keyCounter = 0;
	
	public Session retrieveSessionById(String id) { //Isn't useful right now. There isn't a need for retrieving sessions in the tool yet. 
		//Left as placeholder in case it needs to be implemented later
		for(Session s:sessions) {
			if(s.getSessionID() == id) {
				return s;
			}
		}
		return null;
	} 
	
	public Session newSession(Session session, int portfolio) throws Exception {
		session.setCreatedOn(new Date());
		String encodeUrl = Base64.getEncoder().withoutPadding().encodeToString((new Date().getTime()+""+keyCounter).getBytes());
		keyCounter++;
		if(keyCounter > 10) {
			keyCounter = 0;
		}
		Session newSession = dao.createSession(session, encodeUrl, portfolio);
		return newSession;
	}
	
	public Session teamDemo(Session session, long id) throws Exception {
		return dao.updateSession(session, id);
	}
	
	public List<String> teamWorks (List<String> works, long id) throws Exception{
		return dao.teamWorks(works, id);
	}
	
	public String updateSessionURL(Session session) {
		Session s = retrieveSessionById(session.getSessionID());
		s.setUrl(session.getUrl());
		return s.getUrl();
	}

	public String setCategory(long sessionId, long id, String team) throws Exception {
		String str = dao.setCategory(id, sessionId, team);
		return str;
	}
	
	public List<Session> sessionHistory(String urlKey) throws Exception{
		return dao.sessionHistory(urlKey);
	}

	public void closeSession(String urlKey) throws Exception {
		dao.closeSession(urlKey);
	}
}
