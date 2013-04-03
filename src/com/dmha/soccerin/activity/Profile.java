package com.dmha.soccerin.activity;

import com.dmha.soccerin.utils.DownloadImagesTask;
import com.dmha.soccerin.utils.Singleton;
import com.dmha.soccerin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {

	private ImageView profilePhoto;
	private ImageView profilePosition;

	private TextView profileName;
	private TextView profileUsername;
	private TextView profileCity;
	private TextView profileYears;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		profilePhoto = (ImageView) findViewById(R.id.profile_photo);
		profilePosition = (ImageView) findViewById(R.id.profile_position);

		profileName = (TextView) findViewById(R.id.profile_name);
		profileUsername = (TextView) findViewById(R.id.profile_username);
		profileCity = (TextView) findViewById(R.id.profile_city);
		profileYears = (TextView) findViewById(R.id.profile_old);

		setInformation();

		if (Utils.isGetActionBar()) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	private void setInformation() {
		profileName.setText(Singleton.getName());
		profileUsername.setText(Singleton.getUsername());
		profileCity.setText(Singleton.getCity());
		profileYears.setText(Singleton.getEdad());

		profilePhoto.setTag(this.getString(R.string.url_gravatar)
				+ Singleton.getIdUser());
		new DownloadImagesTask().execute(profilePhoto);
		
		switch (Singleton.getPosition()) {
		case 1:
			// Arquero
			profilePosition.setImageResource(R.drawable.back);
			break;
		case 2:
			// Defensa
			break;
		case 3:
			// Volante
			break;
		case 4:
			// Delantero
			break;

		default:
			break;
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

	public void back(View view) {
		finish();
	}

}
