package com.assessment.assessment.demographic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemographicService {
	@Autowired
	private DemographicDAO dao;
	
	
	public long importTeam(Demographic team) throws Exception {
		
		return dao.importTeam(team);
	}
	
	public List<Portfolio> getPortfolios() throws Exception {
		return dao.getPortfolios();
	}
	
	
}
