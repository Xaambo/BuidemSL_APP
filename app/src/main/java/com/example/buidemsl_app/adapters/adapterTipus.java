package com.example.buidemsl_app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import com.example.buidemsl_app.MainActivity;

public class adapterTipus extends android.widget.SimpleCursorAdapter {

    private static final String noStock = "#BFD78290";
    private static final String wStock = "#FFFFFF";

    private MainActivity fragmentMaquines;

    public adapterTipus(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        fragmentMaquines = (MainActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = super.getView(position, convertView, parent);

        // Agafem l'objecte de la view que es una LINEA DEL CURSOR
        Cursor maquines = (Cursor) getItem(position);



        return view;
    }
}