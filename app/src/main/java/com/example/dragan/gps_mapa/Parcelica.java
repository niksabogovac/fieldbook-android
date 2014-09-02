package com.example.dragan.gps_mapa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;


public class Parcelica extends Activity {

    ArrayList<LatLng> parcelica_lista;
    EditText oznakaParcelica, povrsinaParcelice;
    CheckBox boxParcelica;
    boolean cbp ;
    String op, pp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        parcelica_lista =  (ArrayList<LatLng>)intent.getSerializableExtra("listaMarkera");
        setContentView(R.layout.activity_parcelica);

        oznakaParcelica = (EditText)findViewById(R.id.oznaka_parcelica);
        povrsinaParcelice = (EditText)findViewById(R.id.povrsina_parcelica);
        boxParcelica = (CheckBox)findViewById(R.id.checkbox_omogucena_parcelica);
        boxParcelica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boxParcelica.isChecked()) {
                    cbp = true;
                } else {
                    cbp = false;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parcelica, menu);
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

    public void saveParcelica(View view) {
        op = oznakaParcelica.getText().toString();
        pp = povrsinaParcelice.getText().toString();

        writeJSONP();
        Toast.makeText(getApplicationContext(),"cuvanje",Toast.LENGTH_SHORT).show();
    }

    public void writeJSONP() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("oznaka_parcelice", op);
            jsonObject.put("povrsina_parcelice", pp);
            jsonObject.put("omogucena--parcelica", cbp);
            Log.d("ujson", jsonObject.toString());
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < parcelica_lista.size(); i++) {
                JSONArray tmp = new JSONArray();
                tmp.put( parcelica_lista.get(i).latitude);
                tmp.put( parcelica_lista.get(i).longitude);
                jsonArray.put(tmp);
            }
            jsonObject.put("koordinate_parcelice", jsonArray);
            Log.d("json", jsonObject.toString());

            String data = jsonObject.toString();
            try {
                FileOutputStream fOut = openFileOutput("podaci_parcelica",MODE_PRIVATE);
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
}
