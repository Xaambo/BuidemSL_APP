package com.example.buidemsl_app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.buidemsl_app.DatasourceDB;
import com.example.buidemsl_app.MainActivity;
import com.example.buidemsl_app.R;
import com.example.buidemsl_app.ui.main.FragmentZones;

public class adapterZones extends android.widget.SimpleCursorAdapter {

    private FragmentZones fragmentZones;

    public adapterZones(Context context, int layout, Cursor c, String[] from, int[] to, int flags, FragmentZones fragment) {
        super(context, layout, c, from, to, flags);
        fragmentZones = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = super.getView(position, convertView, parent);

        // Agafem l'objecte de la view que es una LINEA DEL CURSOR

        ImageView accioCela = view.findViewById(R.id.btnDelZona);

        accioCela.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Cursor linia = getLinia(v);

            fragmentZones.eliminarZona(linia.getLong(linia.getColumnIndexOrThrow(DatasourceDB.ZONE_ID)));

            }
        });

        accioCela = view.findViewById(R.id.btnEditZona);

        accioCela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Cursor linia = getLinia(v);

            fragmentZones.editZona(linia.getInt(linia.getColumnIndexOrThrow(DatasourceDB.ZONE_ID)));

            }
        });

        return view;
    }

    private Cursor getLinia(View v) {

        // Busquem la linia a eliminar
        View row = (View) v.getParent().getParent();
        // Busquem el listView per poder treure el numero de la fila
        ListView lv = (ListView) row.getParent().getParent();
        // Busco quina posicio ocupa la Row dins de la ListView
        int position = lv.getPositionForView(row);

        // Carrego la linia del cursor de la posició.
        return (Cursor) getItem(position);
    }
}