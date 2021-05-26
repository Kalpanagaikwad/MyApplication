package com.myapplication.map;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat;
    double longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = new Intent();
         lat=getIntent().getDoubleExtra("lat",0f);
         longi=getIntent().getDoubleExtra("longi",0f);
        Log.e("datalat", String.valueOf(+lat));
        Log.e("datalon", String.valueOf(+longi));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng(lat, 12.12312);
        //"lat": 19.1231,
        //"long": 12.12312
        Log.e("datalat", String.valueOf(+lat));
        Log.e("datalon", String.valueOf(+longi));
        mMap.addMarker(new MarkerOptions().position(loc).title("Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(4), 1000, null);
    }
}