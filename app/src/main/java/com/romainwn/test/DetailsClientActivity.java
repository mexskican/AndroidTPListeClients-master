package com.romainwn.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.romainwn.test.model.Client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class DetailsClientActivity extends AppCompatActivity {
    public static final int TAKE_PICTURE_REQUEST_CODE =1;
    TextView tName, tFirstName, tSex, tAge, tEmail, tNiveau, tActif;
    int pos;
    public static final String TAG = MainActivity.class.getSimpleName();
    private Client c;
    private ImageView clientImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_client);
        pos = getIntent().getIntExtra("pos",0);
        List<Client> clients = Client.getClients();
        c = clients.get(pos);
        tName = (TextView) findViewById(R.id.lastnameDetail);
        tName.setText(c.getLastname());

        tFirstName = (TextView) findViewById(R.id.firstnameDetail);
        tFirstName.setText(c.getFirstname());

        tSex = (TextView) findViewById(R.id.sexDetail);
        if (c.getSexe() == Client.Sexe.HOMME){
            tSex.setText("HOMME");
        }
        else {
            tSex.setText("Femme");
        }

        tEmail = (TextView) findViewById(R.id.emailDetail);
        tEmail.setText(c.getEmail());

        tAge = (TextView) findViewById(R.id.ageDetail);
        //tAge.setText(String.valueOf(c.getAge()));

        tNiveau = (TextView) findViewById(R.id.niveauDetail);
        tNiveau.setText(c.getLevel());

        tActif= (TextView) findViewById(R.id.actifDetail);
        if (c.isActive()) {
            tActif.setText("Oui");
        }
        else {
            tActif.setText("Non");
        }
        clientImageView = (ImageView) findViewById(R.id.clientImageView);

        FileInputStream input = null;
        try {
            input = openFileInput("picture_" + c.getLastname());
            Bitmap bm = BitmapFactory.decodeStream(input);
            clientImageView.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            if(c.getSexe() == Client.Sexe.HOMME){
                clientImageView.setImageResource(R.drawable.man);
            }else{
                clientImageView.setImageResource(R.drawable.woman);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case R.id.item_delete:
                Client.getClients().remove(pos);
                Log.d(TAG,String.valueOf(pos));
                finish();
                return true;

            case R.id.action_settings:
                Intent form = new Intent(DetailsClientActivity.this, SettingsActivity.class);
                startActivity(form);
                return true;

            case R.id.item_mail:
                Uri uri = Uri.parse("mailto:" + c.getEmail());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onTakePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case TAKE_PICTURE_REQUEST_CODE :
                if (resultCode == RESULT_OK){
                    Bitmap bitmap = data.getParcelableExtra("data");
                    clientImageView.setImageBitmap(bitmap);
                    FileOutputStream output =null;
                    try {
                         output
                                = openFileOutput("picture_" + c.getLastname(), Context.MODE_PRIVATE);

                    } catch (FileNotFoundException e) {
                        Log.e(TAG, "onActivityResult "+e.getMessage());
                    }
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                }
                break;
            default:
                Log.d(TAG, "request "+requestCode+"code unknown");
        }
    }
}
