package com.dmha.soccerin.activity;

import java.util.HashMap;
import java.util.Map;

import com.dmha.soccerin.asynctask.TaskUserInformation;
import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener{
	
	private EditText username;
	private EditText passwd;
	private Button init;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		username = (EditText) findViewById(R.id.login_username);
		passwd = (EditText) findViewById(R.id.login_passwd);
		init = (Button) findViewById(R.id.login_init);
		
		username.setText("lancha90");
		passwd.setText("e10adc3949ba59abbe56e057f20f883e");
		
		init.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("passwd",passwd.getText().toString());
		credentials.put("username",username.getText().toString());
		credentials.put("url",getString(R.string.url_login));
		
		new TaskUserInformation(this).execute(credentials);
	}

	public void goToMain(){
		Intent intent = new Intent(this,Main.class);
		startActivity(intent);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.login, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_share:
	            share();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void share(){
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Te invito a conocer SoccerIn. via @soccerin");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}

}
