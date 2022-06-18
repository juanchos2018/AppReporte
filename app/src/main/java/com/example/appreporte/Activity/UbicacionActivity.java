package com.example.appreporte.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import com.example.appreporte.Interface.InterfaceLocation;
import com.example.appreporte.Presenter.AddIncidenciaPresenter;
import com.example.appreporte.Presenter.LocationPresenter;
import com.example.appreporte.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.text.DecimalFormat;

public class UbicacionActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, InterfaceLocation {


 //   private GoogleMap mMap;

    double lat_current, lon_current;
    Location location;
    String location_desp;
    LocationManager lm;
    Button btnenviar;

    private static final String TAG = UbicacionActivity.class.getSimpleName();
    private GoogleMap map;
    private CameraPosition cameraPosition;
    private PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final LatLng defaultLocation = new LatLng(-18.0342353, -70.2411543);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private LocationPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        String MAPS_API_KEY="AIzaSyBOvRCYUhSBurum7waurXDF6bUZbruts7Q";
        Places.initialize(getApplicationContext(), MAPS_API_KEY);
        placesClient = Places.createClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnenviar=(Button) findViewById(R.id.btnenviar);
        btnenviar.setOnClickListener(this);

        presenter= new LocationPresenter(this);



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

   // @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

//        if (ContextCompat.checkSelfPermission(
//              this, Manifest.permission.ACCESS_FINE_LOCATION) ==
//            PackageManager.PERMISSION_GRANTED) {
//            lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
//            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            LatLng cliente = new LatLng(latitude, longitude);
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(cliente)
//                    .zoom(12)
//                    .bearing(30)
//                    .build();
//            mMap.addMarker(new MarkerOptions().position(cliente).title("Yo"));
//            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                    googleMap.clear();
                    map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    LatLng cliente = new LatLng(latLng.latitude, latLng.longitude);
                    lat_current=latLng.latitude;
                    lon_current=latLng.longitude;
                    presenter.InfoLocation(lat_current,lon_current);

                    map.addMarker(new MarkerOptions().anchor(0.1f,0.5f).position(cliente).title("Yo"));
                    //Toast.makeText(UbicacionActivity.this, "position lat "+ String.valueOf(latLng.latitude) +" - lon" + String.valueOf(latLng.longitude), Toast.LENGTH_SHORT).show();
                }
            });
//            Toast.makeText(this, "performAction", Toast.LENGTH_SHORT).show();
//        }else {
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
    }



    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case  R.id.btnenviar:
                    DecimalFormat form = new DecimalFormat("0.0000000");
                    String poslate= form.format(lat_current);
                    String poslon= form.format(lon_current);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("lat",poslate);
                    returnIntent.putExtra("lon",poslon);
                    returnIntent.putExtra("address",location_desp);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                    break;
            }
    }
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                lat_current=lastKnownLocation.getLatitude();
                                lon_current=lastKnownLocation.getLongitude();
                               // Toast.makeText(UbicacionActivity.this, "getLatitude :" + String.valueOf(lastKnownLocation.getLatitude()), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            lat_current=defaultLocation.latitude;
                            lon_current=defaultLocation.longitude;
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    @Override
    public void showLoading(boolean state) {

    }

    @Override
    public void onRequestSuccess(String object) {
        location_desp=object;
        Toast.makeText(this, object, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(Object object) {

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}