package com.dmha.soccerin.activity;

import java.util.HashMap;
import java.util.Map;

import com.dmha.soccerin.asynctask.TaskUserInformation;
import com.dmha.soccerin.utils.Singleton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {

	private EditText username;
	private EditText passwd;
	private Button init;

	static String TWITTER_CONSUMER_KEY = "PD4vj1rLarjmVe6HmSZ7XQ";
	static String TWITTER_CONSUMER_SECRET = "jBhOCrFsqxhloWhWjilnAZ2YEMQ5d4Mcg6dX051Yk";

	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	static final String TWITTER_CALLBACK_URL = "T4J_OAuth://callback_main";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	
     
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.login_username);
		passwd = (EditText) findViewById(R.id.login_passwd);
		init = (Button) findViewById(R.id.login_init);

		username.setText("lancha90");
		passwd.setText("e10adc3949ba59abbe56e057f20f883e");

		init.setOnClickListener(this);

        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "soccerin", 0);
        

	}

	public void onClick(View v) {

		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("passwd", passwd.getText().toString());
		credentials.put("username", username.getText().toString());
		credentials.put("url", getString(R.string.url_login));
		
		Singleton.setPasswd(passwd.getText().toString());

		new TaskUserInformation(this).execute(credentials);
	}

	public void goToMain() {
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
	}

	public void goTwitter(View v) {
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

	private void share() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT,
				"Te invito a conocer SoccerIn. via @soccerin");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}
	
}
