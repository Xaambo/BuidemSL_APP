package com.example.buidemsl_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class crearMaquina extends AppCompatActivity {

    long idMaquina;
    DatasourceDB bd;
    Border border = new Border();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_maquina);

        LinearLayout linearLayout;

        LayerDrawable layerDrawable = border.getBorders(Color.WHITE, Color.BLACK, 0, 0, 1, 0);

        linearLayout = findViewById(R.id.linear_CP_City);
        linearLayout.setBackground(layerDrawable);

        layerDrawable = border.getBorders(Color.WHITE, Color.BLACK, 0, 0, 1, 0);

        linearLayout = findViewById(R.id.linear_Tel_Email);
        linearLayout.setBackground(layerDrawable);

        bd = new DatasourceDB(this);

        final EditText edtDatePicker = findViewById(R.id.edtDatePicker);
        edtDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtDatePicker);
            }
        });

        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aceptar();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    private void showDatePickerDialog(final EditText edtDatePicker) {
        final DatePickerFragment picker = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                month = month + 1;

                String formatedDay = String.valueOf(day);
                String formatedMonth = String.valueOf(month);

                if (day < 10) {
                    formatedDay = "0" + day;
                }

                if (month < 10) {
                    formatedMonth = "0" + month;
                }

                //final String selectedDate = formatedDay + "/" + formatedMonth + "/" + year;
                final String selectedDate = year + "-" + formatedMonth + "-" + formatedDay;
                edtDatePicker.setText(selectedDate);
            }
        });

        picker.show(getSupportFragmentManager(), "datePicker");
    }

    private void aceptar() {
        // Validem les dades
        EditText edt;
        String numeroSerie, data;

        edt = findViewById(R.id.edtNomZona);
        numeroSerie = edt.getText().toString();

        Cursor maquina = bd.existeixMaquina(numeroSerie);

        if (maquina.moveToFirst()) {
            Snackbar.make(findViewById(android.R.id.content), "Ja existeix una màquina amb aquest número de serie", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (numeroSerie.length() == 0) {
            Snackbar.make(findViewById(android.R.id.content), "El numero de serie ha d'estar informat", Snackbar.LENGTH_LONG).show();
            return;
        }

        // El nom de la maquina ha d'estar informat
        edt = findViewById(R.id.edtNomZona);
        String nomMaquina = edt.getText().toString();

        if (nomMaquina.length() == 0) {
            Snackbar.make(findViewById(android.R.id.content), "El nom de la maquina ha d'estar informat.", Snackbar.LENGTH_LONG).show();
            return;
        }

        edt = findViewById(R.id.edtDatePicker);
        data = edt.getText().toString();

        idMaquina = bd.AfegirMaquina(nomMaquina, "Calle Falsa", "08392", "LlavaCity", "963852741", "info@falso.com", numeroSerie, data, 1, 1);

        Intent i = new Intent();
        i.putExtra("id", idMaquina);
        setResult(RESULT_OK, i);

        finish();
    }

    private void cancelar() {
        Intent i = new Intent();
        i.putExtra("id", idMaquina);
        setResult(RESULT_CANCELED, i);

        finish();
    }
}
