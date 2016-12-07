package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Configuracion de la aplicacion extraida de las SharedPreferences (tambien las guarda)
 */

public class Configuracion {
    private Nivel nivel;
    private boolean temporizado;
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
        this.preferencias = context.getSharedPreferences(context.getString(R.string.key_perfil), MODE_PRIVATE);
        this.editorPreferencias = this.preferencias.edit();
        recargarPreferencias();

        //TODO sacar y usar una lista guardadoa

        elementos.add(Elemento.CABALLO);
        elementos.add(Elemento.MATRA);
        elementos.add(Elemento.MONTURA);
        elementos.add(Elemento.MONTURIN);
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
        this.editorPreferencias.putString(context.getString(R.string.key_nivel), this.nivel.toString());
        this.editorPreferencias.commit();
    }

    public boolean isTemporizado() {
        return temporizado;
    }

    public List<Elemento> getElementos() {
        return elementos;
    }

    public void setTemporizado(boolean temporizado) {
        this.temporizado = temporizado;
        this.editorPreferencias.putBoolean(context.getString(R.string.key_temporizado), this.temporizado);
        this.editorPreferencias.commit();
    }

    public void recargarPreferencias(){
        this.nivel = Nivel.valueOf(preferencias.getString(context.getString(R.string.key_nivel),Nivel.INICIAL.toString()));
        Log.e("nivel", nivel.toString());
        this.temporizado = preferencias.getBoolean(context.getString(R.string.key_temporizado),false);
    }
}
