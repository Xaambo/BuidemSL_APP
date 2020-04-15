package com.example.buidemsl_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class crearMaquina extends AppCompatActivity {

    long idMaquina;
    DatasourceDB bd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_zona);

        bd = new DatasourceDB(this);

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

    private void aceptar() {
        // Validem les dades
        EditText edt;
        String numeroSerie;

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

        idMaquina = bd.AfegirMaquina(nomMaquina, "Calle Falsa", "08392", "LlavaCity", "963852741", "info@falso.com", numeroSerie, "1999/01/26", 1, 1);

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
