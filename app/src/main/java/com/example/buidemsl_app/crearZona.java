package com.example.buidemsl_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class crearZona extends AppCompatActivity {

    long idZona;
    DatasourceDB bd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_zona);

        bd = new DatasourceDB(this);

        idZona = getIntent().getExtras().getInt("id");

        if (idZona != -1) {
            carregaDadesZona((int)idZona);
        }

        Button btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aceptar();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    private void carregaDadesZona(int id) {

        Cursor zona = bd.Zona(id);
        zona.moveToFirst();

        EditText edt;

        edt = findViewById(R.id.edtNomZona);
        edt.setText(zona.getString(zona.getColumnIndexOrThrow(DatasourceDB.ZONE_NOM)));
    }

    private void aceptar() {
        // Validem les dades
        EditText edt;

        // El codi d'article ha d'estar informat i ha de ser únic
        edt = findViewById(R.id.edtNomZona);
        String nomZona = edt.getText().toString();

        if (nomZona.length() == 0) {
            Snackbar.make(findViewById(android.R.id.content), "El nom de la zona ha d'estar informat.", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (idZona == -1) {
            idZona = bd.AfegirZona(nomZona);
        } else {
            bd.ActualizarZona(idZona, nomZona);
        }

        Intent i = new Intent();
        i.putExtra("id", idZona);
        setResult(RESULT_OK, i);

        finish();
    }

    private void cancelar() {
        Intent i = new Intent();
        i.putExtra("id", idZona);
        setResult(RESULT_CANCELED, i);

        finish();
    }
}
