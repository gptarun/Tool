package com.assessment.assessment.outcome;

public class Category {
	private float minScore;
	private float maxScore;
	private long id;
	private long areaId;
	private float total;
	private long size;
	private long notApplicable;

	
	public Category() {
		
	}
	public Category(float minScore, float maxScore, long id, long areaId, float total) {
		super();
		this.minScore = minScore;
		this.maxScore = maxScore;
		this.id = id;
		this.areaId = areaId;
		this.total = total;
	}
	public float getMinScore() {
		return minScore;
	}
	public void setMinScore(float minScore) {
		this.minScore = minScore;
	}
	public float getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}

	public void incSize(){
		this.size++;
	}
	public long getNotApplicable() {
		return notApplicable;
	}
	public void setNotApplicable(long notApplicable) {
		this.notApplicable = notApplicable;
	}
	public void incNA() {
		this.notApplicable++;
	}

}
