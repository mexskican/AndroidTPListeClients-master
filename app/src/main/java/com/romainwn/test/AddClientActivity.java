package com.romainwn.test;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.romainwn.test.model.Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddClientActivity extends AppCompatActivity {

    Spinner sp;
    ArrayAdapter<CharSequence> adapter;
    TextView age;
    Switch switcherActif;
    Button birthDateButton;
    RadioGroup radio_group;
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int AGE_MIN = 18;
    public static final int AGE_MAX = 65;

    EditText nom, prenom, email;
    String vNom, vPrenom, vEmail, vNiveau;
    boolean actif;
    Date vAge;
    Client.Sexe sexe;
    private final Calendar dateSelected = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        sp = (Spinner) findViewById(R.id.spinner);
        switcherActif = (Switch) findViewById(R.id.switchActivate);
        adapter = ArrayAdapter.createFromResource(this, R.array.niveau, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        birthDateButton = (Button) findViewById(R.id.dateNaissance);

        radio_group = (RadioGroup) findViewById(R.id.radio_group);

    }


    public void onAddButton(View view) {

        nom = (EditText) findViewById(R.id.tNom);
        vNom = nom.getText().toString();
        Log.d(TAG,"Nom : " + vNom);

        prenom = (EditText) findViewById(R.id.tPrenom);
        vPrenom = prenom.getText().toString();
        Log.d(TAG,"prenom : " + vPrenom);

        email = (EditText) findViewById(R.id.tMail);
        vEmail = email.getText().toString();
        Log.d(TAG,"email : " + vEmail);

        Date birthDate = dateSelected.getTime();

        int checkedRadioButtonId = radio_group.getCheckedRadioButtonId();
        Client.Sexe vsexe = (checkedRadioButtonId == R.id.rbHomme) ? Client.Sexe.HOMME : Client.Sexe.FEMME;

        Client c = new Client(vNom, vPrenom, vEmail, birthDate, vNiveau, vsexe, actif);

        Client.getClients().add(c);
        Toast toast = Toast.makeText(this,getString(R.string.client_added), Toast.LENGTH_SHORT);
        toast.show();
        finish();

    }

    public void pickDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        final int annee = calendar.get(Calendar.YEAR);
        final int mois = calendar.get(Calendar.MONTH);
        final int jour = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(Calendar.YEAR, year);
                dateSelected.set(Calendar.MONTH, monthOfYear);
                dateSelected.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                birthDateButton.setText(sdf.format(dateSelected.getTime()));
            }
        };
        DatePickerDialog datePick = new DatePickerDialog(this, listener, annee, mois, jour);
        datePick.show();
    }

}
