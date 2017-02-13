package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.content.Intent;
import android.graphics.Point;
import android.opengl.Visibility;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.laboratoriosoftware2016.memorializarraldelacunza.util.Juego;
import com.plattysoft.leonids.ParticleSystem;

import static java.security.AccessController.getContext;

/**
 * esta es la acivity donde se jugara
 */
public class ActivityJuego extends android.support.v7.app.AppCompatActivity {

    private Elemento correcto = Elemento.MONTURA;
    private Configuracion configuracion;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private Juego juego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configuracion = new Configuracion(this);
        setContentView(R.layout.activity_juego_principal);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView txt = (TextView) findViewById(R.id.toolbar_text);
        txt.setText(getString(R.string.nivel)+": " + configuracion.getNivel().toString().toLowerCase());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction FT = fragmentManager.beginTransaction();
        if( configuracion.getTurno() == 0 ) {
            FT.add(R.id.activity_juego_principal, new Fragment());
        }
        FT.commit();

        if( configuracion.getElementos().isEmpty()){
            Toast.makeText(this,R.string.mensaje_sin_elementos_seleccionados,Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,ActivityInicio.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            this.finish();
            this.startActivity(i);
        }
        else {
            this.juego = new Juego(configuracion, this);
            juego.iniciarTurno();
            //iniciarTurno(configuracion.getElementos().get(configuracion.getTurno()));
        }
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void proximaEleccion(){
        this.juego.proximaEleccion();
    }

    public FragmentManager getFragmento(){
        return this.fragmentManager;
    }

}
