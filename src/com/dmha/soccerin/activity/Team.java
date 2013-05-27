package com.dmha.soccerin.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dmha.soccerin.adapters.AdapterEvent;
import com.dmha.soccerin.adapters.AdapterTeam;
import com.dmha.soccerin.asynctask.TaskEventAll;
import com.dmha.soccerin.asynctask.TaskEventCreate;
import com.dmha.soccerin.asynctask.TaskEventUser;
import com.dmha.soccerin.asynctask.TaskTeamAddUser;
import com.dmha.soccerin.asynctask.TaskTeamAll;
import com.dmha.soccerin.asynctask.TaskTeamUser;
import com.dmha.soccerin.utils.Singleton;
import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Team extends Activity implements OnItemClickListener{

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
	
	private void suscriptionTema(String team){
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", getString(R.string.url_add_user_team));
		params.put("user", Singleton.getUsername());
		params.put("team", team);

		new TaskTeamAddUser(this).execute(params);
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
		listAllTeam.setOnItemClickListener(this);
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
	
	public void onItemClick(AdapterView<?> arg0, final View view, int i, long arg3) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.team_confirm))
		        .setTitle("Advertencia")
		        .setCancelable(false)
		        .setNegativeButton(getResources().getString(R.string.team_confirm_no),
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                        dialog.cancel();
		                    }
		                })
		        .setPositiveButton(getResources().getString(R.string.team_confirm_yes),
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                        
		                    	
		                    	TextView text = (TextView) view.findViewById(R.id.item_team_name);
		                    	suscriptionTema(text.getText().toString());
		                    	
		                    	
		                    }
		                });
		AlertDialog alert = builder.create();
		alert.show();
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
