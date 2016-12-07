package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.lb.auto_fit_textview.AutoResizeTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 *
 */

public class FragmentoEleccion extends Fragment {
    private Elemento correcto;
    private Configuracion configuracion;

    public FragmentoEleccion(){};

    public FragmentoEleccion(Elemento correcto, Configuracion configuracion) {
        this.correcto = correcto;
        this.configuracion = configuracion;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmento_eleccion, container, false);
    }

    public void setCorrecto(Elemento correcto) {
        this.correcto = correcto;
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // nose puede usar getView antes de onCreateView()
        LinearLayout imageContainer = (LinearLayout) getView().findViewById(R.id.imagenesContainer);

        for( ImageView img: conjuntoImagenes()){
            imageContainer.addView(img);
        }

        inicializarTitulo();
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * este metodo inicializa el titulo en base al Elemento correcto
     */
    private void inicializarTitulo() {
        LinearLayout tituloContainer = (LinearLayout) getActivity().findViewById(R.id.tituloContainer);


        //click listener sonido
        View.OnClickListener clickListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), correcto.getIdSonido(configuracion.getSexoVoz()));
                mp.start();
            }
        };

        //crear texto
        TextView tw = new AutoResizeTextView(getActivity());
        String text = this.getString(correcto.getIdString());
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        tw.setText(text);


        LinearLayout.LayoutParams twLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        twLP.weight=1f;
        twLP.gravity= Gravity.CENTER;
        tw.setLayoutParams(twLP);
        tw.setGravity(Gravity.CENTER);
        tw.setSingleLine(true);
        tw.setTextSize(Dimension.SP, 200);

        //click listener para mayor comodidad del usuario
        //tw.setOnClickListener(clickListener);
        //tw.setClickable(true);

        tituloContainer.addView(tw);


        //crear icono sonido
        ImageView ic_sound = new ImageView(getActivity());
        ic_sound.setImageResource(R.mipmap.ic_sound);
        ic_sound.setOnClickListener(clickListener);

        LinearLayout.LayoutParams soundLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        soundLP.gravity=Gravity.CENTER;
        ic_sound.setLayoutParams(soundLP);
        tituloContainer.addView(ic_sound);

        if( configuracion.isTemporizado() ) {
            setearTimmer();
        }
        else{
            getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
        }

    }

    private void setearTimmer() {
        final ProgressBar bar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        Integer maxSec = configuracion.getMaxSegundos()*1000;
        bar.setMax(maxSec);
        bar.setVisibility(View.VISIBLE);
        new CountDownTimer(maxSec, 1000) {

            public void onTick(long millisUntilFinished) {
                Integer progreso = (int)millisUntilFinished;
                bar.setProgress(progreso);
            }

            public void onFinish() {
                bar.setProgress(0);
                //TODO que pasa cuando pierde?
            }
        }.start();
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
                MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.correcto);
                mp.start();

                //TODO accion click correcto (cambiar esto)
                ActivityJuego jp = (ActivityJuego)getActivity();
                jp.proximaEleccion();

            }
        };

        //click listener sonido incorrecto
        View.OnClickListener clickListenerIncorrecto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.resoplido);
                mp.start();
            }
        };

        List<ImageView> lista = new ArrayList<ImageView>();
        List<Elemento> falsos = new ArrayList<Elemento>(EnumSet.allOf(Elemento.class));
        falsos.remove(correcto);
        Collections.shuffle(falsos);
        Log.e("pref_nivel_seleccionado",configuracion.getNivel().toString());
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
        ImageView img = new ImageView(getActivity());
        img.setImageResource(e.getIdImagen());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.CENTER_VERTICAL;
        lp.weight=1f;
        img.setLayoutParams(lp);

        return img;
    }
}
