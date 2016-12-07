package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.os.Bundle;
import android.preference.PreferenceFragment;

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
    }
}
