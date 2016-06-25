package com.romainwn.test.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Romain on 24/05/2016.
 */
public class ClientDBOpenHelper extends SQLiteOpenHelper {

    public ClientDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    public void onCreate(SQLiteDatabase db) {
        // Création de la base
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Mise à jour de la bdd existante
    }
}
