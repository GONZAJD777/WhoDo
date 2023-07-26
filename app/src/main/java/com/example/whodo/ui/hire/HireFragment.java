package com.example.whodo.ui.hire;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.whodo.MainActivity;
import com.example.whodo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;


public class HireFragment extends Fragment implements OnMapReadyCallback {
    boolean mLocationPermissionsGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 13f;
    private static final String TAG = "TAG-1";
    private MapView mapView;
    private GoogleMap mMap;

    private LinearLayout vServicesLinearLayout;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;


    @Override
    public View onCreateView( LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.act_main_frag_hire, container, false);
        Button Btn = root.findViewById(R.id.button_ItemHire);

        // Gets the MapView from the XML layout and creates it
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        getLocationPermission();

        LinearLayout bottomSheet = root.findViewById(R.id.ll_bottom_sheet);
        vServicesLinearLayout=root.findViewById(R.id.LYV);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        Btn.setOnClickListener(v -> {
            Log.i("botton1", "Presionaste el boton de la cinta" );
        });
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        bottomSheet.setMinimumHeight(500);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }

        });


        loadServices();

        return root;
    }


    private void loadServices(){
        ArrayList<String> vServices = MainActivity.getServices();
        vServicesLinearLayout.removeAllViews();
        Log.i("CheckBoxPicked", "vServices.size: " + MainActivity.getServices().size());
        for(int i = 0; i< vServices.size(); i++) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(vServices.get(i));
            Log.i("CheckBoxPicked", "Servicio elegido: " + vServices.get(i));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i("CheckBoxPicked", "Servicio elegido: " + compoundButton.toString() );
                }
            });
            vServicesLinearLayout.addView(checkBox);
    }}

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setOnMapClickListener(

                arg0 -> {
                    Log.i("onMapClick", "Horray!");

                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });
        if (mLocationPermissionsGranted) {

            if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setMyLocationEnabled(true);
            getDeviceLocation();
            addMarkers(googleMap);
            //init();

        }


    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.requireContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.requireContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                 mLocationPermissionsGranted = true;
                //initMap();
            }else{
                ActivityCompat.requestPermissions(this.requireActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this.requireActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        initMap();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        mapView.getMapAsync(this);

    }

    public void addMarkers(GoogleMap googleMap){
        //float zoomLevel = 13;      // el nivel del zoom con el cual inicia el mapa
        //googleMap.setMinZoomPreference(zoomLevel);
        //-31.4226, -64.1801
        LatLng Marker = new LatLng(-31.4226,-64.1801);  // las coordenadas (latitud, longitud) que lo agregas en position
          // los marcadores (posición, título, ícono):
        googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        //googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker()));

        LatLng Marker1 = new LatLng(-31.4226,-64.3501);  // las coordenadas (latitud, longitud) que lo agregas en position
        // los marcadores (posición, título, ícono):
        googleMap.addMarker(new MarkerOptions().position(Marker1).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker()));

        LatLng Marker2 = new LatLng(-32.4226,-64.1801);  // las coordenadas (latitud, longitud) que lo agregas en position
        // los marcadores (posición, título, ícono):
        googleMap.addMarker(new MarkerOptions().position(Marker2).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        //googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker()));

       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker, zoomLevel));  // para mostrar el mapa con zoom, en este caso nivel 13
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity());

        try{
            if(mLocationPermissionsGranted){

                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: found location!");

                        Location currentLocation = (Location) task.getResult();
                        if (currentLocation != null) {
                            LatLng mLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            moveCamera(mLatLng);
                        }

                    }else{
                        Log.d(TAG, "onComplete: current location is null");

                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, HireFragment.DEFAULT_ZOOM));

       /* if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
       }*/


    }
        
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}

