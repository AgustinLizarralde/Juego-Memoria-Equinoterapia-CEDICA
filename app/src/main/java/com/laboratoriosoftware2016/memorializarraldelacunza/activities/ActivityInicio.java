package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.lb.auto_fit_textview.AutoResizeTextView;

public class ActivityInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //inicializa el contexto del singleton de la aplicacion
        Configuracion.createInstancia(this.getApplicationContext());

        Button jugar = (Button) findViewById(R.id.button_jugar);
        Button configurar = (Button) findViewById(R.id.button_configurar);

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),ActivityJuego.class));
            }
        });

        configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),ActivityConfigurar.class));
            }
        });
    }
}
