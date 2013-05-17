package com.dmha.soccerin.activity;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Chat extends Activity implements IOCallback {

	private LinearLayout listView;
	private EditText message;

	private String TAG = "dmha";
	private String URL_SOCKET = "http://107.20.186.237:8000/";
	private SocketIO io;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		if (Utils.isGetActionBar()) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		listView = (LinearLayout) findViewById(R.id.notification);
		message = (EditText) findViewById(R.id.message);

		try {
			io = new SocketIO();
			io.connect(URL_SOCKET, this);
			// Sends a string to the server.

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Metodo encargado de enviar un mensaje la sistemas de notificaciones
	 * 
	 * @param view
	 */
	public void sendMessage(View view) {

		addMessage("lancha90", message.getText().toString());

		try {
			JSONObject json = new JSONObject();

			json.put("nick", "lancha90");
			json.put("msj", message.getText().toString());

			io.emit("msj-send", json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void back(View view) {
		finish();
	}

	public void addMessage(String nick, String msj){
		
		LayoutInflater inflater = getLayoutInflater();
		View item = inflater.inflate(R.layout.item_instant_message, null);

		TextView lblNick = (TextView) item.findViewById(R.id.instant_nick);
		lblNick.setText(nick);

		TextView lblMessage = (TextView) item
				.findViewById(R.id.instant_message);
		lblMessage.setText(msj);

		listView.addView(item);
	}
	
	public void onMessage(JSONObject json, IOAcknowledge ack) {
		try {
			Log.i(TAG, "Server said:" + json.toString(2));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void onMessage(String data, IOAcknowledge ack) {
		Log.i(TAG, "Server said: " + data);
	}

	public void onError(SocketIOException socketIOException) {
		Log.i(TAG, "an Error occured");
		socketIOException.printStackTrace();
	}

	public void onDisconnect() {
		Log.i(TAG, "Connection terminated.");
	}

	public void onConnect() {
		Log.i(TAG, "Connection established");
	}

	/**
	 * recibe un mensaje
	 */
	public void on(String event, IOAcknowledge ack, Object... args) {
		Log.i(TAG, "Server triggered event '" + event + "'");
		if (event.equals("msj-receive")) {
			JSONObject params = (JSONObject) args[0];
			Message msg = new Message();
			msg.obj = params;
			mHandler.sendMessage(msg);

		}

	}
	
	Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            
        	try {
				
        		JSONObject params = (JSONObject) msg.obj;
        		
				String json_nick = params.getString("nick");
				String json_message = params.getString("msj");
				
				addMessage(json_nick, json_message);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i(TAG, "Server triggered event ' error en el json '");
				e.printStackTrace();
			}
        	
        }
};

}
