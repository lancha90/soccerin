package com.dmha.soccerin.adapters;

import java.util.ArrayList;

import com.dmha.soccerin.activity.R;
import com.dmha.soccerin.entity.Friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterFriends extends ArrayAdapter {

	private Context mContext;
	private ArrayList<Friend> contenidos;

	public AdapterFriends(Context c, ArrayList<Friend> contenidos) {
		super(c, R.layout.item_friends, contenidos);
		mContext = c;
		this.contenidos = contenidos;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		View MyView = convertView;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		MyView = inflater.inflate(R.layout.item_friends, null);

		TextView name = (TextView) MyView.findViewById(R.id.item_friends_name);
		name.setText(contenidos.get(position).getName());
		
		TextView username = (TextView) MyView.findViewById(R.id.item_friends_username);
		username.setText(contenidos.get(position).getUsername());
		
		return MyView;
	}


}
