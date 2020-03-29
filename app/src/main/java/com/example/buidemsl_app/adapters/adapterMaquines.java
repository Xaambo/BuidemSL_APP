package com.example.buidemsl_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.buidemsl_app.MainActivity;
import com.example.buidemsl_app.R;
import com.example.buidemsl_app.ui.main.FragmentGMaps;
import com.example.buidemsl_app.ui.main.FragmentMaquines;

public class adapterMaquines extends android.widget.SimpleCursorAdapter {

    private static final String noStock = "#BFD78290";
    private static final String wStock = "#FFFFFF";

    private MainActivity fragmentMaquines;

    public adapterMaquines(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
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