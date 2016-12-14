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
import android.widget.Toast;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;
import com.plattysoft.leonids.ParticleSystem;

import static java.security.AccessController.getContext;

/**
 * esta es la acivity donde se jugara
 */
public class ActivityJuego extends android.support.v7.app.AppCompatActivity {

    private Elemento correcto = Elemento.MONTURA;
    private Configuracion configuracion;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configuracion = new Configuracion(this);
        setContentView(R.layout.activity_juego_principal);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction FT = fragmentManager.beginTransaction();
        if( configuracion.getTurno() == 0 ) {
            FT.add(R.id.activity_juego_principal, new Fragment());
        }
        FT.commit();

        iniciarTurno(configuracion.getElementos().get(configuracion.getTurno()));
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
        configuracion.proximoTurno();
        if(configuracion.getTurno()<configuracion.getElementos().size()){
            iniciarTurno(configuracion.getElementos().get(configuracion.getTurno()));
        }
        else {
            configuracion.notJugando();



            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.popup_ganaste, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

            Button boton_volver_jugar = (Button)popupView.findViewById(R.id.boton_volver_jugar);
            boton_volver_jugar.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(),ActivityJuego.class));
                }});

            if ( !configuracion.isMaximoNivel() ) {
                Button boton_subir_dificultad = (Button) popupView.findViewById(R.id.boton_subir_dificultad);
                boton_subir_dificultad.setVisibility(View.VISIBLE);
                boton_subir_dificultad.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        configuracion.aumentarNivel();
                        startActivity(new Intent(v.getContext(), ActivityJuego.class));
                    }
                });
            }

            Button boton_menu = (Button)popupView.findViewById(R.id.boton_menu);
            boton_menu.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(),ActivityInicio.class));
                }});

            popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER,0,0);
            confeti(popupView);
        }
    }

    private void confeti(View v) {
        Point size = new Point();
        new ParticleSystem(this, 250, R.drawable.confeti2, 5000)
                .setSpeedRange(0.4f, 0.7f)
                .emit(v,250,5000);
    }

    private void iniciarTurno(Elemento e){
        configuracion.jugar();
        FragmentTransaction FT = fragmentManager.beginTransaction();
        Fragment fragment = new FragmentoEleccion(e, this.configuracion);
        FT.replace(R.id.activity_juego_principal, fragment);
        FT.commit();
    }
}
