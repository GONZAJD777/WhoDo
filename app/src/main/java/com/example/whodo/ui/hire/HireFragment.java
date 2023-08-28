package com.example.whodo.ui.hire;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.BusinessClasses.User;
import com.example.whodo.BusinessClasses.UserSpecRating;
import com.example.whodo.MainActivity;
import com.example.whodo.MainActivityViewModel;
import com.example.whodo.R;
import com.example.whodo.SingletonUser;
import com.example.whodo.UiClasses.HireItem;
import com.example.whodo.crud.CRUD;
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

import java.util.ArrayList;
import java.util.Objects;


public class HireFragment extends Fragment implements OnMapReadyCallback {
    boolean mLocationPermissionsGranted = false;
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private MapView mapView;
    private GoogleMap mMap;
    private SeekBar DistanceFilterSeekBar;
    private LatLng DeviceLocation;
    private static String ServicePickerFilter="";
    private double ServiceDistanceFilter=10*0.009009009009009;
    private double LatUpperLimit;
    private double LatLowerLimit;
    private double LonRigthLimit;
    private double LonLetfLimit;
    private LinearLayout ReelItemsLinearLayout;
    private LinearLayout vServicesLinearLayout ;
    private FloatingActionButton FiltersButton;
    private FloatingActionButton ReelButton;
    private LinearLayout ReelLinearLayout;
    private LinearLayout FilterLinearLayout;
    private BottomSheetBehavior<LinearLayout> FiltersBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> ReelBottomSheetBehavior;
    private ConstraintLayout ButtonsConsLayout;
    private ConstraintSet mConstraintSet1 ; // Create a ConstraintSet.
    private ConstraintSet mConstraintSet2 ; // Create a ConstraintSet.
    private boolean isCons2Active;
    private ArrayList<User> Providers;
    private MainActivityViewModel model;
    private User LoggedUserSnapshot;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_main_frag_hire, container, false);

        //Button Btn = root.findViewById(R.id.button_ItemHire);
        FiltersButton=root.findViewById(R.id.FiltersButton);
        ReelButton=root.findViewById(R.id.ReelButton);
        FiltersButton.setOnClickListener(this::onClick);
        ReelButton.setOnClickListener(this::onClick);
        FilterLinearLayout = root.findViewById(R.id.Filter_bottom_sheet);


        vServicesLinearLayout=root.findViewById(R.id.LYV);

        ReelItemsLinearLayout = root.findViewById(R.id.ReelItemsLinearLayout);
        FiltersBottomSheetBehavior = BottomSheetBehavior.from(FilterLinearLayout);
        FiltersBottomSheetBehavior.setSkipCollapsed(true);
        ReelLinearLayout = root.findViewById(R.id.Reel_bottom_sheet);
        ReelBottomSheetBehavior = BottomSheetBehavior.from(ReelLinearLayout);

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
        TextView MaxDistanceFilterLabel = root.findViewById(R.id.MaxDistanceFilterLabel);
        MaxDistanceFilterLabel.setText("10km");


        FiltersBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetBehavior", "STATE_EXPANDED");
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
            int DistanceLabel=10;
            if (i==0) {
                DistanceLabel=1;
                ServiceDistanceFilter=1*0.009009009009009; }
            if (i==1) {
                DistanceLabel=2;
                ServiceDistanceFilter=2*0.009009009009009; }
            if (i==2) {
                DistanceLabel=5;
                ServiceDistanceFilter=5*0.009009009009009; }
            if (i==3) {
                DistanceLabel=10;
                ServiceDistanceFilter=10*0.009009009009009; }
            if (i==4) {
                DistanceLabel=15;
                ServiceDistanceFilter=15*0.009009009009009; }
            if (i==5) {
                DistanceLabel=20;
                ServiceDistanceFilter=20*0.009009009009009; }
            if (i==6) {
                DistanceLabel=25;
                ServiceDistanceFilter=25*0.009009009009009; }
            if (i==7) {
                DistanceLabel=30;
                ServiceDistanceFilter=30*0.009009009009009; }
            if (i==8) {
                DistanceLabel=35;
                ServiceDistanceFilter=35*0.009009009009009; }
            if (i==9) {
                DistanceLabel=40;
                ServiceDistanceFilter=40*0.009009009009009; }
            if (i==10) {
                DistanceLabel=45;
                ServiceDistanceFilter=45*0.009009009009009; }
            if (i==11) {
                DistanceLabel=50;
                ServiceDistanceFilter=50*0.009009009009009; }
            if (i==12) {
                DistanceLabel=55;
                ServiceDistanceFilter=60*0.009009009009009; }
            if (i==13) {
                DistanceLabel=60;
                ServiceDistanceFilter=65*0.009009009009009; }
            if (i==14) {
                DistanceLabel=70;
                ServiceDistanceFilter=70*0.009009009009009; }
            if (i==15) {
                DistanceLabel=75;
                ServiceDistanceFilter=75*0.009009009009009; }
            if (i==16) {
                DistanceLabel=80;
                ServiceDistanceFilter=80*0.009009009009009; }
            if (i==17) {
                DistanceLabel=85;
                ServiceDistanceFilter=85*0.009009009009009; }
            if (i==18) {
                DistanceLabel=90;
                ServiceDistanceFilter=90*0.009009009009009; }
            if (i==19) {
                DistanceLabel=100;
                ServiceDistanceFilter=100*0.009009009009009; }
            if (i==20) {
                DistanceLabel=150;
                ServiceDistanceFilter=150*0.009009009009009; }

            if(LoggedUserSnapshot!=null) {
                setBoundsProvDistFilter();
            }
            MaxDistanceFilterLabel.setText(DistanceLabel+"Km");
                //Log.i("FILTRANDO DISTANCIA", "UBICACION : LATUTID"+ SingletonUser.getInstance().getLatitude() + ",LONGITUD " + SingletonUser.getInstance().getLongitude() );
                //Log.i("FILTRANDO DISTANCIA", "LIMITES ENTRE LATITUD: Limite Superior "+ LatUpperLimit + ",Limite Inferior " + LatLowerLimit );
                //Log.i("FILTRANDO DISTANCIA", "LIMITES ENTRE LONGITUD: Limite Izquierdo "+ LonLetfLimit + ",Limite Derecho " +  LonRigthLimit);
            showProviders(Providers);
            //ReelBehavior();
            //ReelBehavior();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        return root;
    }
    public void loadProviders(User pLoggedUserSnapshot){
        LoggedUserSnapshot=pLoggedUserSnapshot;

        setBoundsProvDistFilter();
            if(getActivity()!=null) {
                model.getProviders(pLoggedUserSnapshot.getLatitude(), pLoggedUserSnapshot.getLongitude()).observe(requireActivity(), this::showProviders);
                model.getServices().observe(requireActivity(),this::loadServices);

            }
        }
    public void setBoundsProvDistFilter(){
        LatUpperLimit=LoggedUserSnapshot.getLatitude()+ServiceDistanceFilter;
        LatLowerLimit=LoggedUserSnapshot.getLatitude()-ServiceDistanceFilter;
        LonRigthLimit=LoggedUserSnapshot.getLongitude()+ServiceDistanceFilter;
        LonLetfLimit=LoggedUserSnapshot.getLongitude()-ServiceDistanceFilter;
    }
    @SuppressLint("LongLogTag")
    public void showProviders(ArrayList<User> pProviders){
        Providers=pProviders;
        clearLinearLayout(ReelItemsLinearLayout);
        if (mMap != null){
            mMap.clear();
           LatLng UserLatLon= new LatLng(LoggedUserSnapshot.getLatitude(),LoggedUserSnapshot.getLongitude());

            String mAvgTariff;
            String mAvgCompletionTime;
            String mOverallScore;

            try {
                mAvgTariff=LoggedUserSnapshot.getUserScore().getAvgTariff();
                mAvgCompletionTime=LoggedUserSnapshot.getUserScore().getAvgCompletionTime();
                mOverallScore=LoggedUserSnapshot.getUserScore().getOverallScore();
            }
            catch (Exception e)
            {
                Log.d("HIRE-FRAGMENT.showProviders()", "El USR no existe:");
                UserSpecRating USR=new UserSpecRating();
                USR.setRatingSpec("0");
                USR.setAppereanceScore("0");
                USR.setCleanlinessScore("0");
                USR.setSpeedScore("0");
                USR.setQualityScore("0");
                USR.setAvgTariff("0");
                USR.setAvgCompletionTime("0");
                USR.setOverallScore("0");
                LoggedUserSnapshot.setUserScore(USR);
                mAvgTariff=LoggedUserSnapshot.getUserScore().getAvgTariff();
                mAvgCompletionTime=LoggedUserSnapshot.getUserScore().getAvgCompletionTime();
                mOverallScore=LoggedUserSnapshot.getUserScore().getOverallScore();
            }
                         String s = LoggedUserSnapshot.getSpecialization().replaceAll("$", "|")  +
                         context.getResources().getString(R.string.HireFragment_MapMarkerSnipets_AvgTariff) + " " + mAvgTariff + "\n" +
                         context.getResources().getString(R.string.HireFragment_MapMarkerSnipets_AvgCompletionTime) + " " + mAvgCompletionTime + "\n" +
                         context.getResources().getString(R.string.HireFragment_MapMarkerSnipets_OverallScore) + " " + mOverallScore;

             if (Objects.equals(LoggedUserSnapshot.getType(), "1"))
             {
                 mMap.addMarker(new MarkerOptions().position(UserLatLon).title("Tu Ubicacion Registrada").snippet(s).icon(BitmapDescriptorFactory.fromResource(R.drawable.myloc_cus)));
             }  else {
                 mMap.addMarker(new MarkerOptions().position(UserLatLon).title("Tu Ubicacion Registrada").snippet(s).icon(BitmapDescriptorFactory.fromResource(R.drawable.myloc_prov)));
             }

        }

        if ( !(pProviders == null || pProviders.isEmpty() || pProviders.get(0) == null) )
        {
            for(int i = 0; i< pProviders.size(); i++) {
                if (pProviders.get(i).getLatitude()<LatUpperLimit &&
                        pProviders.get(i).getLatitude()>LatLowerLimit &&
                        pProviders.get(i).getLongitude()>LonLetfLimit &&
                        pProviders.get(i).getLongitude()<LonRigthLimit) {
                    if (pProviders.get(i).getSpecialization().equals(ServicePickerFilter) || Objects.equals(ServicePickerFilter, "")) {
                        LatLng mLatLon;
                        mLatLon = new LatLng(pProviders.get(i).getLatitude(), pProviders.get(i).getLongitude());

                        try {
                        addMarkers(mMap,
                                mLatLon,
                                pProviders.get(i).getName(),
                                pProviders.get(i).getSpecialization(),
                                pProviders.get(i).getUid(),
                                pProviders.get(i).getUserScore().getAvgTariff(),
                                pProviders.get(i).getUserScore().getAvgCompletionTime(),
                                pProviders.get(i).getUserScore().getOverallScore());
                                }
                        catch (Exception e)
                                {
                                    Log.d("HIRE-FRAGMENT.showProviders()", "El USR no existe:");
                                    UserSpecRating USR=new UserSpecRating();
                                    USR.setRatingSpec("-");
                                    USR.setAvgCompletionTime("0");
                                    USR.setAvgTariff("0");
                                    USR.setOverallScore("0");
                                    USR.setAppereanceScore("0");
                                    USR.setCleanlinessScore("0");
                                    USR.setSpeedScore("0");
                                    USR.setQualityScore("0");
                                    pProviders.get(i).setUserScore(USR);
                                    addMarkers( mMap,
                                                mLatLon,
                                                pProviders.get(i).getName(),
                                                pProviders.get(i).getSpecialization(),
                                                pProviders.get(i).getUid(),
                                                pProviders.get(i).getUserScore().getAvgTariff(),
                                                pProviders.get(i).getUserScore().getAvgCompletionTime(),
                                                pProviders.get(i).getUserScore().getOverallScore());
                                }
                                addHireItem(pProviders.get(i));//Agrego el item al reel
                    }
                }
            }
        }
        ReelBehavior();
        ReelBehavior();
    }
    public void ReelBehavior(){
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
    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.ReelButton:
                ReelBehavior();
                //CRUD crud = new CRUD();
                //crud.CreateUserField();
                break;
            case R.id.FiltersButton:
                if (FiltersBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    FiltersBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (FiltersBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED || FiltersBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_HALF_EXPANDED ){
                    FiltersBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
        }
    }
    public void addHireItem(User pProvider){
         HireItem mHireItem = new HireItem(context);
         mHireItem.setName(pProvider.getName());
         try{
         mHireItem.PricePercent(pProvider.getUserScore().getAvgTariff()+"");
         mHireItem.setReviews(pProvider.getUserScore().getOverallScore()+"");
         mHireItem.setSpeed(pProvider.getUserScore().getAvgCompletionTime()+"");
    }
                     catch (Exception e)
    {

    }
         mHireItem.setSpec(pProvider.getSpecialization().toUpperCase().replaceAll(",$", ""));
         mHireItem.setImage(pProvider.getProfilePicture());
         ReelItemsLinearLayout.addView(mHireItem);
    }
    public void loadServices(ArrayList<String> pServices){
        clearLinearLayout(vServicesLinearLayout);
        RadioGroup RadioGroupFilter = new RadioGroup(requireContext());
        vServicesLinearLayout.addView(RadioGroupFilter);
        RadioButton ServiceRadioButtonALL = new RadioButton(requireContext());
        ServiceRadioButtonALL.setText(requireContext().getText(R.string.HireFragment_RadioButton_AllServiceFilter));
        ServiceRadioButtonALL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ServicePickerFilter="";
                }
                showProviders(Providers);
            }
        });
        RadioGroupFilter.addView(ServiceRadioButtonALL);
        ServiceRadioButtonALL.setChecked(true);
        //*//******************************************************************************
        //*//Se comienza a recorrer la lista con los servicio para agregar los botones
        //*//y validar cuales estan checkeados por el usuario para filtrar
        for(int i = 0; i< pServices.size(); i++) {
            RadioButton ServiceCheckBox = new RadioButton(requireContext());
            ServiceCheckBox.setText(pServices.get(i));
            //******************************************************************************
            //Se valida si el servicio del checkbox esta en la variable ServicePickerFilter
            //para aplicar el filtro
            ServiceCheckBox.setChecked(ServicePickerFilter.toUpperCase().contains(pServices.get(i).toUpperCase()));
            //******************************************************************************
            ServiceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        ServicePickerFilter=compoundButton.getText().toString();
                    }
                    showProviders(Providers);
                }
            });
            //se agrega el Checkbox con la configuracion
            RadioGroupFilter.addView(ServiceCheckBox);
        }

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.setOnMapClickListener(arg0 -> {
                    Log.i("onMapClick", "Horray!");
                    if (FiltersBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                        FiltersBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });
            if (mLocationPermissionsGranted) {
                if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
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

                    googleMap.setPadding(50,30,0,0);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    googleMap.setMyLocationEnabled(true);
                    getDeviceLocation();
                    setMarkerSnippet();
                    //once map is ready, we proceed to add de Markers
            }
        }
    public void setMarkerSnippet(){
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            public View getInfoWindow(@NonNull Marker arg0) {
                return null;
            }
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippetSpec = new TextView(context);
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

                TextView snippetInfo = new TextView(context);
                snippetInfo.setTextColor(Color.GRAY);
                int indexStart = marker.getSnippet().indexOf("|")+1;
                int indexEnd = marker.getSnippet().length();
                //Log.d("MAPMARKER SNIPPET", "INDEXSTART " +indexStart);
                //Log.d("MAPMARKER SNIPPET", "INDEXEND " +indexEnd);
                snippetInfo.setText(Objects.requireNonNull(marker.getSnippet()).substring(indexStart,indexEnd));

                info.addView(title);
                info.addView(snippetSpec);
                info.addView(snippetInfo);

                return info;
            }
        });
    }
    private void getLocationPermission(){
        Log.d("getLocationPermission()", "getLocationPermission: getting location permissions");
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
    public void addMarkers(GoogleMap googleMap, LatLng pLatLon, String pName, String pSpecialization, String pUID, String pAvgTariff, String pAvgCompletionTime,String pOverallScore){

        String AvgTariff = context.getResources().getString(R.string.HireFragment_MapMarkerSnipets_AvgTariff);
        String AvgCompletionTime = context.getResources().getString(R.string.HireFragment_MapMarkerSnipets_AvgCompletionTime);
        String OverallScore = context.getResources().getString( R.string.HireFragment_MapMarkerSnipets_OverallScore);

        String SnippetText = pSpecialization.replaceAll("$", "|") +
                AvgTariff + " " + pAvgTariff + "\n" +
                AvgCompletionTime + " " + pAvgCompletionTime + "\n" +
                OverallScore + " " + pOverallScore;
        //Log.d("SNIPPET", SnippetText);


        switch (pSpecialization) {
                    case "Plomero":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.plomeria)));
                        break;
                    case "Pintor":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.pintor)));
                        break;
                    case "Electricista":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.electricista)));
                        break;
                    case "Carpintero":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.carpinteria)));
                        break;
                    case "Albañil":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.ordenalba_il)));
                        break;
                    case "Tecnico de Computacion":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.computacion)));
                        break;
                    case "Tecnico de Refrigeracion":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.refrigeracion)));
                        break;
                    case "Jardinero":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.jardineria)));
                        break;
                    case "Cerrajero":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.cerrajeria)));
                        break;
                    case "Gasista":
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.gasista)));
                        break;
                    default:
                        googleMap.addMarker(new MarkerOptions().position(pLatLon).title(pName).snippet(SnippetText).icon(BitmapDescriptorFactory.fromResource(R.drawable.proveevarios)));

        }


    }
    private void getDeviceLocation(){
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity());
        try{
            if(mLocationPermissionsGranted){
                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Location currentLocation = (Location) task.getResult();
                        if (currentLocation != null) {
                            DeviceLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            moveCamera(DeviceLocation);
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
    private void moveCamera(LatLng latLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) 11.75));
    }
    private void initMap(){
        mapView.getMapAsync(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        model.getLoggedUser().observe(requireActivity(), this::loadProviders);
        if(context==null) {
            context = getActivity();
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
    public void clearLinearLayout(LinearLayout LL){
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
}

