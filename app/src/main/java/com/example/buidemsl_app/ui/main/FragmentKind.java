package com.example.buidemsl_app.ui.main;

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
import com.example.buidemsl_app.adapters.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class FragmentKind extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;
    private DatasourceDB bd;
    private adapterTipus scTipus;

    private static String[] from = new String[]{};
    private static int[] to = new int[]{};

    public FragmentKind() {}

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

        View root = inflater.inflate(R.layout.fragment_kind_layout, container, false);

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

        Cursor cursorKind = bd.TipusMaquines();

        scTipus = new adapterTipus(getActivity(), R.layout.layout_zona, cursorKind, from, to, 1);

        ListView lv = getActivity().findViewById(R.id.listTipus);
        lv.setAdapter(scTipus);
    }

    public void FABActionListener() {
        FloatingActionButton add = getActivity().findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTipus(v);
            }
        });
    }

    private void addTipus(View v) {

        Snackbar.make(v, "Tipus", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }
}