package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.media.MediaPlayer;
import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * esta es la clase principal de la al¿plicacion
 */
public class JuegoPrincipal extends AppCompatActivity {

    private Elemento correcto = Elemento.MONTURA;
    private Configuracion configuracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configuracion = new Configuracion(this);
        configuracion.setNivel(Nivel.INICIAL);

        setContentView(R.layout.activity_juego_principal);

        //TODO sacar y usar una lista guardadoa
        LinearLayout imageContainer = (LinearLayout) findViewById(R.id.imagenesContainer);

        for( ImageView img: conjuntoImagenes()){
            imageContainer.addView(img);
        }

        inicializarTitulo();

    }

    /**
     * este metodo inicializa el titulo en base al Elemento correcto
     */
    private void inicializarTitulo() {
        LinearLayout tituloContainer = (LinearLayout) findViewById(R.id.tituloContainer);


        //click listener sonido
        View.OnClickListener clickListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), correcto.getIdSondioMasculino());
                mp.start();
            }
        };

        //crear texto
        TextView tw = new AutoResizeTextView(this);
        String text = this.getString(correcto.getIdString());
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        tw.setText(text);


        LinearLayout.LayoutParams twLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        twLP.weight=1f;
        twLP.gravity=Gravity.CENTER;
        tw.setLayoutParams(twLP);
        tw.setGravity(Gravity.CENTER);
        tw.setSingleLine(true);
        tw.setTextSize(Dimension.SP, 200);

        //click listener para mayor comididad del usuario
        //tw.setOnClickListener(clickListener);
        //tw.setClickable(true);

        tituloContainer.addView(tw);


        //crear icono sonido
        ImageView ic_sound = new ImageView(this);
        ic_sound.setImageResource(R.mipmap.ic_sound);
        ic_sound.setOnClickListener(clickListener);

        LinearLayout.LayoutParams soundLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        soundLP.gravity=Gravity.CENTER;
        ic_sound.setLayoutParams(soundLP);
        tituloContainer.addView(ic_sound);
    }

    /**
     * Devuelve un conjunto de imagenes a mostrar como opciones.
     * Esto es el elemento correcto mas una cantidad de opciones extras random para llenar el espacio hasta alcanzar el maximo de opciones del nivel seleccionado en la configuracion
     * @return lista de ImageView
     */
    private List<ImageView> conjuntoImagenes(){

        //click listener sonido correcto
        View.OnClickListener clickListenerCorrecto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correcto);
                mp.start();
                //TODO accion click correcto
            }
        };

        //click listener sonido incorrecto
        View.OnClickListener clickListenerIncorrecto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.incorrecto);
                mp.start();
            }
        };

        List<ImageView> lista = new ArrayList<ImageView>();
        List<Elemento> falsos = new ArrayList<Elemento>(EnumSet.allOf(Elemento.class));
        falsos.remove(correcto);
        Collections.shuffle(falsos);

        for (Elemento e : falsos.subList(0,configuracion.getNivel().getMaxImagenes()-1)) {
            ImageView img = crearOpcion(e);
            img.setOnClickListener(clickListenerIncorrecto);
            lista.add(img);
        }

        ImageView img = crearOpcion(correcto);
        img.setOnClickListener(clickListenerCorrecto);
        lista.add(img);

        Collections.shuffle(lista);
        return lista;
    }

    /**
     * Crea un ImageView a mostrar a partir de los datos de un Elemento
     * @param e elemento a crear en la imagen
     * @return ImageView
     */
    private ImageView crearOpcion( Elemento e ){
        ImageView img = new ImageView(this);
        img.setImageResource(e.getIdImagen());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.CENTER_VERTICAL;
        lp.weight=1f;
        img.setLayoutParams(lp);

        return img;
    }
}
