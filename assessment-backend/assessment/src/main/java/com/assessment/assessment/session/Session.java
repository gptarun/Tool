package com.assessment.assessment.session;
import java.util.Date;

public class Session { //object representing an individual assessment session
	private long dbId;
	private String sessionID;
	private String email;
	private String url;
	private String teamName;
	private Date createdOn;
	private Date completedOn;
	private String location;
	private long category;
	private int size;
	private boolean oldData;
	protected Session() {
		
	}
	public Session(long dbId, String sessionID, Date completedOn, long category, boolean oldData) {
		super();
		this.dbId = dbId;
		this.sessionID = sessionID;
		this.completedOn = completedOn;
		this.category = category;
		this.oldData = oldData;
	}
	public Session(String sessionID, String email) {
		super();
		this.sessionID = sessionID;
		this.email = email;
		this.createdOn = new Date();
	}
	
	public Session(long dbId, String sessionID, String email, Date createdOn, Date completedOn) {
		super();
		this.dbId = dbId;
		this.sessionID = sessionID;
		this.email = email;
		this.createdOn = createdOn;
		this.completedOn = completedOn;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getDbId() {
		return dbId;
	}
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getCompletedOn() {
		return completedOn;
	}
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}
	public long getCategory() {
		return category;
	}
	public void setCategory(long category) {
		this.category = category;
	}
	public boolean isOldData() {
		return oldData;
	}
	public void setOldData(boolean oldData) {
		this.oldData = oldData;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
}
