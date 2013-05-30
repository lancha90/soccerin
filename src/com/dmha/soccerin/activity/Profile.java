package com.dmha.soccerin.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dmha.soccerin.adapters.AdapterEvent;
import com.dmha.soccerin.adapters.AdapterFriends;
import com.dmha.soccerin.adapters.AdapterMessage;
import com.dmha.soccerin.asynctask.TaskDownloadImages;
import com.dmha.soccerin.asynctask.TaskEventUser;
import com.dmha.soccerin.asynctask.TaskFriendsUser;
import com.dmha.soccerin.asynctask.TaskMessageUser;
import com.dmha.soccerin.asynctask.TaskUserInformation;
import com.dmha.soccerin.asynctask.TaskUserUpdate;
import com.dmha.soccerin.entity.Friend;
import com.dmha.soccerin.entity.Message;
import com.dmha.soccerin.utils.Singleton;
import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Profile extends Activity {

	private ViewFlipper vf;
	
	private ImageView profilePhoto;
	private ImageView profilePosition;

	private TextView profileName;
	private TextView profileUsername;
	private TextView profileCity;
	private TextView profileYears;
	private TextView profileLevel;
	private TextView profileRanking;

	private EditText profileInputName;
	private EditText profileInputEmail;
	private EditText profileInputPassword;
	private Spinner profileInputPosition;
	
	private ListView listMyFriends;
	private ListView listMyInbox;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		vf = (ViewFlipper) findViewById(R.id.vf_profile);
		
		profilePhoto = (ImageView) findViewById(R.id.profile_photo);
		profilePosition = (ImageView) findViewById(R.id.profile_position);

		profileName = (TextView) findViewById(R.id.profile_name);
		profileUsername = (TextView) findViewById(R.id.profile_username);
		profileCity = (TextView) findViewById(R.id.profile_city);
		profileYears = (TextView) findViewById(R.id.profile_old);
		profileRanking = (TextView) findViewById(R.id.profile_ranking);
		profileLevel = (TextView) findViewById(R.id.profile_level);

		profileInputName = (EditText) findViewById(R.id.profile_input_name);
		profileInputEmail = (EditText) findViewById(R.id.profile_input_email);
		profileInputPassword = (EditText) findViewById(R.id.profile_input_password);
		profileInputPosition = (Spinner) findViewById(R.id.profile_input_position);
		
		listMyFriends = (ListView) findViewById(R.id.list_my_friends);
		listMyInbox = (ListView) findViewById(R.id.list_my_inbox);

		setInformation();
		getDataFriends();
		getDataMessage();

		if (Utils.isGetActionBar()) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	private void setInformation() {
		String position = Singleton.getPosition();
		
		profileInputName.setText(Singleton.getName());
		profileInputEmail.setText(Singleton.getEmail());
		
		profileName.setText(Singleton.getName());
		profileUsername.setText(Singleton.getUsername());
		profileCity.setText(Singleton.getCity());
		profileYears.setText("" + Singleton.getOld());
		profileLevel.setText("" + Singleton.getLevel());
		profileRanking.setText("" + Singleton.getRanking());

		profilePhoto.setTag(this.getString(R.string.url_gravatar)
				+ Singleton.getIdUser());

		new TaskDownloadImages().execute(profilePhoto);

		
		
		if(position.equals("GK")) {			
			profilePosition.setImageResource(R.drawable.back);
		}else{
			profilePosition.setImageResource(R.drawable.settings);
		}
	}
	/**
	 * Metodo encargado de obtener la información de los amigos
	 */
	private void getDataFriends(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", Singleton.getUsername());
		params.put("url", getString(R.string.url_all_friends));

		new TaskFriendsUser(this).execute(params);
	}
	/**
	 * Metodo encargado de obtener la información de los amigos
	 */
	private void getDataMessage(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", Singleton.getUsername());
		params.put("url", getString(R.string.url_all_message));
		
		new TaskMessageUser(this).execute(params);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void back(View view) {
		finish();
	}

	public void updateUser(View view) {

		profileName.setText(profileInputName.getText().toString());
		
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("passwd", (profileInputPassword.getText().toString().length() > 6) ? profileInputPassword.getText().toString() : Singleton.getPasswd());
		credentials.put("username", profileUsername.getText().toString());
		credentials.put("name", profileInputName.getText().toString());
		credentials.put("email", profileInputEmail.getText().toString());
		credentials.put("url", getString(R.string.url_update_user));
		credentials.put("position", getPosition());
		
		new TaskUserUpdate(this).execute(credentials);
	}
	
	/**
	 * Metodo encargado de listar los amigos del usuario
	 * 
	 * @param myFriends
	 */
	public void loadDataFriends(ArrayList<Friend> myFriends) {

		listMyFriends.setAdapter(new AdapterFriends(this, myFriends));
	}
	
	/**
	 * Metodo encargado de listar los mensajes del usuario
	 * 
	 * @param myMessage
	 */
	public void loadDataMessage(ArrayList<Message> myMessage) {

		listMyInbox.setAdapter(new AdapterMessage(this, myMessage));
	}
	
	private String getPosition(){
		String position = profileInputPosition.getSelectedItem().toString();
		
		if(position.equals("Arquero")){
			return "GK";
		}else if(position.equals("Defensa")){
			return "DF";
		} else if(position.equals("Volante")){
			return "MF";
		}else{
			return "FW";
		}

	
	}

	public void goToProfile(View view) {
		vf.setDisplayedChild(0);
	}
	public void goToUpdate(View view) {
		vf.setDisplayedChild(1);
	}
	public void goToFriend(View view) {
		vf.setDisplayedChild(2);
	}
	public void goToMessage(View view) {
		vf.setDisplayedChild(3);
	}


}
