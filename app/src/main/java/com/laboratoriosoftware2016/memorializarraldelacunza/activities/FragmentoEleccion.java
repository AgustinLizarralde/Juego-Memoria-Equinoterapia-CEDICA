package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.laboratoriosoftware2016.memorializarraldelacunza.util.Colorear;
import com.lb.auto_fit_textview.AutoResizeTextView;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Timer;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 *
 */

public class FragmentoEleccion extends Fragment {
    private Elemento correcto;
    private Configuracion configuracion;
    private CountDownTimer timer;
    private PopupWindow popupWindow;

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
        inicializarPopupTiempo();
    }

    @Override
    public void onResume() {
        LinearLayout imageContainer = (LinearLayout) getView().findViewById(R.id.imagenesContainer);
        imageContainer.removeAllViews();
        for (ImageView img : conjuntoImagenes()) {
            imageContainer.addView(img);
        }
        popupWindow.dismiss();
        configuracion.jugar();
        inicializarTitulo();
        super.onResume();
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * este metodo inicializa el titulo en base al Elemento correcto
     */
    private void inicializarTitulo() {
        LinearLayout tituloContainer = (LinearLayout) getActivity().findViewById(R.id.tituloContainer);
        TextView titulo = (TextView) getActivity().findViewById(R.id.elemento_text);
        ImageView boton_sonido = (ImageView) getActivity().findViewById(R.id.elemento_sonido);


        //click listener sonido
        View.OnClickListener clickListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), correcto.getIdSonido(configuracion.getSexoVoz()));
                mp.start();
            }
        };

        //crear texto
        String text = this.getString(correcto.getIdString());
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        titulo.setText(text);

        //click listener para mayor comodidad del usuario
        //tw.setOnClickListener(clickListener);
        //tw.setClickable(true);


        //crear icono sonido
        boton_sonido.setOnClickListener(clickListener);


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
        if (timer!=null) timer.cancel();
        timer = new CountDownTimer(maxSec, 1000) {

            public void onTick(long millisUntilFinished) {
                if(getActivity() != null && configuracion.isJugando()) {
                    Integer progreso = (int) millisUntilFinished;
                    bar.setProgress(progreso);
                }
            }

            public void onFinish() {
                if(getActivity() != null && configuracion.isJugando()){
                    bar.setProgress(0);
                    configuracion.notJugando();
                    popupWindow.showAtLocation(bar, Gravity.CENTER,0,0);
                }
            }
        };
        timer.start();
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
                if( configuracion.isJugando() ) {
                    MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.correcto);
                    mp.start();

                    long milis = 3500;
                    colorear(Color.GREEN, (ImageView) v, milis+500);

                    new ParticleSystem(getActivity(),10, R.mipmap.ic_star,2000)
                    .setRotationSpeed(144)
                    .setSpeedRange(0.1f, 0.2f)
                    .oneShot(v, 8);
                    final ActivityJuego jp = (ActivityJuego) getActivity();
                    Runnable run = new Runnable() {
                        @Override
                        public void run() {
                            jp.proximaEleccion();
                        }
                    };

                    Handler h = new Handler();
                    h.postDelayed(run,milis);
                }
            }
        };

        //click listener sonido incorrecto
        View.OnClickListener clickListenerIncorrecto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( configuracion.isJugando() ) {
                    MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.resoplido);
                    mp.start();
                    colorear(Color.RED, (ImageView) v, 2000);
                }
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
        ImageView img = new ImageView(getActivity());
        img.setImageResource(e.getIdImagen());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.CENTER_VERTICAL;
        lp.weight=1f;
        img.setLayoutParams(lp);

        return img;
    }

    private void colorear(int color, ImageView imagen, long milis){
        Handler h = new Handler();
        h.postDelayed(new Colorear(color, imagen),milis);
    }

    private void inicializarPopupTiempo(){
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

        Button btn_Cerrar = (Button)popupWindow.getContentView().findViewById(R.id.boton_seguir);
        btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                configuracion.jugar();
            }});

        Button btn_volver_inicio = (Button)popupWindow.getContentView().findViewById(R.id.boton_inicio);
        btn_volver_inicio.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),ActivityInicio.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }});
    }
}
