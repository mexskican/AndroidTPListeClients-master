package com.romainwn.test;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.romainwn.test.model.Client;
import com.romainwn.test.model.ClientAdapter;

import java.util.List;

public class ListClientActivity extends ListActivity {

    private ClientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Client> clients = Client.getClients();
        adapter = new ClientAdapter(this, clients);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent detail = new Intent(ListClientActivity.this,DetailsClientActivity.class);
        detail.putExtra("pos",position);
        startActivity(detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
