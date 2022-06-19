package com.example.whodo.ui.hire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.whodo.LoginActivity;
import com.example.whodo.R;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class HireFragment extends Fragment implements OnMapReadyCallback {
    private HireViewModel hireViewModel;
    private static final String TAG = "TAG-1";
    private MapView mapView;

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottomSheet;


@Override
    public View onCreateView( LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //hireViewModel = new ViewModelProvider(this).get(HireViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hire, container, false);


        // Gets the MapView from the XML layout and creates it
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        bottomSheet=root.findViewById(R.id.ll_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

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
        Button Btn = root.findViewById(R.id.button_ItemHire);

     Btn.setOnClickListener(v -> {
        //Intent intent = new Intent(v.getContext(),LoginActivity.class);
        // v.getContext().startActivity(intent);
        //Log.i("botton1", "Presionaste el boton de la cinta" )
     });

        return root;
    }





    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        googleMap.setOnMapClickListener(
                arg0 -> {
                    Log.i("onMapClick", "Horray!");

                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });


        addMarkers(googleMap);
        googleMap.setMyLocationEnabled(true);
    }

    public void addMarkers(GoogleMap googleMap){
        //float zoomLevel = 13;      // el nivel del zoom con el cual inicia el mapa
        //-31.4226, -64.1801
        //LatLng Marker = new LatLng(-31.4226,-64.1801);  // las coordenadas (latitud, longitud) que lo agregas en position
          // los marcadores (posición, título, ícono):
        //googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        //googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker()));
        //googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon()));

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(farma1, zoomLevel));  // para mostrar el mapa con zoom, en este caso nivel 13
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

