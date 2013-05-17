package com.dmha.soccerin.entity;

public class Event {

	private String id;
	private String date;
	private String field;
	private String duration;
	private String user;
	
	public Event(String id, String date, String field, String duration, String user) {
		super();
		this.id = id;
		this.date = date;
		this.field = field;
		this.duration = duration;
		this.user = user;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
