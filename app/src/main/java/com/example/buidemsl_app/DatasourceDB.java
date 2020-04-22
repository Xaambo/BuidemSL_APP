package com.example.buidemsl_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatasourceDB {

    public static final String table_MACHINE = "maquines";
    public static final String MACHINE_ID = "_id";
    public static final String MACHINE_NOMCLIENT = "nom";
    public static final String MACHINE_DIRECCIO = "direccio";
    public static final String MACHINE_CP = "codipostal";
    public static final String MACHINE_CIUTAT = "poblacio";
    public static final String MACHINE_TELEFON = "telefon";
    public static final String MACHINE_EMAIL = "email";
    public static final String MACHINE_NUMEROSERIE = "numeroserie";
    public static final String MACHINE_DATAREVISIO = "datarevisio";
    public static final String MACHINE_TIPUS = "tipus";
    public static final String MACHINE_ZONA = "zona";

    public static final String table_ZONE = "zonamaquina";
    public static final String ZONE_ID = "_id";
    public static final String ZONE_NOM = "nomzona";

    public static final String table_TYPE = "tipusmaquina";
    public static final String TYPE_ID = "_id";
    public static final String TYPE_DESC = "descripcio";

    private BuidemSLHelper dbHelper;
    private SQLiteDatabase dbR,dbW;

    /** CONTRUCTOR */
    
    public DatasourceDB(Context ctx) {

        dbHelper = new BuidemSLHelper(ctx);
        open();
    }

    protected void finalize() {
        dbR.close();
        dbW.close();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }

    /** TYPES CRUD */

    public Cursor TipusMaquines() {

        //return dbR.query(table_TYPE, new String[]{TYPE_ID, TYPE_DESC},null,null,null,null, TYPE_ID);
        return dbR.rawQuery("SELECT * FROM " + table_TYPE + " ORDER BY 1", null);
    }

    public long AfegirTipus(String tipus) {

        ContentValues values = new ContentValues();
        values.put(TYPE_DESC, tipus);

        return dbW.insert(table_TYPE,null, values);
    }

    public void ActualitzarTipus(long id, String type) {

        ContentValues values = new ContentValues();
        values.put(TYPE_DESC,type);

        dbW.update(table_TYPE,values,TYPE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void EliminarTipus(long id) {
        dbW.delete(table_TYPE,TYPE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    /** ZONE CRUD */

    public Cursor Zones() {

        //return dbR.query(table_ZONE, new String[]{ZONE_ID,ZONE_NOM},null,null,null,null,ZONE_ID);
        return dbR.rawQuery("SELECT * FROM " + table_ZONE + " ORDER BY 1", null);
    }

    public long AfegirZona(String nom) {

        ContentValues values = new ContentValues();
        values.put(ZONE_NOM,nom);

        return dbW.insert(table_ZONE,null,values);
    }

    public void ActualizarZona(long id, String nom) {

        ContentValues values = new ContentValues();
        values.put(ZONE_NOM, nom);

        dbW.update(table_ZONE,values,ZONE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void EliminarZona(long id) {
        
        dbW.delete(table_ZONE,ZONE_ID + " = ?", new String[]{String.valueOf(id)});
        
    }

    /** MACHINE CRUD */

    public Cursor Maquines() {
        
        //return dbR.query(table_MACHINE, new String[]{MACHINE_ID, MACHINE_NOMCLIENT, MACHINE_DIRECCIO, MACHINE_CP, MACHINE_CIUTAT, MACHINE_TELEFON, MACHINE_EMAIL, MACHINE_NUMEROSERIE, MACHINE_DATAREVISIO, MACHINE_TIPUS, MACHINE_ZONA},null,null, null,null, MACHINE_ID);
        return dbR.rawQuery("SELECT * FROM " + table_MACHINE + " ORDER BY 1", null);
    }

    public Cursor existeixMaquina(String numeroSerie) {

        String[] args = new String[] {numeroSerie};

        //return dbR.query(table_MACHINE, new String[]{MACHINE_ID, MACHINE_NOMCLIENT, MACHINE_DIRECCIO, MACHINE_CP, MACHINE_CIUTAT, MACHINE_TELEFON, MACHINE_EMAIL, MACHINE_NUMEROSERIE, MACHINE_DATAREVISIO, MACHINE_TIPUS, MACHINE_ZONA},null,null, null,null, MACHINE_ID);
        return dbR.rawQuery("SELECT " + MACHINE_NUMEROSERIE + " FROM " + table_MACHINE + " WHERE " + MACHINE_NUMEROSERIE + " = ?", args);
    }

    public long AfegirMaquina(String client, String adreca, String codiPostal, String city, String telefon, String email, String numeroserie, String datarevisio, int tipus, int zona) {

        ContentValues values = new ContentValues();
        values.put(MACHINE_NOMCLIENT, client);
        values.put(MACHINE_DIRECCIO, adreca);
        values.put(MACHINE_CP, codiPostal);
        values.put(MACHINE_CIUTAT, city);
        values.put(MACHINE_TELEFON, telefon);
        values.put(MACHINE_EMAIL, email);
        values.put(MACHINE_NUMEROSERIE, numeroserie);
        values.put(MACHINE_DATAREVISIO, datarevisio);
        values.put(MACHINE_ZONA, zona);
        values.put(MACHINE_TIPUS, tipus);

        return dbW.insert(table_MACHINE,null, values);
    }

    public void ActualitzarMaquina(long id, String client, String adreca, String codiPostal, String city, String telefon, String email, String numeroserie, String datarevisio, int zona, int tipus) {

        ContentValues values = new ContentValues();
        values.put(MACHINE_NOMCLIENT, client);
        values.put(MACHINE_DIRECCIO, adreca);
        values.put(MACHINE_CP, codiPostal);
        values.put(MACHINE_CIUTAT, city);
        values.put(MACHINE_TELEFON, telefon);
        values.put(MACHINE_EMAIL, email);
        values.put(MACHINE_NUMEROSERIE, numeroserie);
        values.put(MACHINE_DATAREVISIO, datarevisio);
        values.put(MACHINE_ZONA, zona);
        values.put(MACHINE_TIPUS, tipus);

        dbW.update(table_MACHINE,values, MACHINE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void EliminarMaquina(long id) {

        dbW.delete(table_MACHINE,MACHINE_ID + " = ?",new String[]{String.valueOf(id)});

    }
}