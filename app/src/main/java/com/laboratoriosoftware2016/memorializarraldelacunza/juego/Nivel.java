package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

/**
 * Created by Agustin on 04/12/2016.
 */

public enum Nivel {
    INICIAL("Inicial", 1),
    MEDIO("Medio", 2),
    AVANZADO("Avanzado", 3),
    EXPERTO("Experto", 4);

    private String nombre;
    private Integer maxImagenes;

    Nivel(String nombre, Integer maxImagenes){
        this.nombre=nombre;
        this.maxImagenes=maxImagenes;
    }

    public String getNombre(){
        return this.nombre;
    }

    public Integer getMaxImagenes(){
        return this.maxImagenes;
    }
}
