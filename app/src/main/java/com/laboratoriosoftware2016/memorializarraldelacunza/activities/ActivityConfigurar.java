package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;

/**
 * Pantalla para editar las preferencias
 */

public class ActivityConfigurar extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.vista_preferencias);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new FragmentoPreferencias()).commit();
    }
}
