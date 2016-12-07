package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Configuracion de la aplicacion extraida de las SharedPreferences (tambien las guarda)
 */

public class Configuracion {
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editorPreferencias;
    List<Elemento> elementos = new ArrayList<>();
    private Context context;


    /**
     * Al crearse la configuracion extrae las preferencias almacenadas
     * @param context contexto del cual obtener las preferencias
     */
    public Configuracion(Context context) {
        this.context = context;
        this.preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        this.editorPreferencias = this.preferencias.edit();

        //TODO sacar y usar una lista guardadoa

        elementos.add(Elemento.CABALLO);
        elementos.add(Elemento.MATRA);
        elementos.add(Elemento.OJOS);
        elementos.add(Elemento.BOZAL);
    }

    public Nivel getNivel() {
        return Nivel.valueOf(preferencias.getString(context.getString(R.string.key_nivel),Nivel.INICIAL.toString()));
    }

    public void setNivel(Nivel nivel) {
        this.editorPreferencias.putString(context.getString(R.string.key_nivel), nivel.toString());
        this.editorPreferencias.commit();
    }

    public boolean isTemporizado() {
        return preferencias.getBoolean(context.getString(R.string.key_temporizado),false);
    }

    public void setTemporizado(boolean temporizado) {
        this.editorPreferencias.putBoolean(context.getString(R.string.key_temporizado), temporizado);
        this.editorPreferencias.commit();
    }

    public Integer getMaxSegundos(){
        Integer num = Integer.valueOf(preferencias.getString(context.getString(R.string.key_tiempo_max),"30"));
        return num;
    }

    public void setMaxSegundos(Integer integer) {
        this.editorPreferencias.putString(context.getString(R.string.key_tiempo_max), integer.toString());
        this.editorPreferencias.commit();
    }

    public Sexo getSexoVoz() {
        Log.e("pref_sexo",Sexo.valueOf(preferencias.getString(context.getString(R.string.key_sexo_voz),Sexo.MASCULINO.toString())).toString());
        return Sexo.valueOf(preferencias.getString(context.getString(R.string.key_sexo_voz),Sexo.MASCULINO.toString()));
    }

    public void setSexoVoz(Sexo sexo) {
        this.editorPreferencias.putString(context.getString(R.string.key_sexo_voz), sexo.toString());
        this.editorPreferencias.commit();
    }

    public List<Elemento> getElementos() {
        return elementos;
    }

}
