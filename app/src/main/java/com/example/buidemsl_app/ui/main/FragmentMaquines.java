package com.example.buidemsl_app.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.buidemsl_app.adapters.adapterMaquines;

import com.example.buidemsl_app.crearMaquina;
import com.example.buidemsl_app.crearZona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_OK;

public class FragmentMaquines extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;
    private DatasourceDB bd;
    private adapterMaquines scMaquines;

    private static String[] from = new String[]{DatasourceDB.MACHINE_NUMEROSERIE, DatasourceDB.MACHINE_NOMCLIENT};
    private static int[] to = new int[]{R.id.tvSNc, R.id.tvNomMaquina};

    public FragmentMaquines() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

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

        View root = inflater.inflate(R.layout.fragment_maquines_layout, container, false);

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

        FABActionListener();

        bd = new DatasourceDB(getActivity());

        Cursor cursorMaquines = bd.Maquines();

        scMaquines = new adapterMaquines(getActivity(), R.layout.layout_maquina, cursorMaquines, from, to, 1, FragmentMaquines.this);

        ListView lv = getActivity().findViewById(R.id.listMaquines);
        lv.setAdapter(scMaquines);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.layout_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.orderByNom:
                ordenarPerNom();
                return true;
            case R.id.orderByAdress:
                ordenarPerDireccio();
                return true;
            case R.id.orderByDate:
                ordenarPerData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ordenarPerNom() {

        Cursor cursorMaquines = bd.MaquinesPerNom();
        updateList(cursorMaquines, "Ordenant maquines per nom...");
    }

    private void ordenarPerDireccio() {

        Cursor cursorMaquines = bd.MaquinesPerDireccio();
        updateList(cursorMaquines, "Ordenant maquines per direcció...");
    }

    private void ordenarPerData() {

        Cursor cursorMaquines = bd.MaquinesPerData();
        updateList(cursorMaquines, "Ordenant maquines per data de revisió...");
    }

    private void updateList(Cursor c, String missatge) {

        scMaquines.changeCursor(c);
        scMaquines.notifyDataSetChanged();

        ListView lv = getActivity().findViewById(R.id.listMaquines);
        lv.setSelection(0);

        Snackbar.make(getActivity().findViewById(android.R.id.content), missatge, Snackbar.LENGTH_LONG).show();
    }

    public void FABActionListener() {
        FloatingActionButton add = getActivity().findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMaquina();
            }
        });
    }

    private void addMaquina() {

        Intent i = new Intent(getActivity(), crearMaquina.class);

        i.putExtra("id" , -1);

        startActivityForResult(i, 1);

    }

    public void editMaquina(int id) {

        Intent i = new Intent(getActivity(), crearMaquina.class);

        i.putExtra("id" , id);

        startActivityForResult(i, 1);

    }

    public void eliminarMaquina(final long _id) {
        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Desitja eliminar la maquina?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.EliminarMaquina(_id);
                carregaMaquines();
            }
        });

        builder.setNegativeButton("No", null);

        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // Carreguem totes les zones a lo bestia
            carregaMaquines();
        }
    }

    private void carregaMaquines() {

        Cursor cursorMaquines;

        cursorMaquines = bd.Maquines();

        // Un cop hem creat la maquina li diem al adapter que li hem canviat les dades i que s'actualitzi
        scMaquines.changeCursor(cursorMaquines);
        scMaquines.notifyDataSetChanged();
    }
}