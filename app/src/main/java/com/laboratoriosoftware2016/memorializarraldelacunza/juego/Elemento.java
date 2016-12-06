package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;

/**
 * Representa a los elementos del juego de memoria.
 * De estos elementos se puede extraer sus propiedades asociadas como nombre, imagen y sonido.
 */

public enum Elemento {
    CABALLO(R.string.caballo, R.drawable.caballo, R.raw.caballo, R.raw.caballo),
    MATRA(R.string.matra, R.drawable.matra, R.raw.caballo, R.raw.caballo),
    MONTURA(R.string.montura, R.drawable.montura, R.raw.caballo, R.raw.caballo),
    MONTURIN(R.string.monturin, R.drawable.monturin, R.raw.caballo, R.raw.caballo);
    /*
    Cinchón de Volteo
    Cabezada
    Bozal
    Riendas
    Casco
    Arriador
    Fusta
    Cuerda
    Cepillo
    Rasqueta
    Escarba Vasos
    Palos
    Tarima
    Pasto
    Zanahoria
    Pelota
    Aro
    Orejas
    Crines
    Cola
    Ojos
    Cascos
    */

    Elemento(int idString, int idImagen, int idSondioMasculino, int idSonidoFemenino) {
        this.idString = idString;
        this.idImagen = idImagen;
        this.idSondioMasculino = idSondioMasculino;
        this.idSonidoFemenino = idSonidoFemenino;
    }

    private int idString;
    private int idImagen;
    private int idSondioMasculino;
    private int idSonidoFemenino;

    public int getIdString() {
        return idString;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public int getIdSondioMasculino() {
        return idSondioMasculino;
    }

    public int getIdSonidoFemenino() {
        return idSonidoFemenino;
    }

}
