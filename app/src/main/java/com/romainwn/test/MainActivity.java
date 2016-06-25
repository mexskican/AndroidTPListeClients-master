package com.romainwn.test;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Spinner sp;
    ArrayAdapter <CharSequence> adapter;
    SeekBar seek_bar;
    TextView age;
    Switch switcherActif;
    RadioGroup radio_group;
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int AGE_MIN = 18;
    public static final int AGE_MAX = 65;

    EditText nom,prenom,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = (Spinner)findViewById(R.id.spinner);
        switcherActif = (Switch) findViewById(R.id.switchActivate);
        adapter = ArrayAdapter.createFromResource(this,R.array.niveau, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        radio_group = (RadioGroup)findViewById(R.id.radio_group);
        seekBar();


    }

    public void seekBar(){
        seek_bar = (SeekBar) findViewById(R.id.seekBar);
        age = (TextView) findViewById(R.id.tvAge);
        age.setText(seek_bar.getProgress() + " ans");
        seek_bar.setMax(AGE_MAX-AGE_MIN);
        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        age.setText(String.valueOf(progress+AGE_MIN)+ " ans");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // age.setText(age.setText(String.valueOf(progress+AGE_MIN))+ " ans");
                    }
                });

    }

    public void onAddButton(View view){

        nom = (EditText)findViewById(R.id.tNom);
        prenom = (EditText)findViewById(R.id.tPrenom);
        email = (EditText)findViewById(R.id.tMail);
        age = (TextView)findViewById(R.id.tvAge);


        Log.d(TAG,"Nom : " + nom.getText().toString() );
        Log.d(TAG, "Prenom : " + prenom.getText().toString());
        Log.d(TAG, "Email : " + email.getText().toString());
        Log.d(TAG,"Age : " + age.getText().toString() );
    }

}
