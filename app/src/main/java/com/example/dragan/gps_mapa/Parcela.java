package com.example.dragan.gps_mapa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Parcela extends Activity {

    ArrayList<LatLng> parcela_lista;
    ArrayAdapter<String> adapter;
    private ListView lv;
    ArrayAdapter mAdapter;
    Spinner spinner;
    EditText poljeOznaka, povrsinaZemljista;
    CheckBox box;
    String s, po, spn, pz, b;
    boolean cb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        parcela_lista =  (ArrayList<LatLng>)intent.getSerializableExtra("listaMarkera");
        setContentView(R.layout.activity_parcela);

       /* lv = (ListView)findViewById(R.id.fieldInfo_listview);
        mAdapter = new ArrayAdapter (this,android.R.layout.simple_list_item_1, mlista);
        lv.setAdapter(mAdapter); */

        poljeOznaka = (EditText)findViewById(R.id.polje_oznaka);
        spinner = (Spinner)findViewById(R.id.tip_zemljista);
        povrsinaZemljista = (EditText)findViewById(R.id.povrsinaZemljista);
        box = (CheckBox)findViewById(R.id.checkbox_omogucena_parcela);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box.isChecked()) {
                    cb = true;
                }
                else {
                    cb = false;
                }
            }
        });
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


    public void writeJSON() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("oznaka_parcele", po);
            jsonObject.put("tip_zemljista_parcele", spn);
            jsonObject.put("povrsina_parcele", pz);
            jsonObject.put("omogucena--parcela", cb);
            Log.d("ujson", jsonObject.toString());
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < parcela_lista.size(); i++) {
                JSONArray tmp = new JSONArray();
                tmp.put( parcela_lista.get(i).latitude);
                tmp.put( parcela_lista.get(i).longitude);
                jsonArray.put(tmp);
            }
            jsonObject.put("koordinate_parcele", jsonArray);
            Log.d("json", jsonObject.toString());
            String data = jsonObject.toString();
            try {
                FileOutputStream fOut = openFileOutput("podaci_parcela",MODE_PRIVATE);
                fOut.write(data.getBytes());
                fOut.close();
                Toast.makeText(getBaseContext(),"file saved",
                        Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void saveParcela(View view) {
        po = poljeOznaka.getText().toString();
        spn = spinner.getSelectedItem().toString();
        pz = povrsinaZemljista.getText().toString();
        Log.d("testis", "provera");
        writeJSON();

        Toast.makeText(getApplicationContext(),"cuvanje",Toast.LENGTH_SHORT).show();
    }
}
