package com.dmha.soccerin.asynctask;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dmha.soccerin.activity.Login;
import com.dmha.soccerin.activity.Main;
import com.dmha.soccerin.utils.Singleton;
import com.github.kevinsawicki.http.HttpRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UserInformation extends AsyncTask<Map<String, String>, String, String> {
	
	private ProgressDialog progDailog;
	private Login activity;
	
	public UserInformation(Login activity){
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
		if(result.equals("404")){
			
			//context.finish();
		}else{
			
			try {
				
				JSONArray jsonArray = new JSONArray(result);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				
				Singleton.setName(jsonObject.getString("name").toString());
				Singleton.setRanking(Double.parseDouble(jsonObject.getString("ranking").toString()));
				Singleton.setLevel(Double.parseDouble(jsonObject.getString("level").toString()));
				Singleton.setEmail(jsonObject.getString("email").toString());
				Singleton.setProfile(jsonObject.getString("profile").toString());

				activity.goToMain();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
		}
		
		progDailog.dismiss();
	}

	private String getInformation(Map<String, String> data ) {
		
		String url=data.get("url");
		
		data.remove("url");
		
		HttpRequest httpRequest = HttpRequest.post(url).form(data);
		

		Log.i("dmha", "codigo de respuesta: " + httpRequest.code());

		if (httpRequest.code() != 404) {
			return httpRequest.body();
		} else {
			return "404";
		}

	}

	

}
