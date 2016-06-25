package com.romainwn.test;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.romainwn.test.model.Client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int NOTIF_SYNCHRO_ID = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onFormClient(View view){
        Intent form = new Intent(HomeActivity.this, AddClientActivity.class);
        startActivity(form);

    }

    public void onListClients(View view){
        Intent intent = new Intent(HomeActivity.this, ListClientActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_refresh:
                synchro();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }

    }

    public void synchro(){

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://ama-gestion-clients.appspot.com/rest/client");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.connect();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                    String json =input.readLine();
                    Log.d(TAG, "doInBackground: "+ json);

                    JSONArray array = new JSONArray(json);
                    for (int i = 0; i < array.length() ; i++) {
                        JSONObject object = array.getJSONObject(i);
                        String lastname = object.getString("nom");
                        String firstname = object.getString("prenom");
                        String email = object.getString("email");
                        Client.Sexe sexe =
                                (object.getString("sexe").equals("HOMME"))? Client.Sexe.HOMME : Client.Sexe.FEMME;
                        String level = object.getString("niveau");
                        boolean active = object.getBoolean("actif");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthdate = sdf.parse(object.getString("dateNaissance"));
                        Client client = new Client(lastname,firstname, email, birthdate, level, sexe, active);
                        Client.getClients().add(client);

                    }

                } catch (Exception e) {
                    Log.e(TAG, "synchro: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                NotificationManager manager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this);
                builder.setContentTitle("Titre")
                        .setContentText("Sous titre")
                        .setContentInfo("info")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(android.R.drawable.ic_popup_sync);
                Notification notification = builder.build();

                manager.notify(NOTIF_SYNCHRO_ID, notification);
            }
        };
        task.execute();

    }
}
