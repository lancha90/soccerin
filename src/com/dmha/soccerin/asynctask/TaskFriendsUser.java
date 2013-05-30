package com.dmha.soccerin.asynctask;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dmha.soccerin.activity.Profile;
import com.dmha.soccerin.entity.Friend;
import com.github.kevinsawicki.http.HttpRequest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TaskFriendsUser extends AsyncTask<Map<String, String>, String, String> {
	
	private ProgressDialog progDailog;
	private Profile activity;
	
	public TaskFriendsUser(Profile activity){
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
				
				ArrayList<Friend> friends = new ArrayList<Friend>();
				JSONArray jsonArray = new JSONArray(result);
				
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					JSONObject jsonData = jsonObject.getJSONObject("fields");
					JSONArray jsonFriends = jsonData.getJSONArray("friends");
					
					for (int j = 0; j < jsonFriends.length(); j++) {
						String[] datos;
						datos = jsonFriends.get(j).toString().split(" - ");
						friends.add(new Friend(datos[0], datos[1]));
					}
					
				}
				
				
				activity.loadDataFriends(friends);
			} catch (JSONException e) {
				Toast.makeText(activity, "catch", Toast.LENGTH_LONG).show();

				e.printStackTrace();
			}
			
			
		}
		
		progDailog.dismiss();
	}

	private String getInformation(Map<String, String> data ) {
		
		String url=data.get("url");
		
		data.remove("url");
		
		HttpRequest httpRequest = HttpRequest.post(url).form(data);

		if (httpRequest.code() != 404) {
			return httpRequest.body();
		} else {
			return "404";
		}

	}

	

}
