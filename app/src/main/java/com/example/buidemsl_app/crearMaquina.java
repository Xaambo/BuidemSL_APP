package com.example.buidemsl_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

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

        linearLayout = findViewById(R.id.linear_Tel_Email);
        linearLayout.setBackground(layerDrawable);

        bd = new DatasourceDB(this);

        final Spinner tipus = findViewById(R.id.spinnerTipus);
        carregaSpinnerTipus(tipus);

        final Spinner zones = findViewById(R.id.spinnerZona);
        carregaSpinnerZones(zones);

        final EditText edtDatePicker = findViewById(R.id.edtDatePicker);
        edtDatePicker.setText(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));

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
                aceptar(tipus, zones);
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

    private void carregaSpinnerTipus(Spinner tipus) {

        Cursor cTipus = bd.TipusMaquines();

        if (cTipus.getCount() < 1) {
            dialogNoTipusZones();
        }

        String[] from = new String[] {DatasourceDB.TYPE_DESC};
        int[] to = new int[] {android.R.id.text1};

        SimpleCursorAdapter sAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cTipus, from, to, 1);

        tipus.setAdapter(sAdapter);

    }

    private void carregaSpinnerZones(Spinner zones) {

        Cursor cZones = bd.Zones();

        if (cZones.getCount() < 1) {
            dialogNoTipusZones();
        }

        String[] from = new String[] {DatasourceDB.ZONE_NOM};
        int[] to = new int[] {android.R.id.text1};

        SimpleCursorAdapter sAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cZones, from, to, 1);

        zones.setAdapter(sAdapter);

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

    private void aceptar( Spinner tipus, Spinner zones) {
        // Validem les dades
        EditText edt;
        String numeroSerie, nomMaquina, adreca, codiPostal, ciutat, tel, email, data;
        int intTipus, intZona;

        edt = findViewById(R.id.edtSN);

        numeroSerie = edt.getText().toString();

        Cursor maquina = bd.existeixMaquina(numeroSerie);

        if (maquina.moveToFirst()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Ja existeix una màquina amb aquest número de serie.");
            builder.setPositiveButton("Ok", null);

            builder.show();

            return;
        }

        if (numeroSerie.length() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("El numero de serie ha d'estar informat.");
            builder.setPositiveButton("Ok", null);

            builder.show();

            return;
        }

        edt = findViewById(R.id.edtNomMaquina);
        nomMaquina = edt.getText().toString();

        if (nomMaquina.length() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("El nom de la maquina ha d'estar informat.");
            builder.setPositiveButton("Ok", null);

            builder.show();

            return;
        }

        edt = findViewById(R.id.edtAdreca);
        adreca = edt.getText().toString();

        if (adreca.length() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("L'adreça ha d'estar informada.");
            builder.setPositiveButton("Ok", null);

            builder.show();

            return;
        }

        edt = findViewById(R.id.edtCP);
        codiPostal = edt.getText().toString();

        if (codiPostal.length() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("El codi postal ha d'estar informat.");
            builder.setPositiveButton("Ok", null);

            builder.show();

            return;
        }

        edt = findViewById(R.id.edtCiutat);
        ciutat = edt.getText().toString();

        if (ciutat.length() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("La ciutat ha d'estar informada.");
            builder.setPositiveButton("Ok", null);

            builder.show();

            return;
        }

        edt = findViewById(R.id.edtTel);
        tel = edt.getText().toString();

        edt = findViewById(R.id.edtEmail);
        email = edt.getText().toString();

        if (!emailValid(email)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("L'email ha de ser vàlid.");
            builder.setPositiveButton("Ok", null);

            builder.show();

            return;
        }

        edt = findViewById(R.id.edtDatePicker);
        data = edt.getText().toString();

        Cursor item = (Cursor) tipus.getSelectedItem();
        intTipus = (int) item.getLong(item.getColumnIndexOrThrow(DatasourceDB.ZONE_ID));

        item = (Cursor) zones.getSelectedItem();
        intZona = (int) item.getLong(item.getColumnIndexOrThrow(DatasourceDB.ZONE_ID));

        idMaquina = bd.AfegirMaquina(nomMaquina, adreca, codiPostal, ciutat, tel, email, numeroSerie, data, intTipus, intZona);

        Intent i = new Intent();
        i.putExtra("id", idMaquina);
        setResult(RESULT_OK, i);

        finish();
    }

    private void dialogNoTipusZones() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("No hi ha tipus de maquines o zones creades.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.show();
    }

    private void cancelar() {
        Intent i = new Intent();
        i.putExtra("id", idMaquina);
        setResult(RESULT_CANCELED, i);

        finish();
    }

    private boolean emailValid(String email) {

        return email.contains("@");
    }
}
