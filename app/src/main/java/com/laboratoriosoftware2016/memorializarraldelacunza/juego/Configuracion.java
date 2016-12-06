package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Agustin on 05/12/2016.
 */

public class Configuracion {
    private final String perfil="configuracion";
    private Nivel nivel;
    private final String key_nivel="nivel";
    private boolean temporizado;
    private final String key_temporizado="temporizado";
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editorPreferencias;
    //private Set<Object> memoria


    public Configuracion(Context context) {
        this.preferencias = context.getSharedPreferences(perfil, MODE_PRIVATE);
        this.editorPreferencias = this.preferencias.edit();
        recargarPreferencias();
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
        this.editorPreferencias.putString(key_nivel, this.nivel.toString());
        this.editorPreferencias.commit();
    }

    public boolean isTemporizado() {
        return temporizado;
    }

    public void setTemporizado(boolean temporizado) {
        this.temporizado = temporizado;
        this.editorPreferencias.putBoolean(key_temporizado, this.temporizado);
        this.editorPreferencias.commit();
    }

    public void recargarPreferencias(){
        this.nivel = Nivel.valueOf(preferencias.getString(key_nivel,Nivel.INICIAL.toString()));
        Log.e("nivel", nivel.toString());
        this.temporizado = preferencias.getBoolean(key_temporizado,false);
    }
}
