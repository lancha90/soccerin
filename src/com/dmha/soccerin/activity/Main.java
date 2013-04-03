package com.dmha.soccerin.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class Main extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
	}

	public void goToCall(View view) {
		changeActivity(Call.class);
	}
	public void goToEvent(View view) {
		changeActivity(Event.class);
	}
	public void goToProfile(View view) {
		changeActivity(Profile.class);
	}
	public void goToTeam(View view) {
		changeActivity(Team.class);
	}
	public void goToChat(View view) {
		changeActivity(Chat.class);
	}
	
	private void changeActivity(Class<?> cls){
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

}
