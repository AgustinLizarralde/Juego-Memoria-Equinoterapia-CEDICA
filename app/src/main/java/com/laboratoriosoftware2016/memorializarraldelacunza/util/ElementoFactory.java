package com.laboratoriosoftware2016.memorializarraldelacunza.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Dimension;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.lb.auto_fit_textview.AutoResizeTextView;

/**
 * Created by Agustin on 05/12/2016.
 */
public class ElementoFactory {

    public static ImageView createImageView(Elemento e, final Context c){
        ImageView aux = new ImageView(c);
        aux.setImageResource(e.getIdImagen());
        return aux;
    }

    public static TextView createTextView(Elemento e, final Context c){
        TextView aux = new AutoResizeTextView(c);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        aux.setLayoutParams(lp);
        aux.setGravity(Gravity.CENTER);
        aux.setSingleLine(true);
        aux.setTextSize(Dimension.SP, 200);
        return aux;
    }

}
