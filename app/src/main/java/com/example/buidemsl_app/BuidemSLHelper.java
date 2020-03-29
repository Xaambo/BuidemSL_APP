package com.example.buidemsl_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BuidemSLHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;

    // database name
    private static final String database_NAME = "BuidemSLDataBase";

    public BuidemSLHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    String CREATE_MAQUINES =
        "CREATE TABLE maquines ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "direccio TEXT," +
                "codipostal TEXT," +
                "poblacio TEXT," +
                "telefon INTEGER," +
                "email TEXT," +
                "numeroserie TEXT," +
                "datarevisio TEXT," +
                "tipus INTEGER," +
                "zona INTEGER," +
                "FOREIGN KEY(tipus) REFERENCES tipusmaquina(_id) ON DELETE RESTRICT," +
                "FOREIGN KEY(zona) REFERENCES zonamaquina(_id) ON DELETE RESTRICT)";

    String CREATE_TIPUS =
        "CREATE TABLE tipusmaquina ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descripcio TEXT)";

    String CREATE_ZONES =
        "CREATE TABLE zonamaquina ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomzona TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAQUINES);
        db.execSQL(CREATE_TIPUS);
        db.execSQL(CREATE_ZONES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // jaja
    }
}