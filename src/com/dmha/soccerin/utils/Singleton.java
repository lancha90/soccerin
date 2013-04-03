package com.dmha.soccerin.utils;

public class Singleton {

	
	 private static String username = "lancha90";
	 private static String email = "diegomao627@gmail.com";
	 private static String name = "diego mauricio herrera alzate";
	 private static int position = 1;
	 private static int edad = 22;
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
		Singleton.idUser = Utils.md5(email);
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Singleton.name = name;
	}
	public static int getPosition() {
		return position;
	}
	public static void setPosition(int position) {
		Singleton.position = position;
	}
	public static String getEdad() {
		return ""+edad;
	}
	public static void setEdad(int edad) {
		Singleton.edad = edad;
	}
	public static String getCity() {
		return city;
	}
	public static void setCity(String city) {
		Singleton.city = city;
	}

}
