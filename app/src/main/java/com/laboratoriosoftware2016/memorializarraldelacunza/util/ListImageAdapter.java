package com.laboratoriosoftware2016.memorializarraldelacunza.util;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Configuracion;
import com.laboratoriosoftware2016.memorializarraldelacunza.juego.Elemento;

public class ListImageAdapter extends BaseAdapter{

    private Elemento[] elementos = Elemento.values();
    private Set<String> elementosSeleccionados = new HashSet<>();
    private Activity context;

    public ListImageAdapter(Activity context) {
        super();
        this.context = context;
        
        elementosSeleccionados = PreferenceManager.getDefaultSharedPreferences(context).getStringSet(context.getString(R.string.key_elementos),new HashSet<String>());
    }

    private class ViewHolder {
        TextView textView;
        CheckBox checkBox;
        ImageView imageView;
        TextView textValue;
    }

    public int getCount() {
        return elementos.length;
    }

    public Elemento getItem(int position) {
        return elementos[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_image_item, null);
            holder = new ViewHolder();

            holder.textView = (TextView) convertView.findViewById(R.id.list_item_text);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.list_item_checkbox);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            holder.textValue = (TextView) convertView.findViewById(R.id.list_item_value);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.textView.setText(getItem(position).getString(this.context));
        holder.imageView.setImageResource(getItem(position).getIdImagen());
        holder.textValue.setText(getItem(position).toString());

        if (elementosSeleccionados.contains(holder.textValue.getText().toString())) {
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.imageView.setColorFilter(filter);
        }

        holder.checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkValue = holder.textValue.getText().toString();
                Elemento elemento = Elemento.valueOf(checkValue);

                if (holder.checkBox.isChecked()) {
                    elementosSeleccionados.add(elemento.toString());
                    holder.imageView.setColorFilter(null);
                }
                else {
                    elementosSeleccionados.remove(elemento.toString());
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(0);
                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    holder.imageView.setColorFilter(filter);
                }
                guardarCambios();
            }
        });

        return convertView;

    }


    private void guardarCambios(){
        Configuracion.getInstancia().setElementos(elementosSeleccionados);
    }
}