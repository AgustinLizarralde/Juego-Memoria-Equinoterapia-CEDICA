package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Configuracion de la aplicacion extraida de las SharedPreferences (tambien las guarda)
 */

public final class Configuracion {
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editorPreferencias;
    private Integer turno;
    private boolean jugando;
    private Context context;


    /**
     * Al crearse la configuracion extrae las preferencias almacenadas
     * @param context contexto del cual obtener las preferencias
     */
    private Configuracion(Context context) {
        this.context = context;
        this.preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        this.editorPreferencias = this.preferencias.edit();
        this.turno = 0;
        this.jugando = false;
    }

    public Nivel getNivel() {
        return Nivel.valueOf(preferencias.getString(context.getString(R.string.key_nivel),Nivel.INICIAL.toString()));
    }

    public void setNivel(Nivel nivel) {
        this.editorPreferencias.putString(context.getString(R.string.key_nivel), nivel.toString());
        this.editorPreferencias.commit();
    }

    public void aumentarNivel(){
        Nivel[] niveles = Nivel.values();
        int index = Math.min(getNivel().ordinal()+1,niveles.length-1);
        this.setNivel(niveles[index]);
    }

    public boolean isMaximoNivel() {
        return getNivel().ordinal() == Nivel.values().length-1;
    }

    public boolean isTemporizado() {
        return preferencias.getBoolean(context.getString(R.string.key_temporizado),false);
    }

    public void setTemporizado(boolean temporizado) {
        this.editorPreferencias.putBoolean(context.getString(R.string.key_temporizado), temporizado);
        this.editorPreferencias.commit();
    }

    public Integer getMaxSegundos(){
        Integer num = new Integer(preferencias.getInt(context.getString(R.string.key_tiempo_max),30));
        return num;
    }

    public void setMaxSegundos(Integer integer) {
        this.editorPreferencias.putInt(context.getString(R.string.key_tiempo_max), integer);
        this.editorPreferencias.commit();
    }

    public Sexo getSexoVoz() {
        return Sexo.valueOf(preferencias.getString(context.getString(R.string.key_sexo_voz),Sexo.MASCULINO.toString()));
    }

    public void setSexoVoz(Sexo sexo) {
        this.editorPreferencias.putString(context.getString(R.string.key_sexo_voz), sexo.toString());
        this.editorPreferencias.commit();
    }

    public List<Elemento> getElementos() {
        Set<String> def = new HashSet<>();
        for (Elemento e : Elemento.values()) {
            def.add(e.name());
        }
        Set<String> elementosString = preferencias.getStringSet(context.getString(R.string.key_elementos),def);
        List<Elemento> elementos= new ArrayList<>();
        for (String str : elementosString) {
            elementos.add(Elemento.valueOf(str));
        }
        return elementos;
    }

    public void setElementos(Set<String> elementos){
        editorPreferencias.putStringSet(context.getString(R.string.key_elementos),elementos);
        this.editorPreferencias.commit();
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public boolean isJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public void jugar() {
        this.jugando = true;
    }

    public Integer proximoTurno() {
        this.turno++;
        return turno;
    }

    public void notJugando() {
        this.jugando = false;
    }


    //omitir este pedazo de codigo
    private static Configuracion instancia;

    public static Configuracion getInstancia(){
        if( instancia != null){
            return instancia;
        }
        else {
            throw new NullPointerException();
        }
    }

    public static void createInstancia(Context context){
        instancia = new Configuracion(context.getApplicationContext());
    }


}
