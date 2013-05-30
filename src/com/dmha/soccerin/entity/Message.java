package com.dmha.soccerin.entity;

public class Message {

	private String user;
	private String date;
	private String body;

	public Message(String user, String date, String body) {
		super();
		this.user = user;
		this.date = date;
		this.body = body;
	}
	
	public String getUser() {
		return user;
	}
	public String getDate() {
		return date;
	}
	public String getBody() {
		return body;
	}
}
