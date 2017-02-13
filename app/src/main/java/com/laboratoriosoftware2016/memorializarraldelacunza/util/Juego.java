package com.laboratoriosoftware2016.memorializarraldelacunza.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.activities.ActivityInicio;
import com.laboratoriosoftware2016.memorializarraldelacunza.activities.ActivityJuego;
import com.laboratoriosoftware2016.memorializarraldelacunza.activities.FragmentoEleccion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.plattysoft.leonids.ParticleSystem;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Paw on 12/02/2017.
 */

public class Juego {

    private Configuracion configuracion;
    private ActivityJuego contexto;

    public Juego(Configuracion config, ActivityJuego context ){
        this.configuracion = config;
        this.contexto = context;
    }

    public void noJugando(){
        configuracion.notJugando();

        LayoutInflater layoutInflater = (LayoutInflater) this.contexto.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_ganaste, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

        Button boton_volver_jugar = (Button)popupView.findViewById(R.id.boton_volver_jugar);
        boton_volver_jugar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),ActivityJuego.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                contexto.finish();
                contexto.startActivity(i);
            }});

        if ( !configuracion.isMaximoNivel() ) {
            Button boton_subir_dificultad = (Button) popupView.findViewById(R.id.boton_subir_dificultad);
            boton_subir_dificultad.setVisibility(View.VISIBLE);
            boton_subir_dificultad.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    configuracion.aumentarNivel();
                    Intent i = new Intent(v.getContext(),ActivityJuego.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    contexto.finish();
                    contexto.startActivity(i);
                }
            });
        }

        Button boton_menu = (Button)popupView.findViewById(R.id.boton_menu);
        boton_menu.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),ActivityInicio.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                contexto.finish();
                contexto.startActivity(i);
            }});

        popupWindow.showAtLocation(contexto.findViewById(android.R.id.content), Gravity.CENTER,0,0);
        confeti(popupView);
    }


    public void proximaEleccion(){
        configuracion.proximoTurno();
        if(configuracion.getTurno()<configuracion.getElementos().size()){
            iniciarTurno();
        }
        else {
            noJugando();
        }
    }

    public void confeti(View v) {
        Point size = new Point();
        new ParticleSystem(contexto, 250, R.drawable.confeti2, 5000)
                .setSpeedRange(0.4f, 0.7f)
                .emit(v,250,5000);
    }

    public void iniciarTurno(){
        Elemento e = configuracion.getElementos().get(configuracion.getTurno());
        configuracion.jugar();
        TextView txt = (TextView) contexto.findViewById(R.id.toolbar_text);
        txt.setText(contexto.getString(R.string.nivel)+": " + configuracion.getNivel().toString().toLowerCase());
        FragmentTransaction FT = contexto.getFragmento().beginTransaction();
        Fragment fragment = new FragmentoEleccion(e, this.configuracion);
        FT.replace(R.id.activity_juego_principal, fragment);
        FT.commit();
    }
}
