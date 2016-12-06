package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Nivel;
import com.lb.auto_fit_textview.AutoResizeTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * esta es la clase principal de la aplicacion
 */
public class JuegoPrincipal extends android.support.v4.app.FragmentActivity {

    private Elemento correcto = Elemento.MONTURA;
    private Configuracion configuracion;
    //TODO esto es de prueba
    private Integer i=0;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configuracion = new Configuracion(this);
        configuracion.setNivel(Nivel.MEDIO);

        setContentView(R.layout.activity_juego_principal);
        fragmentManager = getSupportFragmentManager();

        iniciarTurno(configuracion.getElementos().get(i));
    }

    public void proximaEleccion(){
        i++;
        Log.e("indice",i.toString());
        if(i<configuracion.getElementos().size()){
            iniciarTurno(configuracion.getElementos().get(i));
        }
        else {
            Toast.makeText(this, "juego terminado", Toast.LENGTH_LONG).show();
        }
    }

    private void iniciarTurno(Elemento e){
        Log.e("turno",e.toString());
        FragmentTransaction FT = fragmentManager.beginTransaction();
        Fragment fragment = new FragmentoEleccion(e, this.configuracion);
        if( i == 0 ) {
            FT.add(R.id.activity_juego_principal, fragment);
        }
        else{
            FT.replace(R.id.activity_juego_principal, fragment);
        }
        FT.commit();
    }
}
