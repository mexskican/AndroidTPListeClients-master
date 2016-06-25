package com.romainwn.test.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.romainwn.test.R;

import java.util.List;

public class ClientAdapter extends ArrayAdapter<Client> {
    public ClientAdapter(Context context, List<Client> objects){
        super(context,0,objects);
    }

    public View getView(int position, View view, ViewGroup parent){
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.client_entry, parent, false);
        }
        Client client = getItem(position);
        TextView titre = (TextView)view.findViewById(R.id.nomClient);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        String list_order_pref = prefs.getString("list_order_pref", "LASTAME_FIRSTNAME");

        if("LASTAME_FIRSTNAME".equals(list_order_pref)){
            titre.setText(client.getLastname() + " " + client.getFirstname());
        } else {
            titre.setText(client.getFirstname() + " " + client.getLastname());
        }

        return view;
    }


}
