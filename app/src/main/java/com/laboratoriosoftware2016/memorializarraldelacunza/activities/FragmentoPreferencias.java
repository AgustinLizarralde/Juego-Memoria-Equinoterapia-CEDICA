package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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
    }
}
