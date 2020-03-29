package com.example.buidemsl_app.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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
import com.example.buidemsl_app.adapters.adapterMaquines;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class FragmentMaquines extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;
    private DatasourceDB bd;
    private adapterMaquines scMaquines;

    private static String[] from = new String[]{};
    private static int[] to = new int[]{};

    public FragmentMaquines() {}

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

        //scMaquines = new adapterMaquines(getActivity(), R.layout.layout_zona, cursorMaquines, from, to, 1);

        ListView lv = getActivity().findViewById(R.id.listMaquines);
        lv.setAdapter(scMaquines);
    }

    public void FABActionListener() {
        FloatingActionButton add = getActivity().findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMaquina(v);
            }
        });
    }

    private void addMaquina(View v) {

        Snackbar.make(v, "Maquines", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }
}