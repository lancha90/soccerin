package com.dmha.soccerin.utils;

public class Singleton {

	
	 private static String username = "lancha90";
	 private static String email = "diegomao627@gmail.com";
	 private static String name = "diego mauricio herrera alzate";
	 private static String profile = "MF";
	 private static double ranking = 9.5;
	 private static double level = 9.5;
	 private static String position = "GK";
	 private static int old = 22;
	 private static String city = "Armenia, Q";
	 private static String idUser = "857aa80e024bc0d9dac7f49b8da686d6?s=200";
     private Singleton(){}

    public static String getIdUser() {
		return idUser;
	}
    
	public static String getUsername() {
		return username;
     }
	public static void setUsername(String username) {
		Singleton.username = username;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		Singleton.email = email;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Singleton.name = name;
	}
	public static String getPosition() {
		return position;
	}
	public static void setPosition(String position) {
		Singleton.position = position;
	}
	public static String getCity() {
		return city;
	}
	public static void setCity(String city) {
		Singleton.city = city;
	}
	public static double getRanking() {
		return ranking;
	}
	public static void setRanking(double ranking) {
		Singleton.ranking = ranking;
	}
	public static double getLevel() {
		return level;
	}
	public static void setLevel(double level) {
		Singleton.level = level;
	}
	public static String getProfile() {
		return profile;
	}
	public static void setProfile(String profile) {
		Singleton.profile = profile;
	}
	public static int getOld() {
		return old;
	}
	public static void setOld(int old) {
		Singleton.old = old;
	}
	

}
