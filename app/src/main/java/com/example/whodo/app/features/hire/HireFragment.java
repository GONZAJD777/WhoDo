package com.example.whodo.app.features.hire;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.resources.images.ImageManager;
import com.example.whodo.app.resources.images.ImagesViewModel;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HireFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MAIN-HIRE-FRAGMENT";
    boolean mLocationPermissionsGranted = false;
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private MapView mapView;
    private SeekBar DistanceFilterSeekBar;
    private LatLng DeviceLocation;
    private TextView MaxDistanceFilterLabel;

    private LinearLayout ReelItemsLinearLayout;
    private LinearLayout vServicesLinearLayout ;
    private FloatingActionButton FiltersButton;
    private FloatingActionButton ReelButton;
    private FloatingActionButton HireProviderButton;
    private FloatingActionButton HideProviderButton;
    private LinearLayout ReelLinearLayout;
    private LinearLayout FilterLinearLayout;
    private LinearLayout ProviderDetailLinearLayout;
    private BottomSheetBehavior<LinearLayout> FiltersBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> ReelBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> ProviderDetailBottomSheetBehavior;
    private ConstraintLayout ButtonsConsLayout;
    private ConstraintSet mConstraintSet1 ; // Create a ConstraintSet.
    private ConstraintSet mConstraintSet2 ; // Create a ConstraintSet.
    private TextView AvgTime_TextView;
    private TextView AvgTariff_TextView;
    private TextView ProviderName_TextView;
    private ImageView ProfilePic_ImageView;
    private TextView ProviderDescription_TextView;
    private TextView ProviderLanguage_TextView;
    private TextView ProviderAddress_TextView;
    private TextView ProviderPhoneNumber_TextView;
    private TextView ProviderEmail_TextView;
    private boolean isCons2Active;

    private RatingBar SpeedScore;
    private RatingBar QualityScore;
    private RatingBar AppereanceScore;
    private RatingBar CleanlinessScore;
    private RatingBar OverallScore;
    private final String RegSeed = ",$";
    private User PickedUser;

    private HireFragmentViewModel mHireFragmentViewModel;
    private ImagesViewModel mImagesViewModel;
    private MainActivityViewModel mMainActivityViewModel;
    private User mLoggedUser;

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.act_main_frag_hire, container, false);

        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mHireFragmentViewModel = new ViewModelProvider(requireActivity()).get(HireFragmentViewModel.class);
        mImagesViewModel = new ViewModelProvider(requireActivity()).get(ImagesViewModel.class);


        AvgTime_TextView = root.findViewById(R.id.AvgTime_TextView);
        AvgTariff_TextView = root.findViewById(R.id.AvgTariff_TextView);
        ProviderName_TextView = root.findViewById(R.id.ProviderName_TextView);
        ProfilePic_ImageView = root.findViewById(R.id.ProfilePic_ImageView);
        ProviderDescription_TextView = root.findViewById(R.id.ProviderDescription_TextView);
        ProviderLanguage_TextView = root.findViewById(R.id.ProviderLanguage_TextView);
        ProviderAddress_TextView = root.findViewById(R.id.ProviderAddress_TextView);
        ProviderPhoneNumber_TextView = root.findViewById(R.id.ProviderPhoneNumber_TextView);
        ProviderEmail_TextView = root.findViewById(R.id.ProviderEmail_TextView);

        OverallScore = root.findViewById(R.id.OverallScore_RatingBar);
        SpeedScore = root.findViewById(R.id.SpeedScore_RatingBar);
        QualityScore = root.findViewById(R.id.QualityScore_RatingBar);
        AppereanceScore = root.findViewById(R.id.AppereanceScore_RatingBar);
        CleanlinessScore = root.findViewById(R.id.CleanlinessScore_RatingBar);

        //Button Btn = root.findViewById(R.id.button_ItemHire);
        HireProviderButton=root.findViewById(R.id.Hire_Button);
        HideProviderButton=root.findViewById(R.id.Hide_Button);
        FiltersButton=root.findViewById(R.id.FiltersButton);
        ReelButton=root.findViewById(R.id.ReelButton);
        FiltersButton.setOnClickListener(this::onClick);
        ReelButton.setOnClickListener(this::onClick);
        HideProviderButton.setOnClickListener(this::onClick);
        HireProviderButton.setOnClickListener(this::onClick);

        ProviderDetailLinearLayout=root.findViewById(R.id.ProviderDetail_bottom_sheet);
        FilterLinearLayout = root.findViewById(R.id.Filter_bottom_sheet);
        vServicesLinearLayout=root.findViewById(R.id.LYV);
        ReelItemsLinearLayout = root.findViewById(R.id.ReelItemsLinearLayout);
        FiltersBottomSheetBehavior = BottomSheetBehavior.from(FilterLinearLayout);
        FiltersBottomSheetBehavior.setSkipCollapsed(true);
        ReelLinearLayout = root.findViewById(R.id.Reel_bottom_sheet);
        ReelBottomSheetBehavior = BottomSheetBehavior.from(ReelLinearLayout);
        ProviderDetailBottomSheetBehavior=BottomSheetBehavior.from(ProviderDetailLinearLayout);

        ButtonsConsLayout = root.findViewById(R.id.ButtonsConsLayout);
        mConstraintSet1 = new ConstraintSet(); // create a Constraint Set
        mConstraintSet2 = new ConstraintSet();
        mConstraintSet2.clone(ButtonsConsLayout);
        mConstraintSet1.clone(ButtonsConsLayout);
        isCons2Active=false;

        //mConstraintSet2.connect(FiltersButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START,144); // boton izquierdo con margen izquierdo - 144 distancia
        mConstraintSet2.connect(FiltersButton.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP,24);
        mConstraintSet2.connect(ReelButton.getId(), ConstraintSet.START, FiltersButton.getId(), ConstraintSet.END,24);
        //mConstraintSet2.connect(ReelButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END,144);
        mConstraintSet2.connect(ReelButton.getId(), ConstraintSet.TOP, FiltersButton.getId(), ConstraintSet.TOP,0);
        mConstraintSet2.setHorizontalChainStyle(FiltersButton.getId(),ConstraintSet.WRAP_CONTENT);
        mConstraintSet2.setHorizontalChainStyle(ReelButton.getId(), ConstraintSet.WRAP_CONTENT);

        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        getLocationPermission();
        MaxDistanceFilterLabel = root.findViewById(R.id.MaxDistanceFilterLabel);
        //MaxDistanceFilterLabel.setText("10km");

        FiltersBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i(TAG, "FiltersBottomSheetBehavior --> STATE_EXPANDED");
                        bottomSheet.setMinimumHeight(500);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        ReelBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        DistanceFilterSeekBar = root.findViewById(R.id.seekBar);
        DistanceFilterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHireFragmentViewModel.setDistanceFilter(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mMainActivityViewModel.getLoggedUser().observe(requireActivity(), this::loadUser);
        mMainActivityViewModel.getProviders().observe(getViewLifecycleOwner(),this::loadProviders);
        mMainActivityViewModel.getServices().observe(getViewLifecycleOwner(),this::loadServices);
        mHireFragmentViewModel.getDistanceFilter().observe(getViewLifecycleOwner(),this::loadDistanceFilter);
        mHireFragmentViewModel.getPickedProvider().observe(getViewLifecycleOwner(),this::showProviderDetail);

        return root;
    }
    private void loadUser(User pLoggedUser){
        mLoggedUser=pLoggedUser;
        mHireFragmentViewModel.setUser(pLoggedUser);
    }
    private void loadProviders(List<User> pProviders){
        mHireFragmentViewModel.setProviders(pProviders);
    }
    private void loadServices(List<String> pServices){
        mHireFragmentViewModel.setServices(pServices);
        loadServicesCheckBox(pServices);
    }
    private void loadDistanceFilter(Double pDistanceFilter){
        DistanceFilterSeekBar.setProgress(mHireFragmentViewModel.getDistanceFilterPoint());
        String serviceDistanceFilterLabel = (pDistanceFilter/0.009009009009009) + "Km";
        MaxDistanceFilterLabel.setText(serviceDistanceFilterLabel);
    }
    private void openFragment(){
        mMainActivityViewModel.setSelectedFragment(15,View.GONE);
        // model.TabLayoutVisibility(View.GONE);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap pGoogleMap) {
        pGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        //Once mas is ready, call get the providers to populate the map
        if (!mHireFragmentViewModel.getProvidersLiveData().isInitialized()) {
            mHireFragmentViewModel.getProvidersLiveData().observe(getViewLifecycleOwner(),pProviders -> {
                this.setProviderMarkers(pGoogleMap,pProviders);
                mImagesViewModel.getStoredServIconNames().observe(getViewLifecycleOwner(),pStoredSerIconNames -> { this.setProviderMarkers(pGoogleMap,pProviders); });
            });
        }else {
            this.setProviderMarkers(pGoogleMap,mHireFragmentViewModel.getProvidersLiveData().getValue());
            mImagesViewModel.getStoredServIconNames().observe(getViewLifecycleOwner(),pStoredSerIconNames -> {
                this.setProviderMarkers(pGoogleMap,mHireFragmentViewModel.getProvidersLiveData().getValue());
            });
            mHireFragmentViewModel.getProvidersLiveData().observe(getViewLifecycleOwner(),pProviders -> {
                this.setProviderMarkers(pGoogleMap,pProviders);
            });
        }

        pGoogleMap.setOnInfoWindowClickListener(marker -> {
            PickedUser= (User) marker.getTag();
            mHireFragmentViewModel.setPickedProvider(PickedUser);
            ProviderDetailBottomSheetBehavior.setDraggable(false);
            ProviderDetailBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        pGoogleMap.setOnMapClickListener(arg0 -> {
            Log.i("onMapClick", "Horray!");
            if (FiltersBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                FiltersBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        if (mLocationPermissionsGranted) {
            if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(this.requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            pGoogleMap.setMyLocationEnabled(true);
            setMapConfig(pGoogleMap);
            getDeviceLocation(pGoogleMap);
            setMarkerSnippet(pGoogleMap);
        }
    }
    @SuppressLint("NonConstantResourceId")
    private void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.ReelButton:
                ReelBehavior();
                break;
            case R.id.FiltersButton:
                if (FiltersBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    FiltersBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (FiltersBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED || FiltersBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_HALF_EXPANDED ){
                    FiltersBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.Hide_Button:
                ProviderDetailBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.Hire_Button:
                mMainActivityViewModel.setPickedWorkOrder(null);
                ProviderDetailBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                openFragment ();
                break;
        }
    }
    //***************************************************************************//
    private void loadServicesCheckBox(@NonNull List<String> pServices){
        clearLinearLayout(vServicesLinearLayout);
        List<String> ServicePickerFilter = new ArrayList<>();
        if(mHireFragmentViewModel.getServiceFilter().getValue()!=null)
            ServicePickerFilter=mHireFragmentViewModel.getServiceFilter().getValue();
        for(int i = 0; i< pServices.size(); i++) {
            CheckBox ServiceCheckBox = new CheckBox(getContext());
            ServiceCheckBox.setText(pServices.get(i));
            for(int j = 0; j< ServicePickerFilter.size(); j++){
                if ( ServicePickerFilter.get(j).toUpperCase().contains(pServices.get(i).toUpperCase()))
                {
                    Log.i(TAG, "loadServicesCheckBox --> Checkeando filtrado, el servicio esta tildado " + ServicePickerFilter.get(j) );
                    ServiceCheckBox.setChecked(true);
                }}
            addCheckBoxListener(ServiceCheckBox);
            vServicesLinearLayout.addView(ServiceCheckBox);
        }
    }
    private void addCheckBoxListener(CheckBox pCheckBox) {
        pCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                List<String> ServicePickerFilter= new ArrayList<>() ;
                if(mHireFragmentViewModel.getServiceFilter().getValue()!=null)
                    ServicePickerFilter=mHireFragmentViewModel.getServiceFilter().getValue();
                if (b){
                    ServicePickerFilter.add(compoundButton.getText().toString());
                    Log.i(TAG, "loadServicesCheckBox --> Checkeando filtrado, el servicio fue tildado " + compoundButton.getText().toString() );
                }else {
                    for(int j = 0; j< ServicePickerFilter.size(); j++){
                        if ( ServicePickerFilter.get(j).equals(compoundButton.getText().toString()))
                        {
                            Log.i(TAG, "loadServicesCheckBox --> Checkeando filtrado, el servicio fue destildado " + ServicePickerFilter.get(j) );
                            ServicePickerFilter.remove(j);
                        }
                    }
                }
                Log.i(TAG, "loadServicesCheckBox --> Servicios tildados " + ServicePickerFilter );
                mHireFragmentViewModel.setServiceFilter(ServicePickerFilter);
            }
        });
    }
    //***************************************************************************//
    private void setProviderMarkers(GoogleMap pGoogleMap,List<User> pProviders){
        clearLinearLayout(ReelItemsLinearLayout);
        if (pGoogleMap != null){
            pGoogleMap.clear();
            if (pProviders != null)
            {
                for(int i = 0; i< pProviders.size(); i++) {
                    addMarkers(pGoogleMap, pProviders.get(i));
                    addHireItem(pProviders.get(i));//Agrego el item al reel
                }
            }
            ReelBehavior();
            ReelBehavior();
        }
    }
    private void addMarkers(@NonNull GoogleMap pGoogleMap, @NonNull User pProvider ){
        String AvgTariff = this.getResources().getString(R.string.HireFragment_MapMarkerSnipets_AvgTariff);
        String AvgCompletionTime = this.getResources().getString(R.string.HireFragment_MapMarkerSnipets_AvgCompletionTime);
        String OverallScore = this.getResources().getString( R.string.HireFragment_MapMarkerSnipets_OverallScore);
        LatLng UserLatLon = new LatLng(pProvider.getLatitude(), pProvider.getLongitude());
        String SnippetText = pProvider.getSpecialization().replaceAll(RegSeed, "|") +
                AvgTariff + " " + pProvider.getUserScore().getAvgTariff() + "\n" +
                AvgCompletionTime + " " + pProvider.getUserScore().getAvgCompletionTime() + "\n" +
                OverallScore + " " + pProvider.getUserScore().getOverallScore();

        if (Objects.equals(pProvider.getUid(), mLoggedUser.getUid()) && Objects.equals(mLoggedUser.getType(), "1")) {
            String mMapIconName = "my_location_customer";
            Bitmap mMapIcon = ImageManager.getStoredIcon(requireContext(),mMapIconName,80,80);
            Objects.requireNonNull(pGoogleMap.addMarker(new MarkerOptions().position(UserLatLon)
                                .title("Tu Ubicacion Registrada")
                                .snippet(SnippetText)
                                .icon(BitmapDescriptorFactory.fromBitmap(mMapIcon))))
                                .setTag(pProvider);
        } else if (Objects.equals(pProvider.getUid(), mLoggedUser.getUid()) && Objects.equals(mLoggedUser.getType(), "2")) {
            String mMapIconName = "my_location_provider";
            Bitmap mMapIcon = ImageManager.getStoredIcon(requireContext(),mMapIconName,80,80);
            Objects.requireNonNull(pGoogleMap.addMarker(new MarkerOptions().position(UserLatLon)
                                .title("Tu Ubicacion Registrada")
                                .snippet(SnippetText)
                                .icon(BitmapDescriptorFactory.fromBitmap(mMapIcon))))
                                .setTag(pProvider);
        } else {
            String mServIconName;
            String[] mSplit = pProvider.getSpecialization().split(",");
            if (mSplit.length>1) {
                mServIconName= "varios_24";
            }
            else {
                mServIconName = pProvider.getSpecialization().replaceAll(RegSeed, "") + "_64";
            }
            Bitmap mMapIcon = ImageManager.getStoredIcon(requireContext(),mServIconName,80,80);
            Objects.requireNonNull(pGoogleMap.addMarker(new MarkerOptions().position(UserLatLon)
                                .title(pProvider.getName())
                                .snippet(SnippetText)
                                .icon(BitmapDescriptorFactory.fromBitmap(mMapIcon))))
                                .setTag(pProvider);
        }
    }
    public void addHireItem(@NonNull User pProvider){
        HireItem mHireItem = new HireItem(this.requireContext());
        mHireItem.setProvider(pProvider);
        mHireItem.setName(pProvider.getName());
        mHireItem.PricePercent(pProvider.getUserScore().getAvgTariff()+"");
        mHireItem.setReviews(pProvider.getUserScore().getOverallScore()+"");
        mHireItem.setSpeed(pProvider.getUserScore().getAvgCompletionTime()+"");

        String SpecText=pProvider.getSpecialization().replaceAll(RegSeed, "");
        ArrayList<String> SpecArrayList = new ArrayList<>(Arrays.asList(SpecText.split(",")));
        for(int i = 0; i< SpecArrayList.size(); i++){

            ImageView Spec = new ImageView(this.requireContext());
            String mServIconName = SpecArrayList.get(i) + "_borderless_16";
            Bitmap mServIconImage = ImageManager.getStoredIcon(requireContext(),mServIconName,80,80);
            Spec.setImageBitmap(mServIconImage);
            LinearLayout.LayoutParams ImageViewLP=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
            ImageViewLP.gravity=Gravity.CENTER;
            Spec.setLayoutParams(ImageViewLP);
            Log.d(TAG, "addHireItem --> Se agrego el icono al item: "+SpecArrayList.get(i));

            mHireItem.addSpecItem(Spec);
        }
        mHireItem.setImage(pProvider.getProfilePicture());
        mHireItem.setOnClickListener(view -> {
            PickedUser=mHireItem.getProvider();
            mHireFragmentViewModel.setPickedProvider(PickedUser);
            ProviderDetailBottomSheetBehavior.setDraggable(false);
            ProviderDetailBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //Log.d(TAG, "click"+view);
        });
        ReelItemsLinearLayout.addView(mHireItem);
    }
    @SuppressLint("NonConstantResourceId")
    public void setMarkerSnippet(GoogleMap pGoogleMap){
        pGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            public View getInfoWindow(@NonNull Marker arg0) {
                return null;
            }
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                LinearLayout info = new LinearLayout(requireContext());
                LinearLayout SpecList = new LinearLayout(requireContext());
                info.setOrientation(LinearLayout.VERTICAL);
                SpecList.setOrientation(LinearLayout.HORIZONTAL);
                SpecList.setGravity(Gravity.CENTER);

                TextView title = new TextView(requireContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippetSpec = new TextView(requireContext());
                snippetSpec.setTextColor(Color.BLUE);
                snippetSpec.setTypeface(null, Typeface.BOLD);
                snippetSpec.setGravity(Gravity.CENTER);
                snippetSpec.setText(marker.getSnippet());
                //Log.d("MAPMARKER SNIPPET", "SNIPPET TEXT " +marker.getSnippet());
                int indexStart1 = 0;
                int indexEnd1 = Objects.requireNonNull(marker.getSnippet()).indexOf("|");
                //Log.d("MAPMARKER SNIPPET", "INDEXSTART " +indexStart1);
                //Log.d("MAPMARKER SNIPPET", "INDEXEND " +indexEnd1);
                snippetSpec.setText(Objects.requireNonNull(marker.getSnippet()).substring(indexStart1,indexEnd1));

                TextView snippetInfo = new TextView(requireContext());
                snippetInfo.setTextColor(Color.GRAY);
                int indexStart = marker.getSnippet().indexOf("|")+1;
                int indexEnd = marker.getSnippet().length();
                //Log.d("MAPMARKER SNIPPET", "INDEXSTART " +indexStart);
                //Log.d("MAPMARKER SNIPPET", "INDEXEND " +indexEnd);
                snippetInfo.setText(Objects.requireNonNull(marker.getSnippet()).substring(indexStart,indexEnd));

                ArrayList<String> SpecArrayList = new ArrayList<>(Arrays.asList(snippetSpec.getText().toString().split(",")));
                for(int i = 0; i< SpecArrayList.size(); i++){
                    ImageView Spec = new ImageView(requireContext());
                    String mMapIconName = SpecArrayList.get(i) + "_24";
                    Bitmap mMapIcon = ImageManager.getStoredIcon(requireContext(),mMapIconName,80,80);
                    Spec.setImageBitmap(mMapIcon);
                    LinearLayout.LayoutParams ImageViewLP=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
                    ImageViewLP.gravity=Gravity.CENTER;
                    Spec.setLayoutParams(ImageViewLP);
                    SpecList.addView(Spec);
                }

                info.addView(title);
                info.addView(SpecList);
                //info.addView(snippetSpec);
                info.addView(snippetInfo);

                return info;
            }
        });
    }
    private void showProviderDetail (@NonNull User PickedUser){

        try {
            AvgTime_TextView.setText(PickedUser.getUserScore().getAvgCompletionTime());
            AvgTariff_TextView.setText(PickedUser.getUserScore().getAvgTariff());
            ProviderName_TextView.setText(PickedUser.getName());
            Picasso.get().load(PickedUser.getProfilePicture()).into(ProfilePic_ImageView);
            ProviderDescription_TextView.setText(PickedUser.getDescription());
            ProviderLanguage_TextView.setText(PickedUser.getLanguages().replaceAll(RegSeed,""));
            ProviderAddress_TextView.setText(PickedUser.getAddress());
            String FullPhoneNumber =PickedUser.getPhone_ccn().substring(PickedUser.getPhone_ccn().indexOf("("),PickedUser.getPhone_ccn().indexOf(")")+1)+" "+PickedUser.getPhone();
            ProviderPhoneNumber_TextView.setText(FullPhoneNumber);
            ProviderEmail_TextView.setText(PickedUser.getEmail());

            OverallScore.setRating(Float.parseFloat((PickedUser.getUserScore().getOverallScore()).substring(0, 3)));
            //OverallScore.setRightTextView(PickedUser.getUserScore().getOverallScore());
            SpeedScore.setRating(Float.parseFloat(PickedUser.getUserScore().getSpeedScore().substring(0, 3)));
            QualityScore.setRating(Float.parseFloat(PickedUser.getUserScore().getQualityScore().substring(0, 3)));
            AppereanceScore.setRating(Float.parseFloat(PickedUser.getUserScore().getAppereanceScore().substring(0, 3)));
            CleanlinessScore.setRating(Float.parseFloat(PickedUser.getUserScore().getCleanlinessScore().substring(0, 3)));

        } catch (Exception e)
        {
            Log.d(TAG, "onMapReady --> No hay informacion del proveedor " + e);
            OverallScore.setRating(0.0F);
            //OverallScore.setRightTextView(PickedUser.getUserScore().getOverallScore());
            SpeedScore.setRating(0.0F);
            //SpeedScore.setRightTextView(PickedUser.getUserScore().getSpeedScore());
            QualityScore.setRating(0.0F);
            //QualityScore.setRightTextView(PickedUser.getUserScore().getQualityScore());
            AppereanceScore.setRating(0.0F);
            //AppereanceScore.setLeftTextView(PickedUser.getUserScore().getAppereanceScore());
            CleanlinessScore.setRating(0.0F);
            //CleanlinessScore.setLeftTextView(PickedUser.getUserScore().getCleanlinessScore());
        }
    }
    //***************************************************************************//

    private void getLocationPermission(){
        Log.d("getLocationPermission()", "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

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
    private void getDeviceLocation(GoogleMap pGoogleMap){
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity());
        try{
            if(mLocationPermissionsGranted){
                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Location currentLocation = (Location) task.getResult();
                        if (currentLocation != null) {
                            DeviceLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            moveCamera(pGoogleMap,DeviceLocation);
                        }
                    }else{
                        Log.d("getDeviceLocation", "onComplete: current location is null");
                    }
                });
            }
        }catch (SecurityException e){
            Log.e("getDeviceLocation", "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }
    private void initMap(){
        mapView.getMapAsync(this);
    }
    private void moveCamera(GoogleMap pGoogleMap,LatLng latLng){
        pGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) 11.75));
    }
    private void clearLinearLayout(LinearLayout LL){
        try {
            if (LL.getChildCount() > 0) {
                LL.removeAllViewsInLayout();
            }
        }
        catch (Exception e)
        {
            Log.i("ReelItemsLinearLayout", "Has no childs:" + e);
        }
    }
    private void setMapConfig(GoogleMap pGoogleMap){
        View googleLogo = mapView.findViewWithTag("GoogleWatermark");
        RelativeLayout.LayoutParams glLayoutParams = (RelativeLayout.LayoutParams)googleLogo.getLayoutParams();
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, 0);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END,0 );
        googleLogo.setLayoutParams(glLayoutParams);

        View MapButtons = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("4"));
        RelativeLayout.LayoutParams glLayoutParamsMapbuttons = (RelativeLayout.LayoutParams)MapButtons.getLayoutParams();
        glLayoutParamsMapbuttons.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        glLayoutParamsMapbuttons.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        glLayoutParamsMapbuttons.addRule(RelativeLayout.ALIGN_PARENT_START, 0);
        glLayoutParamsMapbuttons.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        glLayoutParamsMapbuttons.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.TRUE );
        MapButtons.setLayoutParams(glLayoutParamsMapbuttons);

        View LocButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams glLayoutParamsLocButton = (RelativeLayout.LayoutParams)LocButton.getLayoutParams();
        glLayoutParamsLocButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        glLayoutParamsLocButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        glLayoutParamsLocButton.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        glLayoutParamsLocButton.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        glLayoutParamsLocButton.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
        LocButton.setLayoutParams(glLayoutParamsLocButton);

        pGoogleMap.setPadding(50,30,0,0);
        pGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

    }
    private void ReelBehavior(){
        if (!isCons2Active){
            ReelBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            ReelBottomSheetBehavior.setDraggable(false);
            ReelBottomSheetBehavior.setHideable(false);
            mConstraintSet2.applyTo(ButtonsConsLayout);
            isCons2Active=true;
        }else {
            ReelBottomSheetBehavior.setDraggable(true);
            ReelBottomSheetBehavior.setHideable(true);
            ReelBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            mConstraintSet1.applyTo(ButtonsConsLayout);
            isCons2Active=false;
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
        //clearReel(this.ReelLinearLayout);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        //clearReel(this.ReelLinearLayout);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        //clearReel(this.ReelLinearLayout);
    }
}

