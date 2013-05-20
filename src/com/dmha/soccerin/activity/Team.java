package com.dmha.soccerin.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dmha.soccerin.adapters.AdapterEvent;
import com.dmha.soccerin.adapters.AdapterTeam;
import com.dmha.soccerin.asynctask.TaskEventAll;
import com.dmha.soccerin.asynctask.TaskEventCreate;
import com.dmha.soccerin.asynctask.TaskEventUser;
import com.dmha.soccerin.asynctask.TaskTeamAll;
import com.dmha.soccerin.asynctask.TaskTeamUser;
import com.dmha.soccerin.utils.Singleton;
import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Team extends Activity {

	public float init_x;
	public ViewFlipper vf;
	
	private ListView listAllTeam;
	private ListView listMyTeam;
	
	private TextView teamName;
	private TextView teamDescription;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team);
		
		vf = (ViewFlipper) findViewById(R.id.vf_team);
		
		listAllTeam = (ListView) findViewById(R.id.list_all_team);
		listMyTeam = (ListView) findViewById(R.id.list_my_team);
		
		teamName = (TextView) findViewById(R.id.team_name);
		teamDescription = (TextView) findViewById(R.id.team_description);
		
		if (Utils.isGetActionBar()) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		getMyTeams();
		getAllTeams();
		
		listAllTeam.setOnTouchListener(new ListenerTouchViewFlipper());
		listMyTeam.setOnTouchListener(new ListenerTouchViewFlipper());
		vf.setOnTouchListener(new ListenerTouchViewFlipper());
	}
	
	/**
	 * Metodo encargado de consultar los eventos del usuario
	 */
	private void getMyTeams() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("email", Singleton.getEmail());
		params.put("url", getString(R.string.url_my_team));

		new TaskTeamUser(this).execute(params);

	}

	/**
	 * Metodo encargado de consultar los eventos existentes en la plataforma
	 */
	private void getAllTeams() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("url", getString(R.string.url_all_team));

		new TaskTeamAll(this).execute(params);

	}
	
	/**
	 * Metodo encargado de listar los eventos del usuario
	 * 
	 * @param myEvents
	 */
	public void loadDataMyTeam(
			ArrayList<com.dmha.soccerin.entity.Team> myTeams) {

		listMyTeam.setAdapter(new AdapterTeam(this, myTeams));
	}

	/**
	 * Metodo encargado de listar los eventos de la plataforma
	 * 
	 * @param myEvents
	 */
	public void loadDataAllTeam(
			ArrayList<com.dmha.soccerin.entity.Team> myTeams) {

		listAllTeam.setAdapter(new AdapterTeam(this, myTeams));
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


	/**
	 * Metodo encargado de visualizar la vista de adicionar evento
	 * 
	 * @param view
	 */
	public void goToTeam(View view) {
		vf.setDisplayedChild(2);
	}

	/**
	 * Metodo encargado de visualizar la vista de mis eventos
	 * 
	 * @param view
	 */
	public void goToMyTeam(View view) {
		vf.setDisplayedChild(1);
	}
	/**
	 * Metodo encargado de visualizar la vista de mis eventos
	 * 
	 * @param view
	 */
	public void goToAllTeam(View view) {
		vf.setDisplayedChild(0);
	}
	
	
	/**
	 * Clase encargada de permitir pasar de interface para los eventos
	 * 
	 * @author dmha
	 * 
	 */
	private class ListenerTouchViewFlipper implements View.OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: // Cuando el usuario toca la pantalla
											// por primera vez
				init_x = event.getX();
				return true;
			case MotionEvent.ACTION_UP: // Cuando el usuario deja de presionar
				float distance = init_x - event.getX();

				if (distance > 60) {
					vf.showPrevious();
				}

				if (distance < -60) {
					vf.showNext();
				}

			default:
				break;
			}

			return false;
		}

	}
}
