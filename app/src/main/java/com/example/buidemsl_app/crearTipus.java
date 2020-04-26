package com.example.buidemsl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class crearTipus extends AppCompatActivity {

    long idTipus;
    DatasourceDB bd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_tipus);

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

        // El codi d'article ha d'estar informat i ha de ser únic
        edt = findViewById(R.id.edtNomZona);
        String nomTipus = edt.getText().toString();

        if (nomTipus.length() == 0) {
            Snackbar.make(findViewById(android.R.id.content), "El nom del tipus ha d'estar informat.", Snackbar.LENGTH_LONG).show();
            return;
        }

        idTipus = bd.AfegirTipus(nomTipus);

        Intent i = new Intent();
        i.putExtra("id", idTipus);
        setResult(RESULT_OK, i);

        finish();
    }

    private void cancelar() {
        Intent i = new Intent();
        i.putExtra("id", idTipus);
        setResult(RESULT_CANCELED, i);

        finish();
    }
}
