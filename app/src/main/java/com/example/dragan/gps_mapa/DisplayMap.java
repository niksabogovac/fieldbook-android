package com.example.dragan.gps_mapa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;


public class DisplayMap extends Activity implements GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener {

    GoogleMap map;
    ArrayList<LatLng> lista;
    private int index;
    private ArrayList<Marker> markerList;
    PolygonOptions rectOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        lista = (ArrayList<LatLng>)intent.getSerializableExtra("mojalista");

        setContentView(R.layout.activity_display_map);


        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        markerList = new ArrayList<Marker>();
        for(int i = 0; i < lista.size(); i++) {
           // map.addMarker(new MarkerOptions().position(new LatLng(lista.get(i).latitude, lista.get(i).longitude)).draggable(true));
            markerList.add(map.addMarker(new MarkerOptions().position(lista.get(i)).draggable(true).title("marker").snippet(" broj " + i)) );
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(lista.get(0), 16));

        }

        rectOptions = new PolygonOptions();
            rectOptions.addAll(lista);

        // Get back the mutable Polygon
        map.addPolygon(rectOptions).setStrokeColor(Color.RED);
        map.setOnMarkerDragListener(this);
        map.setOnMapClickListener(this);
        map.setOnInfoWindowClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_map, menu);
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
    public void onMarkerDragStart(Marker marker) {
        for(int i = 0; i < markerList.size(); i++) {
            if(markerList.get(i).equals(marker)) {
                index = i;
            }
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng NewPosition = marker.getPosition();
        lista.set(index, NewPosition);
        map.clear();
        markerList.clear();


        for (int i = 0; i < lista.size(); i++) {
            //map.addMarker(new MarkerOptions().position(new LatLng(lista.get(i).latitude, lista.get(i).longitude)).draggable(true));
            markerList.add(map.addMarker(new MarkerOptions().position(lista.get(i)).draggable(true).title("marker").snippet(" broj " + i) ) );

        }
        rectOptions = new PolygonOptions();
        rectOptions.addAll(lista);
        map.addPolygon(rectOptions).setStrokeColor(Color.RED);

    }



    @Override
    public void onMapClick(LatLng point) {

        lista.add(point);
        map.clear();
        markerList.clear();

        for(int i = 0; i < lista.size(); i++) {
            markerList.add(map.addMarker(new MarkerOptions().position(lista.get(i)).draggable(true).title("marker").snippet(" broj " + i)));
        }

        rectOptions = new PolygonOptions();
        rectOptions.addAll(lista);
        map.addPolygon(rectOptions).setStrokeColor(Color.RED);

    }

    public void mapType(View view) {
        //da li je toggle ukljucen
        boolean on = ((ToggleButton) view).isChecked();
        if(on) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // Remove the marker
            //marker.remove();
            index = markerList.indexOf(marker);
        if(index == 0){ Toast.makeText(this, "Ne mozete obrisati prvi marker", Toast.LENGTH_SHORT).show();}
        else {
            marker.remove();
            lista.remove(lista.get(index));
            map.clear();
            markerList.clear();

            for (int i = 0; i < lista.size(); i++) {
                markerList.add(map.addMarker(new MarkerOptions().position(lista.get(i)).draggable(true).title("marker").snippet(" broj " + i)));

            }
            rectOptions = new PolygonOptions();
            rectOptions.addAll(lista);
            map.addPolygon(rectOptions).setStrokeColor(Color.RED);
        }
        }
    }

