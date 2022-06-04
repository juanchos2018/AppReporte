package com.example.appreporte.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appreporte.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class UbicacionActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener {


    private GoogleMap mMap;
    double lat, lon;
    double lat_conductir, lon_conductor;
    Location location;
    LocationManager lm;

    Button btnenviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnenviar=(Button) findViewById(R.id.btnenviar);
        btnenviar.setOnClickListener(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            Log.v("Localizacion", location.getLatitude() + " y " + location.getLongitude());
            lm.removeUpdates(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

           // return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

      //  double latitude = location.getLatitude();
       // double longitude = location.getLongitude();

        LatLng cliente = new LatLng(-18.0348346, -70.2478669);
       // LatLng aquitoy = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(cliente)
                .zoom(12)//esto es el zoom
                .bearing(30)//esto es la inclonacion
                .build();

        mMap.addMarker(new MarkerOptions().position(cliente).title("Yo"));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions();

                LatLng cliente = new LatLng(lat, lon);
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                lat_conductir=latLng.latitude;
                lon_conductor=latLng.longitude;
                mMap.addMarker(markerOptions);
                mMap.addMarker(new MarkerOptions().anchor(0.1f,0.5f).position(cliente).title("Yo"));

            }
        });
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){

                case  R.id.btnenviar:
                    DecimalFormat form = new DecimalFormat("0.0000000");
                    String poslate= form.format(lat_conductir);
                    String poslon= form.format(lon_conductor);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("lat",poslate);
                    returnIntent.putExtra("lon",poslon);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                 //   Toast.makeText(this, "lati:" +posotion, Toast.LENGTH_SHORT).show();
                    break;
            }
    }
}