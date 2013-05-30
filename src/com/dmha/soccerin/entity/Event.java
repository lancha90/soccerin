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
	public String getDate() {
		return date;
	}
	public String getField() {
		return field;
	}
	public String getDuration() {
		return duration;
	}
	public String getUser() {
		return user;
	}
}
