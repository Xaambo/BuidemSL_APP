package com.example.buidemsl_app.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buidemsl_app.BuidemSLHelper;
import com.example.buidemsl_app.DatasourceDB;
import com.example.buidemsl_app.MainActivity;
import com.example.buidemsl_app.R;
import com.example.buidemsl_app.adapters.*;

import com.example.buidemsl_app.crearZona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_OK;

public class FragmentZones extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;
    private DatasourceDB bd;
    private adapterZones scZones;

    private static String[] from = new String[]{DatasourceDB.ZONE_ID, DatasourceDB.ZONE_NOM};
    private static int[] to = new int[]{R.id.tvCodiZona, R.id.tvNomZona};

    public FragmentZones() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //pageViewModel.setIndex(index);
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_zones_layout, container, false);

        /*
        final TextView textView = root.findViewById(R.id.section_label);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bd = new DatasourceDB(getActivity());

        Cursor cursorZones = bd.Zones();

        scZones = new adapterZones(getActivity(), R.layout.layout_zona, cursorZones, from, to, 1, FragmentZones.this);

        ListView lv = getActivity().findViewById(R.id.listZones);
        lv.setAdapter(scZones);
    }

    public void FABActionListener() {
        FloatingActionButton add = getActivity().findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addZona();
            }
        });
    }

    private void addZona() {

        Intent i = new Intent(getActivity(), crearZona.class);
        startActivityForResult(i, 1);

    }

    public void eliminarZona(final long _id) {
        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Desitja eliminar la zona?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.EliminarZona(_id);
                carregaZones();
            }
        });

        builder.setNegativeButton("No", null);

        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // Carreguem totes les zones a lo bestia
            carregaZones();
        }
    }

    private void carregaZones() {

        Cursor cursorZones;

        cursorZones = bd.Zones();

        // Un cop fet el filtre li diem al cursor que hem canviat les seves dades i que s'actualitzi
        scZones.changeCursor(cursorZones);
        scZones.notifyDataSetChanged();
    }
}