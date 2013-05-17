package com.dmha.soccerin.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dmha.soccerin.adapters.AdapterEvent;
import com.dmha.soccerin.asynctask.TaskEventUser;
import com.dmha.soccerin.asynctask.TaskUserInformation;
import com.dmha.soccerin.utils.Singleton;
import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class Event extends Activity {

	private ListView listEvents;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		
		listEvents = (ListView) findViewById(R.id.listEvents);

		if (Utils.isGetActionBar()) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		getMyEvents();
	}

	
	private void getMyEvents(){
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("email",Singleton.getEmail());
		params.put("url",getString(R.string.url_my_event));
		
		new TaskEventUser(this).execute(params);		
		
	}
	
	public void loadData(ArrayList<com.dmha.soccerin.entity.Event> myEvents){
			
		listEvents.setAdapter(new AdapterEvent(this, myEvents));
	}
	
	/**
	 * Metodo encargado de termina la actividad y retornar a la actividad anterior
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

	/**
	 * Metodo encargado de visualizar la vista de adicionar evento 
	 * @param view
	 */
	public void goToCreate(View view){
		Log.i("dmha", "goToCreate");
	}
	
	/**
	 * Metodo encargado de visualizar la vista de mis eventos
	 * @param view
	 */
	public void goToMyEvent(View view){
		Log.i("dmha", "goToMyEvent");
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
