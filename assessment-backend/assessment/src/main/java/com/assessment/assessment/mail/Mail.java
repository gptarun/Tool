package com.assessment.assessment.mail;

public class Mail { //Email configuration 

	private String recipient;
	private String url;
	public Mail(String recipient, String url) {
		super();
		this.recipient = recipient;
		this.url = url;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
