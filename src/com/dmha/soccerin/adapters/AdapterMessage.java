package com.dmha.soccerin.adapters;

import java.util.ArrayList;

import com.dmha.soccerin.activity.R;
import com.dmha.soccerin.entity.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterMessage extends ArrayAdapter {

	private Context mContext;
	private ArrayList<Message> contenidos;

	public AdapterMessage(Context c, ArrayList<Message> contenidos) {
		super(c, R.layout.item_message, contenidos);
		mContext = c;
		this.contenidos = contenidos;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		View MyView = convertView;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		MyView = inflater.inflate(R.layout.item_message, null);

		TextView user = (TextView) MyView.findViewById(R.id.profile_message_user);
		user.setText(contenidos.get(position).getUser());
		
		TextView date = (TextView) MyView.findViewById(R.id.profile_message_date);
		date.setText(contenidos.get(position).getDate());
		
		
		TextView body = (TextView) MyView.findViewById(R.id.profile_message_body);
		body.setText(contenidos.get(position).getBody());
		
		return MyView;
	}


}
