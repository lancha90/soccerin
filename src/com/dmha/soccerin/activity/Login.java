package com.dmha.soccerin.activity;

import java.util.HashMap;
import java.util.Map;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.dmha.soccerin.asynctask.TaskUserInformation;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

	static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	// Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
     
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

		
		
		// Check if twitter keys are set
        if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
            // Internet Connection is not present
            Toast.makeText(Login.this, "Twitter oAuth tokens. Please set your twitter oauth tokens first!", Toast.LENGTH_LONG).show();
            // stop executing code by return
            return;
        }
        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "soccerin", 0);
        
        
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
 
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
 
                    // Shared Preferences
                    Editor e = mSharedPreferences.edit();
 
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
 
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
 
                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();
                     
                    // Displaying in xml ui
                   Toast.makeText(this, username ,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
 
		
	}

	public void onClick(View v) {

		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("passwd", passwd.getText().toString());
		credentials.put("username", username.getText().toString());
		credentials.put("url", getString(R.string.url_login));

		new TaskUserInformation(this).execute(credentials);
	}

	public void goToMain() {
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
	}

	public void goTwitter(View v) {
		loginToTwitter();
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
	
	 private void loginToTwitter() {
	        // Check if already logged in
	        if (!isTwitterLoggedInAlready()) {
	            ConfigurationBuilder builder = new ConfigurationBuilder();
	            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
	            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
	            Configuration configuration = builder.build();
	             
	            TwitterFactory factory = new TwitterFactory(configuration);
	            twitter = factory.getInstance();
	 
	            try {
	                requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
	                
	                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
	            } catch (TwitterException e) {
	                e.printStackTrace();
	            }
	        } else {
	            // user already logged into twitter
	            Toast.makeText(getApplicationContext(),
	                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
	        }
	    }
	 
	    /**
	     * Check user already logged in your application using twitter Login flag is
	     * fetched from Shared Preferences
	     * */
	    private boolean isTwitterLoggedInAlready() {
	        // return twitter login status from Shared Preferences
	        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	    }

}
