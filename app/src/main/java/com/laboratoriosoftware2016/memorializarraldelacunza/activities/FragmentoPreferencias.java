package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * fragemento que carga las preferencias de un xml
 */

public class FragmentoPreferencias extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        List<String> keys_elementos = new ArrayList<>();
        List<String> values_elementos = new ArrayList<>();

        int i=0;
        for (Elemento e : Elemento.values()) {
            keys_elementos.add(getString(e.getIdString()));
            values_elementos.add(e.name());
            i++;
        }

        Collections.sort(keys_elementos);
        Collections.sort(values_elementos);

        Set<String> selectionSet = new HashSet<String>(values_elementos);

        MultiSelectListPreference multiSelectPref = new MultiSelectListPreference(getActivity());
        multiSelectPref.setKey(getString(R.string.key_elementos));
        multiSelectPref.setTitle(getString(R.string.titulo_elementos));
        multiSelectPref.setEntries(keys_elementos.toArray(new String[0]));
        multiSelectPref.setEntryValues(values_elementos.toArray(new String[0]));
        multiSelectPref.setDefaultValue(selectionSet);
        getPreferenceScreen().addPreference(multiSelectPref);

        //simulacion de boton
        PreferenceCategory category = new PreferenceCategory(getActivity());
        category.setTitle("");

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

        getPreferenceScreen().addPreference(category);
        getPreferenceScreen().addPreference(botonPlacebo);
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
