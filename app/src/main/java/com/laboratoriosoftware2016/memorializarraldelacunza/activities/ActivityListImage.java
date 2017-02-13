package com.laboratoriosoftware2016.memorializarraldelacunza.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;

import com.laboratoriosoftware2016.memorializarraldelacunza.R;
import com.laboratoriosoftware2016.memorializarraldelacunza.util.ListImageAdapter;

import java.util.ArrayList;

public class ActivityListImage extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView itemsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_images);
        itemsView = (GridView) findViewById(R.id.listView1);



        ListImageAdapter Adapter = new ListImageAdapter(this);
        itemsView.setAdapter(Adapter);
        itemsView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onItemClick(AdapterView arg0, View v, int position, long arg3) {
        CheckBox cb = (CheckBox) v.findViewById(R.id.list_item_checkbox);
        cb.performClick();
        //TextView tv = (TextView) v.findViewById(R.id.list_item_text);
        //ImageView tv = (ImageView) v.findViewById(R.id.list_item_image);
    }

}

