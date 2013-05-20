package com.dmha.soccerin.adapters;

import java.util.ArrayList;

import com.dmha.soccerin.activity.R;
import com.dmha.soccerin.entity.Event;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterEvent extends ArrayAdapter {

	private Context mContext;
	private ArrayList<Event> contenidos;

	public AdapterEvent(Context c, ArrayList<Event> contenidos) {
		super(c, R.layout.item_events, contenidos);
		mContext = c;
		this.contenidos = contenidos;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		View MyView = convertView;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		MyView = inflater.inflate(R.layout.item_events, null);

		TextView date = (TextView) MyView.findViewById(R.id.item_team_name);
		date.append(" "+contenidos.get(position).getDate());
		
		TextView field = (TextView) MyView.findViewById(R.id.item_event_field);
		field.append(" "+contenidos.get(position).getField());
		
		TextView duration = (TextView) MyView.findViewById(R.id.item_event_duration);
		duration.append(" "+contenidos.get(position).getDuration());
		
		TextView user = (TextView) MyView.findViewById(R.id.item_team_user);
		user.append(" "+contenidos.get(position).getUser());
		
		return MyView;
	}


}
