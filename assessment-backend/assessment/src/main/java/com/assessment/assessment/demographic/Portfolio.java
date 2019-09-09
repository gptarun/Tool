package com.assessment.assessment.demographic;

import java.util.List;

public class Portfolio { //Corresponds to the IT Portfolio dropdown list on the signup page. Multiple portfolios are under a single vertical.

	private String vertical;
	private List<String> portfolio;
	private List<Integer> portId;
	public Portfolio(String vertical, List<String> portfolio, List<Integer> portId) {
		super();
		this.vertical = vertical;
		this.portfolio = portfolio;
		this.portId = portId;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public List<String> getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(List<String> portfolio) {
		this.portfolio = portfolio;
	}
	
	public List<Integer> getPortId() {
		return portId;
	}
	public void setPortId(List<Integer> portId) {
		this.portId = portId;
	}
}
