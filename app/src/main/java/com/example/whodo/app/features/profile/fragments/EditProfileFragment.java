package com.example.whodo.app.features.profile.fragments;

import static com.example.whodo.app.features.profile.ProfileHolderActivity.hideKeyboard;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.features.profile.ProfileItem;
import com.example.whodo.app.resources.parameters.Parameter;
import com.example.whodo.app.uiClasses.CustomMapView;
import com.example.whodo.R;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditProfileFragment extends Fragment implements OnMapReadyCallback {
    boolean mLocationPermissionsGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 13f;
    private static final String TAG = "EDIT-PROFILE-FRAGMENT";
    private CustomMapView mapView;
    private GoogleMap mMap;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ImageView imagePicker;
    private LatLng mLatLng;
    private static String LoggedUserImage;
    //private static String LoggedUserLanguages;
    private List<String> LoggedUserLanguages = new ArrayList<>();
    private static String LoggedUserDescription;
    private static String LoggedUserAddress;
    private static Double LoggedUserLocationLat;
    private static Double LoggedUserLocationLon;
    private ProfileItem item_Description;
    private ProfileItem item_Location;
    private ProfileItem item_Languages;
    private EditText DescriptionSimpleEditText;
    private EditText LocationSimpleEditText;
    private LinearLayout LanguagesLinearLayout;
    private LinearLayout BlackBackground_bottom_sheet;
    private BottomSheetBehavior<LinearLayout> DescriptionBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> LocationBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> LanguagesBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> BlackBackgroundBottomSheetBehavior;
    private FloatingActionButton SaveChangesButton;
    private TextView ReadyLabelButtonDescription;
    private TextView ReadyLabelButtonLocation;
    private TextView ReadyLabelButtonLanguages;
    private FloatingActionButton PickImageButton;
    private LinearLayout ItemsLinearLayout;
    private LinearLayout Description_bottom_sheet;
    private LinearLayout Location_bottom_sheet;
    private LinearLayout Languages_bottom_sheet;
    private MainActivityViewModel mMainActivityViewModel;
    private User mLoggedUser;


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.act_profile_frag_edit_profile, container, false);

        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        getLocationPermission();
        //*********************************************************************************
        //BUTTONS
        ReadyLabelButtonDescription = root.findViewById(R.id.ReadyLabelButtonDescription);
        ReadyLabelButtonLocation = root.findViewById(R.id.ReadyLabelButtonLocation);
        ReadyLabelButtonLanguages = root.findViewById(R.id.ReadyLabelButtonLanguages);
        PickImageButton = root.findViewById(R.id.PickImageButton);
        SaveChangesButton = root.findViewById(R.id.SaveChangesButton);
        //BUTTONS.LISTENERS
        ReadyLabelButtonDescription.setOnClickListener(this::onClick);
        ReadyLabelButtonLocation.setOnClickListener(this::onClick);
        ReadyLabelButtonLanguages.setOnClickListener(this::onClick);
        SaveChangesButton.setOnClickListener(this::onClick);
        PickImageButton.setOnClickListener(this::onClick);
        //*********************************************************************************
        //EDITTEXT
        LocationSimpleEditText = root.findViewById(R.id.LocationSimpleEditText);
        DescriptionSimpleEditText = root.findViewById(R.id.DescriptionSimpleEditText);
        //listener para evitar que el contenedor scrollee cuando se necesita scrollear el EditText
        DescriptionSimpleEditText.setOnTouchListener((view, motionEvent) -> {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        //*********************************************************************************
        //                              ==LINEARLAYOUTS==
        ItemsLinearLayout = root.findViewById(R.id.linearLayout);
        //*********************************************************************************
        //BACKGROUD BOTTOMSHEET
        BlackBackground_bottom_sheet = root.findViewById(R.id.BlackBackground_bottom_sheet);
        BlackBackgroundBottomSheetBehavior = BottomSheetBehavior.from(BlackBackground_bottom_sheet);
        BlackBackgroundBottomSheetBehavior.setState(STATE_EXPANDED);
        BlackBackgroundBottomSheetBehavior.setDraggable(false);
        //*********************************************************************************
        //DESCRIPTION BOTTOMSHEET
        Description_bottom_sheet = root.findViewById(R.id.Description_bottom_sheet);
        DescriptionBottomSheetBehavior = BottomSheetBehavior.from(Description_bottom_sheet);
        DescriptionBottomSheetBehavior.setDraggable(false);
        //*********************************************************************************
        //LOCATION BOTTOMSHEET
        Location_bottom_sheet = root.findViewById(R.id.Location_bottom_sheet);
        LocationBottomSheetBehavior = BottomSheetBehavior.from(Location_bottom_sheet);
        //*********************************************************************************
        //LANGUAGES BOTTOMSHEET
        LanguagesLinearLayout= root.findViewById(R.id.LanguagesLinearLayout);
        Languages_bottom_sheet = root.findViewById(R.id.Languages_bottom_sheet);
        LanguagesBottomSheetBehavior = BottomSheetBehavior.from(Languages_bottom_sheet);
        //*********************************************************************************
        //                              ==PROFILE ITEMS==
        //DESCRIPTION
        TextView label_Description = new TextView(getContext());
        label_Description.setText(getString(R.string.PersonalInfoFrag_Description));
        label_Description.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Description = new ProfileItem(getContext());
        item_Description.setText(getString(R.string.PersonalInfoFrag_Description1));
        item_Description.setImage(R.drawable.id_insignia_24);
        item_Description.setOnClickListener(v -> {
            setBottomSheetBehavior(DescriptionBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDescriptionText(DescriptionSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Description1),item_Description);
                    setBottomSheetBehavior(DescriptionBottomSheetBehavior,1);
                }
            });
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Description), Toast.LENGTH_LONG).show();
        });
        DescriptionBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                    case STATE_HIDDEN:
                        //Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        //Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        setDescriptionText(DescriptionSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Description1),item_Description);
                        setBottomSheetBehavior(DescriptionBottomSheetBehavior,1);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        //*********************************************************************************
        //LOCATION
        TextView label_Location = new TextView(getContext());
        label_Location.setText(getString(R.string.PersonalInfoFrag_Location));
        label_Location.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Location = new ProfileItem(getContext());
        item_Location.setText(getString(R.string.PersonalInfoFrag_Location1));
        item_Location.setImage(R.drawable.marcador_24);
        item_Location.setOnClickListener(v -> {
            setBottomSheetBehavior(LocationBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLocationText(LocationSimpleEditText.getText().toString(),mLatLng.latitude,mLatLng.longitude,getString(R.string.PersonalInfoFrag_Location1),item_Location);
                    setBottomSheetBehavior(LocationBottomSheetBehavior,1);
                }
            });

            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Location), Toast.LENGTH_LONG).show();
        });
        LocationBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                    case STATE_HIDDEN:
                        //Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        //Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        setLocationText(LocationSimpleEditText.getText().toString(),mLatLng.latitude,mLatLng.longitude,getString(R.string.PersonalInfoFrag_Location1),item_Location);
                        setBottomSheetBehavior(LocationBottomSheetBehavior,1);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        //*********************************************************************************
        //LANGUAGES
        TextView label_Languages = new TextView(getContext());
        label_Languages.setText(getString(R.string.PersonalInfoFrag_Languages));
        label_Languages.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Languages = new ProfileItem(getContext());
        item_Languages.setText(getString(R.string.PersonalInfoFrag_Languages));
        item_Languages.setImage(R.drawable.subtitulos_24);
        item_Languages.setOnClickListener(v -> {
            setBottomSheetBehavior(LanguagesBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLanguagesText(LoggedUserLanguages,getString(R.string.PersonalInfoFrag_Languages1),item_Languages);
                    setBottomSheetBehavior(LanguagesBottomSheetBehavior,1);}
            });

            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Location), Toast.LENGTH_LONG).show();
        });
        LanguagesBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                    case STATE_HIDDEN:
                        //Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        //Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        setLanguagesText(LoggedUserLanguages,getString(R.string.PersonalInfoFrag_Languages1),item_Languages);
                        setBottomSheetBehavior(LanguagesBottomSheetBehavior,1);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        //----------------------------------------------------------
        ItemsLinearLayout.addView(label_Description);
        ItemsLinearLayout.addView(item_Description);
        ItemsLinearLayout.addView(label_Location);
        ItemsLinearLayout.addView(item_Location);
        ItemsLinearLayout.addView(label_Languages);
        ItemsLinearLayout.addView(item_Languages);
        //*********************************************************************************
        //                              ==PROFILE IMAGE PICKER==
        imagePicker = root.findViewById(R.id.imagePicker);
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                requireActivity().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Log.d("PhotoPicker", "Selected URI: " + uri);
                Picasso.get().load(uri).into(imagePicker);
                LoggedUserImage= String.valueOf(uri);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });

        mMainActivityViewModel.getLoggedUser().observe(requireActivity(),this::loadUserData);

        return root;
    }
    private void loadUserData (User pUser) {
        mLoggedUser=pUser.deepCopy();

        if (mLoggedUser.getName()!=null) {
            Picasso.get().load(mLoggedUser.getProfilePicture()).into(imagePicker);
            LoggedUserImage = mLoggedUser.getProfilePicture();
            setDescriptionText(mLoggedUser.getDescription(), getString(R.string.PersonalInfoFrag_Description1), item_Description);
            DescriptionSimpleEditText.setText(mLoggedUser.getDescription());
            setLocationText(mLoggedUser.getAddress(), mLoggedUser.getLocation().getLatitude(), mLoggedUser.getLocation().getLongitude(), getString(R.string.PersonalInfoFrag_Location1), item_Location);
            LocationSimpleEditText.setText(mLoggedUser.getAddress());
            //el pin se coloca en el metodo OnMapReady
            LoggedUserLanguages = mLoggedUser.getLanguages();

            Log.i(TAG, "LoggedUserLanguages -->" + LoggedUserLanguages );
            setLanguagesText(LoggedUserLanguages, getString(R.string.PersonalInfoFrag_Languages1), item_Languages);

            mMainActivityViewModel.getParameters().observe(getViewLifecycleOwner(),this::loadLanguages);

        }
    }
    private void saveUserData (){
        mMainActivityViewModel.getLoggedUser().removeObservers(requireActivity());
        mMainActivityViewModel.getParameters().removeObservers(requireActivity());

        mLoggedUser.setDescription(LoggedUserDescription);
        mLoggedUser.setAddress(LoggedUserAddress);
        mLoggedUser.getLocation().setLatitude(LoggedUserLocationLat);
        mLoggedUser.getLocation().setLongitude(LoggedUserLocationLon);

        mLoggedUser.setLanguages(LoggedUserLanguages);
        Log.i(TAG, "mLoggedUser.getLanguages() -->" + mLoggedUser.getLanguages().toString() );
        Log.i(TAG, "LoggedUserLanguages -->" + LoggedUserLanguages );

        mLoggedUser.setProfilePicture(LoggedUserImage);
        //String LoggedUserGeoHash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(LoggedUserLocationLat,LoggedUserLocationLon));
        //mLoggedUser.setGeohash(LoggedUserGeoHash);
        mMainActivityViewModel.updateLoggedUser(mLoggedUser);//luego de cargar todos los cambios en mLoggedUser modificamos el usuario en el ViewModel
        //Log.i(TAG, "POST SALVAR CAMBIOS " + Objects.requireNonNull(model.getLoggedUser().getValue()).getLanguages() );
        //model.setFragmentVisibility(View.VISIBLE);
        mMainActivityViewModel.setSelectedFragment(4,View.VISIBLE);
    }
    private void loadLanguages(List<Parameter> pParameters) {
        List<String> pLanguages = Utils.extractListFromParameters(pParameters, "LANGUAGES");

        for (String language : pLanguages) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(language);

            // Verifica si el idioma está en la lista y marca el CheckBox
            if (LoggedUserLanguages.contains(language)) {
                Log.i(TAG, "EL SIGUIENTE IDIOMA ESTÁ EN LA LISTA: " + LoggedUserLanguages);
                checkBox.setChecked(true);
            }

            checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                String selectedLanguage = compoundButton.getText().toString();
                if (isChecked) {
                    if (!LoggedUserLanguages.contains(selectedLanguage)) {
                        LoggedUserLanguages.add(selectedLanguage);
                    }
                } else {
                    LoggedUserLanguages.remove(selectedLanguage);
                }
                Log.i(TAG, "CheckBoxPicked --> Idiomas en LoggedUserLanguages: " + LoggedUserLanguages);
            });

            LanguagesLinearLayout.addView(checkBox);
        }
    }

    private void setDescriptionText(String text1, String text2, ProfileItem profileItem) {
        // Si text1 no es nulo y su contenido no es vacío, se lo usa.
        if (text1 != null && !text1.trim().isEmpty()) {
            profileItem.setText(text1);
            LoggedUserDescription = text1;
        } else {
            // Si text1 es nulo o vacío, se utiliza text2, siempre chequeando que text2 no sea null.
            profileItem.setText(text2 != null ? text2 : "");
            LoggedUserDescription = null;
        }
    }
    private void setLocationText(String text1, Double pLatitude,Double pLongitude, String text3, ProfileItem profileItem) {
        boolean hasValidText = text1 != null && !text1.trim().isEmpty();
        boolean hasValidLocation = pLatitude != null && pLongitude != null;

        if (hasValidText && hasValidLocation) {
            String locationText = String.format("%s\n Lat: %.6f\n Lon: %.6f", text1, pLatitude, pLongitude);
            profileItem.setText(locationText);

            LoggedUserAddress = text1;
            LoggedUserLocationLat = pLatitude;
            LoggedUserLocationLon = pLongitude;
        } else {
            profileItem.setText(text3 != null ? text3 : "");
            LoggedUserAddress = null;
            LoggedUserLocationLat = null;
            LoggedUserLocationLon = null;
        }
    }
    private void setLanguagesText(List<String> list1,String text2,ProfileItem ProfileItem1){
        if ( list1.isEmpty()) {
            ProfileItem1.setText(text2);
        }
        else
        {
            String regex = ",$";
            ProfileItem1.setText(list1.toString().replaceAll("^\\[|\\]$", ""));
        }

    }
    private void setBottomSheetBehavior (BottomSheetBehavior<LinearLayout> mBottomSheetBehavior, Integer mState){
        if (mState==0){
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_EXPANDED);
            BlackBackground_bottom_sheet.setClickable(true);
            BlackBackground_bottom_sheet.setAlpha(0.25F);

        } else {
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_HIDDEN);
            BlackBackground_bottom_sheet.setClickable(false);
            BlackBackground_bottom_sheet.setAlpha(0);
            hideKeyboard(requireActivity());
        }
    }
    public void addMarkers(GoogleMap googleMap,LatLng LatLng){
        // las coordenadas (latitud, longitud) que lo agregas en position
        // los marcadores (posición, título, ícono):
        mLatLng=LatLng;
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(LatLng).title("Tu Ubicacion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        //googleMap.addMarker(new MarkerOptions().position(Marker).title("PLOMERO/ Ricado Fleitas").icon(BitmapDescriptorFactory.defaultMarker()));

    }
    // Select Image method
    private void SelectImage() {
        ActivityResultContracts.PickVisualMedia.ImageOnly VisualMediaType = ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE;
        pickMedia.launch(new PickVisualMediaRequest.Builder().setMediaType(VisualMediaType).build());
    }
    //MAP METHODS
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (mLoggedUser.getLocation().getLatitude()!=null && mLoggedUser.getLocation().getLongitude()!=null){
            addMarkers(mMap,new LatLng(mLoggedUser.getLocation().getLatitude(),mLoggedUser.getLocation().getLongitude()));
        }
        googleMap.setOnMapClickListener(latLng -> addMarkers(mMap,latLng));
        if (mLocationPermissionsGranted) {
            if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setMyLocationEnabled(true);
            getDeviceLocation();
        }
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.requireContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.requireContext(),COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionsGranted = true;
                //initMap();
            } else {
                ActivityCompat.requestPermissions(this.requireActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this.requireActivity(), permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
        initMap();
    }
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        mapView.getMapAsync(this);

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
                        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        moveCamera(currentLatLng);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, EditProfileFragment.DEFAULT_ZOOM));
    }
    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.ReadyLabelButtonDescription:
                setDescriptionText(DescriptionSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Description1),item_Description);
                setBottomSheetBehavior(DescriptionBottomSheetBehavior,1);
                break;
            case R.id.ReadyLabelButtonLocation:
                setLocationText(LocationSimpleEditText.getText().toString(),mLatLng.latitude,mLatLng.longitude,getString(R.string.PersonalInfoFrag_Location1),item_Location);
                setBottomSheetBehavior(LocationBottomSheetBehavior,1);
                break;
            case R.id.ReadyLabelButtonLanguages:
                setLanguagesText(LoggedUserLanguages,getString(R.string.PersonalInfoFrag_Languages1),item_Languages);
                setBottomSheetBehavior(LanguagesBottomSheetBehavior,1);
                break;
            case R.id.SaveChangesButton:
                saveUserData();
                break;
            case R.id.PickImageButton:
                SelectImage();
                break;
        }
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

