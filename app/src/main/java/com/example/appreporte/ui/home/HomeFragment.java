package com.example.appreporte.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appreporte.Activity.IncidenciaActivity;
import com.example.appreporte.MainActivity;
import com.example.appreporte.R;
import com.example.appreporte.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment  implements OnMapReadyCallback, LocationListener {

    private FragmentHomeBinding binding;
    Button btnyuda;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert1;
    android.app.AlertDialog.Builder builder2;
    AlertDialog alert2;
    private GoogleMap mMap;
    double lat, lon;
    double lat_conductir, lon_conductor;
    Location location;
    LocationManager lm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map1);
//        mapFragment.getMapAsync((OnMapReadyCallback) getContext().getApplicationContext());
//


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
        lm = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mMap.addMarker(new MarkerOptions().position(cliente).title("Cliente"));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}