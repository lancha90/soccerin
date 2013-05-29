package com.dmha.soccerin.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dmha.soccerin.adapters.AdapterEvent;
import com.dmha.soccerin.asynctask.TaskEventAll;
import com.dmha.soccerin.asynctask.TaskEventCreate;
import com.dmha.soccerin.asynctask.TaskEventUser;
import com.dmha.soccerin.asynctask.TaskFieldAll;
import com.dmha.soccerin.asynctask.TaskUserInformation;
import com.dmha.soccerin.utils.Singleton;
import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewFlipper;
import android.support.v4.app.NavUtils;

public class Event extends Activity implements OnItemClickListener {

	public float init_x;
	private ViewFlipper vf;
	private ListView listMyEvents;
	private ListView listAllEvents;

	private Spinner createField;
	private Spinner createDurationHours;
	private Spinner createDurationMinutes;
	private DatePicker createDate;
	private TimePicker createTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		vf = (ViewFlipper) findViewById(R.id.vf_profile);
		listMyEvents = (ListView) findViewById(R.id.list_my_team);
		listAllEvents = (ListView) findViewById(R.id.list_all_team);

		createField = (Spinner) findViewById(R.id.event_field);
		createDurationHours = (Spinner) findViewById(R.id.event_duration_hours);
		createDurationMinutes = (Spinner) findViewById(R.id.event_duration_minutes);
		createDate = (DatePicker) findViewById(R.id.event_date);
		createTime = (TimePicker) findViewById(R.id.event_hours);

		if (Utils.isGetActionBar()) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		getMyEvents();
		getAllEvents();
		getAllFields();

		listAllEvents.setOnTouchListener(new ListenerTouchViewFlipper());
		listMyEvents.setOnTouchListener(new ListenerTouchViewFlipper());
		// listCreateEvents.setOnTouchListener(new ListenerTouchViewFlipper());
		vf.setOnTouchListener(new ListenerTouchViewFlipper());
	}

	/**
	 * Metodo encargado de consultar los eventos del usuario
	 */
	private void getMyEvents() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("email", Singleton.getEmail());
		params.put("url", getString(R.string.url_my_event));

		new TaskEventUser(this).execute(params);

	}

	/**
	 * Metodo encargado de consultar los eventos existentes en la plataforma
	 */
	private void getAllEvents() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("url", getString(R.string.url_all_event));

		new TaskEventAll(this).execute(params);

	}

	/**
	 * Metodo encargado de consultar la lista de campos disponibles
	 */
	private void getAllFields() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("url", getString(R.string.url_all_fields));

		new TaskFieldAll(this).execute(params);

	}

	/**
	 * Metodo encargado de listar los eventos del usuario
	 * 
	 * @param myEvents
	 */
	public void loadDataMyEvent(
			ArrayList<com.dmha.soccerin.entity.Event> myEvents) {

		listMyEvents.setAdapter(new AdapterEvent(this, myEvents));
		listMyEvents.setOnItemClickListener(this);
	}

	/**
	 * Metodo encargado de listar los eventos de la plataforma
	 * 
	 * @param myEvents
	 */
	public void loadDataAllEvent(
			ArrayList<com.dmha.soccerin.entity.Event> myEvents) {

		listAllEvents.setAdapter(new AdapterEvent(this, myEvents));
		listAllEvents.setOnItemClickListener(this);
	}

	public void loadDataFileds(ArrayList<String> spinnerArray) {

		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, spinnerArray);
		createField.setAdapter(spinnerArrayAdapter);
	}

	/**
	 * Metodo encargado de terminar la actividad y retornar a la actividad
	 * anterior
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

	/**
	 * Metodo encargado de adicionar un nuevo evento
	 * 
	 * @param view
	 */
	public void createEvent(View view) {
		String field = createField.getSelectedItem().toString();
		String date = createDate.getYear() + "-" + createDate.getMonth() + "-"
				+ (createDate.getDayOfMonth() - 1) + " "
				+ createTime.getCurrentHour() + ":"
				+ createTime.getCurrentMinute() + ":00";
		String duration = createDurationHours.getSelectedItem().toString()
				+ "." + createDurationMinutes.getSelectedItem().toString();

		Map<String, String> params = new HashMap<String, String>();
		params.put("url", getString(R.string.url_create_event));

		params.put("user", Singleton.getEmail());
		params.put("field", field);
		params.put("date", date);
		params.put("duration", duration);

		new TaskEventCreate(this).execute(params);

	}

	/**
	 * Metodo encargado de visualizar la vista de adicionar evento
	 * 
	 * @param view
	 */
	public void goToCreate(View view) {
		vf.setDisplayedChild(2);
	}

	/**
	 * Metodo encargado de visualizar la vista de mis eventos
	 * 
	 * @param view
	 */
	public void goToMyEvent(View view) {
		vf.setDisplayedChild(1);
	}

	/**
	 * Metodo encargado de visualizar la vista de mis eventos
	 * 
	 * @param view
	 */
	public void goToAllEvent(View view) {
		vf.setDisplayedChild(0);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

		TextView date = (TextView) view.findViewById(R.id.item_team_name);
		TextView field = (TextView) view.findViewById(R.id.item_event_field);
		
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT,
				"Te invito a mi partido. "+field.getText().toString()+" "+date.getText().toString()+" via @soccerin");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
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
