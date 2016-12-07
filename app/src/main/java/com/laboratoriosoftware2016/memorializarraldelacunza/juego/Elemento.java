package com.laboratoriosoftware2016.memorializarraldelacunza.juego;

import android.content.Context;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;

/**
 * Representa a los elementos del juego de memoria.
 * De estos elementos se puede extraer sus propiedades asociadas como nombre, imagen y sonido.
 */

public enum Elemento {
    CABALLO(R.string.caballo, R.drawable.caballo, R.raw.caballo_masc, R.raw.caballo_fem),
    MATRA(R.string.matra, R.drawable.matra, R.raw.matra_masc, R.raw.matra_fem),
    MONTURA(R.string.montura, R.drawable.montura, R.raw.montura_masc, R.raw.montura_fem),
    MONTURIN(R.string.monturin, R.drawable.monturin, R.raw.monturin_masc, R.raw.monturin_fem),
    CINCHON_DE_VOLTEO(R.string.cinchon_de_volteo, R.drawable.cinchon_de_volteo, R.raw.cinchon_de_volteo_masc, R.raw.cinchon_de_volteo_fem),
    CABEZADA(R.string.cabezada, R.drawable.cabezada, R.raw.cabezada_masc, R.raw.cabezada_fem),
    BOZAL(R.string.bozal, R.drawable.bozal, R.raw.bozal_masc, R.raw.bozal_fem),
    RIENDAS(R.string.riendas, R.drawable.riendas, R.raw.riendas_masc, R.raw.riendas_fem),
    CASCO(R.string.casco, R.drawable.casco, R.raw.casco_masc, R.raw.casco_fem),
    ARRIADOR(R.string.arriador, R.drawable.arriador, R.raw.arriador_masc, R.raw.arriador_fem),
    FUSTA(R.string.fusta, R.drawable.fusta, R.raw.fusta_masc, R.raw.fusta_fem),
    CUERDA(R.string.cuerda, R.drawable.cuerda, R.raw.cuerda_masc, R.raw.cuerda_fem),
    CEPILLO(R.string.cepillo, R.drawable.cepillo, R.raw.cepillo_masc, R.raw.cepillo_fem),
    RASQUETA(R.string.rasqueta, R.drawable.rasqueta, R.raw.rasqueta_masc, R.raw.rasqueta_fem),
    ESCARBA_VASOS(R.string.escarba_vasos, R.drawable.escarba_vasos, R.raw.escarba_vasos_masc, R.raw.escarba_vasos_fem),
    PALOS(R.string.palos, R.drawable.palos, R.raw.palos_masc, R.raw.palos_fem),
    PASTO(R.string.pasto, R.drawable.pasto, R.raw.pasto_masc, R.raw.pasto_fem),
    ZANAHORIA(R.string.zanahoria, R.drawable.zanahoria, R.raw.zanahoria_masc, R.raw.zanahoria_fem),
    PELOTA(R.string.pelota, R.drawable.pelota, R.raw.pelota_masc, R.raw.pelota_fem),
    ARO(R.string.aros, R.drawable.aros, R.raw.aros_masc, R.raw.aros_fem),
    OREJAS(R.string.orejas, R.drawable.orejas, R.raw.orejas_masc, R.raw.orejas_fem),
    CRINES(R.string.crines, R.drawable.crines, R.raw.crines_masc, R.raw.crines_fem),
    COLA(R.string.cola, R.drawable.cola, R.raw.cola_masc, R.raw.cola_fem),
    OJOS(R.string.ojos, R.drawable.ojos, R.raw.ojos_masc, R.raw.ojos_fem),
    CASCOS(R.string.cascos, R.drawable.cascos, R.raw.cascos_masc, R.raw.cascos_fem);


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

    public String getString(Context ctx) {
        return ctx.getString(idString);
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

    public int getIdSonido(Sexo sexo) {
        switch (sexo) {
            case MASCULINO:
                return getIdSondioMasculino();
            case FEMENINO:
                return getIdSonidoFemenino();
            default:
                return getIdSondioMasculino();
        }
    }
}


