package com.dmha.soccerin.asynctask;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dmha.soccerin.activity.Event;
import com.github.kevinsawicki.http.HttpRequest;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class TaskFieldAll extends
		AsyncTask<Map<String, String>, String, String> {

	private ProgressDialog progDailog;
	private Event activity;

	public TaskFieldAll(Event activity) {
		this.activity = activity;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		progDailog = new ProgressDialog(activity);
		progDailog.setMessage("Loading...");
		progDailog.setIndeterminate(false);
		progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDailog.setCancelable(true);
		progDailog.show();
	}

	protected String doInBackground(Map<String, String>... arg) {
		return getInformation(arg[0]);
	}

	/**
	 * El sistema invoca éste método para realizar el trabajo en el hilo
	 * principal y entregar el resultado hecho por el hilo trabajador.
	 */
	protected void onPostExecute(String result) {
		if (result.equals("404")) {

			// context.finish();
		} else {

			try {

				ArrayList<String> fields= new ArrayList<String>();

				JSONArray jsonArray = new JSONArray(result);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					JSONObject jsonData = jsonObject.getJSONObject("fields");

					fields.add(jsonData.getString("name"));
				}

				activity.loadDataFileds(fields);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		progDailog.dismiss();
	}

	/**
	 * Metodo encagado de realizar la petición para obtener los eventos de la
	 * persona que se encuentra logueado el momento
	 * 
	 * @param data
	 *            -> url y email
	 * @return respuesta
	 */
	private String getInformation(Map<String, String> data) {

		String url = data.get("url");
		data.remove("url");
		
		HttpRequest httpRequest = HttpRequest.get(url);

		if (httpRequest.code() != 404) {
			return httpRequest.body();
		} else {
			return "404";
		}

	}

}
