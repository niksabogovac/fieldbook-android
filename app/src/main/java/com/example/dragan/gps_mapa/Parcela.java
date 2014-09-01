package com.example.dragan.gps_mapa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class Parcela extends Activity {

    ArrayList<LatLng> mlista;
    private ListView lv;
    ArrayAdapter mAdapter;
    Spinner spiner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mlista =  (ArrayList<LatLng>)intent.getSerializableExtra("listaMarkera");
        setContentView(R.layout.activity_parcela);

       /* lv = (ListView)findViewById(R.id.fieldInfo_listview);

        mAdapter = new ArrayAdapter (this,android.R.layout.simple_list_item_1, mlista);
        lv.setAdapter(mAdapter); */

        Spinner spinner = (Spinner)findViewById(R.id.tip_zemljista);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tip_zemljista, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.field_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
