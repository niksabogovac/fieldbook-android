package com.example.dragan.gps_mapa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class MyActivity extends Activity implements LocationListener, View.OnClickListener {


    private TextView latitudeField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;

    TextView mainTextView;
    Button mainButton;
    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();

    ArrayList<LatLng> lista;
    private double lng;
    private double lat;


    //poziva se kad se napravi prva aktivnost
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mainTextView = (TextView)findViewById(R.id.main_textview);
        // mainTextView.setText("GPS");
        mainButton = (Button) findViewById(R.id.main_button1);
        mainButton.setOnClickListener(this);
        mainListView = (ListView)findViewById(R.id.main_listview);

        mArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, mNameList);

        mainListView.setAdapter(mArrayAdapter);

        latitudeField = (TextView)findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        Location location = locationManager.getLastKnownLocation(provider);

        if(location != null) {
            System.out.println("Provider " + provider + " has been enabled");
            onLocationChanged(location);
        }
        else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 200, 1, this);
        }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = (double)(location.getLatitude());
        lng = (double) (location.getLongitude());
        latitudeField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled new provider " + provider, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    @Override
    public void onClick(View v) {
        mNameList.add(latitudeField.getText().toString());
        mNameList.add(longitudeField.getText().toString());
        mNameList.add("*-*-*-*-*-*-*");
        mArrayAdapter.notifyDataSetChanged();
        LatLng(lat, lng);
    }

    public void showMap(View view) {
        Intent intent = new Intent(this,DisplayMap.class);

        if(lista==null){ Toast.makeText(this, "Niste označili ni jednu koordinatu",Toast.LENGTH_SHORT).show(); }
        else {
            intent.putExtra("mojalista", lista);
            startActivity(intent);
        }

    }

    public void LatLng(double lat, double lng) {
        if (lista == null)
            lista = new ArrayList<LatLng>();

        lista.add(new LatLng(lat, lng));

    }
}
