package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;


import com.laboratoriosoftware2016.memorializarraldelacunza.R;

/**
 * fragemento que carga las preferencias de un xml
 */

public class FragmentoPreferencias extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //boton seleccionar elementos
        PreferenceCategory categoryElementos = new PreferenceCategory(getActivity());
        categoryElementos.setTitle(getString(R.string.grupo_seleccion_imagenes));

        Preference botonIntent = new Preference(getActivity());
        botonIntent.setKey("asdqweq2");
        botonIntent.setTitle(getString(R.string.titulo_elementos));
        botonIntent.setIcon(android.R.drawable.ic_input_add);
        botonIntent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(preference.getContext(),ActivityListImage.class));
                return true;
            }
        });
        categoryElementos.addPreference(botonIntent);
        getPreferenceScreen().addPreference(categoryElementos);


        //simulacion de boton
        PreferenceCategory categoryVacia = new PreferenceCategory(getActivity());
        categoryVacia.setTitle("");


        Preference botonPlacebo = new Preference(getActivity());
        botonPlacebo.setKey("asdqweq");
        botonPlacebo.setTitle(getString(R.string.boton_guardar));
        botonPlacebo.setIcon(android.R.drawable.ic_menu_save);
        botonPlacebo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(preference.getContext(),ActivityInicio.class));
                return true;
            }
        });
        categoryVacia.addPreference(botonPlacebo);
        getPreferenceScreen().addPreference(categoryVacia);

        //dependency simulada
        Preference temporizado = getPreferenceScreen().findPreference(getString(R.string.key_temporizado));
        boolean temporizado_bool = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(getString(R.string.key_temporizado),false);
        Preference tiempo_max = getPreferenceScreen().findPreference(getString(R.string.key_tiempo_max));
        tiempo_max.setEnabled(temporizado_bool);

        temporizado.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if( newValue instanceof Boolean){
                    Boolean bool = (Boolean) newValue;
                    Preference tiempo_max = getPreferenceScreen().findPreference(getString(R.string.key_tiempo_max));
                    tiempo_max.setEnabled(bool);
                }
                return true;
            }
        });

    }
}