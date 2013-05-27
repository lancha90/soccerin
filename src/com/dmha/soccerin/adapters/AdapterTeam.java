package com.dmha.soccerin.adapters;

import java.util.ArrayList;

import com.dmha.soccerin.activity.R;
import com.dmha.soccerin.entity.Event;
import com.dmha.soccerin.entity.Team;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterTeam extends ArrayAdapter {

	private Context mContext;
	private ArrayList<Team> team;

	public AdapterTeam(Context c, ArrayList<Team> team) {
		super(c, R.layout.item_teams, team);
		mContext = c;
		this.team = team;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		View MyView = convertView;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		MyView = inflater.inflate(R.layout.item_teams, null);

		TextView name = (TextView) MyView.findViewById(R.id.item_team_name);
		name.setText(team.get(position).getName());
		
		TextView user = (TextView) MyView.findViewById(R.id.item_team_user);
		user.setText(team.get(position).getUser());
		
		
		
		return MyView;
	}


}
