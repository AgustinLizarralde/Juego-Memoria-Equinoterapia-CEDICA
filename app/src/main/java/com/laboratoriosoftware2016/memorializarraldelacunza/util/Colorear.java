package com.laboratoriosoftware2016.memorializarraldelacunza.util;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;

/**
 * Created by Agustin on 05/02/2017.
 */

public class Colorear implements Runnable {

    private int color;
    private ImageView imagen;
    private ImageView.ScaleType scaleTypeOriginal;
    private Drawable backgroundOriginal;
    private ViewGroup.LayoutParams layoutParamsOriginal;

    public Colorear(int color, ImageView imagen) {
        this.color = color;
        this.imagen = imagen;

        scaleTypeOriginal = imagen.getScaleType();
        backgroundOriginal = imagen.getBackground();
        layoutParamsOriginal = imagen.getLayoutParams();
        Configuracion.getInstancia().notJugando();

        imagen.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // Sets how the bitmap is scaled in it's container
        imagen.setBackgroundColor(color);       // Define the border color
        imagen.setPadding(2,2,2,2);                   // Define the border size
        imagen.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imagen.setClickable(false);
    }

    @Override
    public void run() {

        //volver a lo original
        if( imagen != null) {
            Configuracion.getInstancia().jugar();
            imagen.setScaleType(scaleTypeOriginal);
            imagen.setBackground(backgroundOriginal);
            imagen.setPadding(0, 0, 0, 0);
            imagen.setLayoutParams(layoutParamsOriginal);
            imagen.setClickable(true);
        }
    }
}
