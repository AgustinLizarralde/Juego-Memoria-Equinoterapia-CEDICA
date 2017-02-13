package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;

import java.util.HashSet;

/**
 * fragemento que carga las preferencias de un xml
 */

public class FragmentoPreferencias extends PreferenceFragment {

    @Override
    public void onResume() {
        Preference botonIntent = getPreferenceScreen().findPreference("boton_seleccion");
        botonIntent.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity()).getStringSet(getString(R.string.key_elementos),new HashSet<String>()).size()+"/"+ Elemento.values().length);
        super.onResume();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //boton seleccionar elementos
        PreferenceCategory categoryElementos = new PreferenceCategory(getActivity());
        categoryElementos.setTitle(getString(R.string.grupo_seleccion_imagenes));

        Preference botonIntent = getPreferenceScreen().findPreference("boton_seleccion");
        botonIntent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(preference.getContext(),ActivityListImage.class));
                return true;
            }
        });

        //simulacion de boton
        Preference botonPlacebo = getPreferenceScreen().findPreference("boton_guardar");
        botonPlacebo.setIcon(android.R.drawable.ic_menu_save);
        botonPlacebo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().finish();
                return true;
            }
        });

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