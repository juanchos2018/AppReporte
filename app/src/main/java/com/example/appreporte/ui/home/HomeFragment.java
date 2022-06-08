package com.example.appreporte.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appreporte.Activity.IncidenciaActivity;
import com.example.appreporte.MainActivity;
import com.example.appreporte.R;
import com.example.appreporte.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class HomeFragment extends Fragment  implements OnMapReadyCallback{

    private FragmentHomeBinding binding;
    Button btnyuda;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert1;
    android.app.AlertDialog.Builder builder2;
    AlertDialog alert2;

    double lat_current, lon_current;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        supportMapFragment.getMapAsync(this);

        btnyuda =binding.btnayuda;
        btnyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoAyuda();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private  void  dialogoAyuda(){
        builder1 = new AlertDialog.Builder(getContext());
        Button btncerrrar;
        TextView tvestado;
        builder1.setTitle("detalle");
        ImageView imgseguridad;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_panico, null);
        btncerrrar=(Button)v.findViewById(R.id.btncerrardialogo1);
        imgseguridad=(ImageView)v.findViewById(R.id.imgseguridad);
        btncerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert1.dismiss();
            }
        });
        imgseguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String organization_id="3";
                dialogoSeguridad(organization_id);
                alert1.dismiss();
            }
        });
        builder1.setView(v);
        alert1  = builder1.create();
        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert1.getWindow().setGravity(Gravity.BOTTOM);
        alert1.show();
    }
    private  void  dialogoSeguridad(String organization_id){
        builder2 = new AlertDialog.Builder(getContext());
        Button btncerrrar,btnvandalismo,btnrobo,btnacoso,btnextorsion;
        builder2.setTitle("detalle");
        ImageView imgseguridad;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_incidencia, null);
        btncerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo3);

        btnvandalismo=(Button)v.findViewById(R.id.btnvandalismo);
        btnrobo=(Button)v.findViewById(R.id.btnrobo);
        btnacoso=(Button)v.findViewById(R.id.btnacoso);
        btnextorsion=(Button)v.findViewById(R.id.btnextorsion);

        btncerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert2.dismiss();
            }
        });

        btnvandalismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), IncidenciaActivity.class);
                bundle.putString("id","1");
                bundle.putString("organization_id",organization_id);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        btnrobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), IncidenciaActivity.class);
                bundle.putString("id","2");
                bundle.putString("organization_id",organization_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnacoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), IncidenciaActivity.class);
                bundle.putString("id","3");
                bundle.putString("organization_id",organization_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnextorsion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), IncidenciaActivity.class);
                bundle.putString("id","4");
                bundle.putString("organization_id",organization_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        builder2.setView(v);
        alert2  = builder2.create();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert2.show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("USER",getContext().MODE_PRIVATE);
                                String lat_share =sharedPreferences2.getString("lat", "");
                                String lon_share =sharedPreferences2.getString("lon", "");
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                Toast.makeText(getContext(), "getLatitude :" + String.valueOf(lastKnownLocation.getLatitude()), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("message", "Current location is null. Using defaults.");
                            Log.e("message", "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
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
    public void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }
}