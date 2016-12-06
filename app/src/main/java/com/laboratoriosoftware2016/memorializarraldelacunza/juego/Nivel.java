package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;

/**
 * Informacion sobre el nivel de dificultad del juego
 */

public enum Nivel {
    INICIAL(R.string.dificultad_inicial, 1),
    MEDIO(R.string.dificultad_media, 2),
    AVANZADO(R.string.dificultad_avanzada, 3),
    EXPERTO(R.string.dificultad_experto, 4);

    private Integer idNombre;
    private Integer maxImagenes;

    Nivel(int idNombre, Integer maxImagenes){
        this.idNombre = idNombre;
        this.maxImagenes=maxImagenes;
    }

    public Integer getIdNombre(){
        return this.idNombre;
    }

    public Integer getMaxImagenes(){
        return this.maxImagenes;
    }
}
