package com.example.buidemsl_app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.buidemsl_app.DatasourceDB;
import com.example.buidemsl_app.R;
import com.example.buidemsl_app.ui.main.FragmentMaquines;
import com.example.buidemsl_app.ui.main.FragmentZones;

public class adapterMaquines extends android.widget.SimpleCursorAdapter {

    private FragmentMaquines fragmentMaquines;

    public adapterMaquines(Context context, int layout, Cursor c, String[] from, int[] to, int flags, FragmentMaquines fragment) {
        super(context, layout, c, from, to, flags);
        fragmentMaquines = fragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view = super.getView(position, convertView, parent);

        // Agafem l'objecte de la view que es una LINEA DEL CURSOR

        ImageView accioCela = view.findViewById(R.id.btnDelMaquina);

        accioCela.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Cursor linia = getLiniaCRUD(v);

            fragmentMaquines.eliminarMaquina(linia.getLong(linia.getColumnIndexOrThrow(DatasourceDB.MACHINE_ID)));

            }
        });

        accioCela = view.findViewById(R.id.btnEditMaquina);

        accioCela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Cursor linia = getLiniaCRUD(v);

            fragmentMaquines.editMaquina(linia.getInt(linia.getColumnIndexOrThrow(DatasourceDB.MACHINE_ID)));

            }
        });

        accioCela = view.findViewById(R.id.btnMail);

        accioCela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Cursor linia = getLinia(v);

            fragmentMaquines.mail(linia.getString(linia.getColumnIndexOrThrow(DatasourceDB.MACHINE_EMAIL)), linia.getString(linia.getColumnIndexOrThrow(DatasourceDB.MACHINE_NUMEROSERIE)));

            }
        });

        accioCela = view.findViewById(R.id.btnTel);

        accioCela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Cursor linia = getLinia(v);

            fragmentMaquines.trucar(linia.getInt(linia.getColumnIndexOrThrow(DatasourceDB.MACHINE_TELEFON)));

            }
        });

        return view;
    }

    private Cursor getLiniaCRUD(View v) {

        // Busquem la linia a eliminar
        View row = (View) v.getParent().getParent();
        // Busquem el listView per poder treure el numero de la fila
        ListView lv = (ListView) row.getParent().getParent();
        // Busco quina posicio ocupa la Row dins de la ListView
        int position = lv.getPositionForView(row);

        // Carrego la linia del cursor de la posició.
        return (Cursor) getItem(position);
    }

    private Cursor getLinia(View v) {

        // Busquem la linia a eliminar
        View row = (View) v.getParent();
        // Busquem el listView per poder treure el numero de la fila
        ListView lv = (ListView) row.getParent();
        // Busco quina posicio ocupa la Row dins de la ListView
        int position = lv.getPositionForView(row);

        // Carrego la linia del cursor de la posició.
        return (Cursor) getItem(position);
    }
}