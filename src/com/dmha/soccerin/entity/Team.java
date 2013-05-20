package com.dmha.soccerin.entity;

public class Team {

	private String id;
	private String user;
	private String name;
	private String description;
	private String image;

	public Team(String id,String user, String name, String description, String image) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.description = description;
		this.image = image;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
