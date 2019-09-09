package com.assessment.assessment.demographic;

public class Demographic { //Object for holding user demographic information. Not used very much.

	private long id;
	private String name;
	private String location;
	private long size;
	private String[] works;
	public Demographic(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String[] getWorks() {
		return works;
	}
	public void setWorks(String[] works) {
		this.works = works;
	}
	
	
}
