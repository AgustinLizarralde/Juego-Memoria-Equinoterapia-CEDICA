package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Nivel;

/**
 * esta es la acivity donde se jugara
 */
public class ActivityJuego extends android.support.v7.app.AppCompatActivity {

    private Elemento correcto = Elemento.MONTURA;
    private Configuracion configuracion;
    //TODO esto es de prueba
    private Integer turno=0;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configuracion = new Configuracion(this);
        setContentView(R.layout.activity_juego_principal);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction FT = fragmentManager.beginTransaction();
        if( turno == 0 ) {
            FT.add(R.id.activity_juego_principal, new Fragment());
        }
        FT.commit();

        iniciarTurno(configuracion.getElementos().get(turno));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(this,ActivityConfigurar.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void proximaEleccion(){
        turno++;
        if(turno<configuracion.getElementos().size()){
            iniciarTurno(configuracion.getElementos().get(turno));
        }
        else {
            Toast.makeText(this, "juego terminado", Toast.LENGTH_LONG).show();
        }
    }

    private void iniciarTurno(Elemento e){
        FragmentTransaction FT = fragmentManager.beginTransaction();
        Fragment fragment = new FragmentoEleccion(e, this.configuracion);
        FT.replace(R.id.activity_juego_principal, fragment);
        FT.commit();
    }
}
