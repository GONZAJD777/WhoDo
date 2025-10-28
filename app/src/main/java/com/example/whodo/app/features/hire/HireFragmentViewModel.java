package com.example.whodo.app.features.hire;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.resources.images.ImagesViewModel;
import com.example.whodo.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HireFragmentViewModel extends ViewModel {
    private static final String TAG = "HIRE-FRAG-VIEW-MODEL";
    private static final double DIST_MULT = 0.009009009009009;
    private static final int[] DISTANCE_MULTIPLIERS = {1, 2, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 100};

    private User mUser = new User();
    private List<User> mProviders = new ArrayList<>();
    private List<String> mServices = new ArrayList<>();

    private final MutableLiveData<User> mPickedProvider = new MutableLiveData<>();
    private final MutableLiveData<List<User>> mProvidersLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> mServiceFilter = new MutableLiveData<>();
    private final MutableLiveData<Double> mDistanceFilter = new MutableLiveData<>();

    private int mDistanceFilterPoint;

    // LiveData for the final combined result
    private MediatorLiveData<Boolean> allDataReady = new MediatorLiveData<>();
    private MutableLiveData<List<User>> originalProviders = new MutableLiveData<>();
    private MutableLiveData <List<String>> storedIconNames= new MutableLiveData<>();;
    public HireFragmentViewModel() { // Pass the other ViewModel or a Repository

        // Valor por defecto para el filtro de distancia: 10 * DIST_MULT
        mDistanceFilter.setValue(10 * DIST_MULT);
        mDistanceFilterPoint = 3;


        try {
            initializeObservers();
            allDataReady.observeForever(ready -> {
                if (Boolean.TRUE.equals(ready)) {
                    try {
                        setProviders(originalProviders.getValue());
                    } catch (NumberFormatException e) {
                        Log.d(TAG, "Error al convertir el valor a Double: " + e.getMessage());
                    }
                }
            });
        }catch (Exception e){
            Log.d(TAG, "Error al llamar a la base de datos --> espera a la actualizacion de datos." + e);
        }
    }
    private void initializeObservers() {
        allDataReady.addSource(originalProviders, providers -> checkReady());
        allDataReady.addSource(storedIconNames, icons -> checkReady());
    }

    private void checkReady() {
        if (originalProviders.getValue() != null && storedIconNames.getValue() != null) {
            allDataReady.setValue(true);
        }
    }

    public void setServIconNames(List<String> pIconServIconNames) {
        this.storedIconNames.setValue(pIconServIconNames);
    }

    public void setOriginalProviders(List<User> originalProviders){
        this.originalProviders.setValue(originalProviders);
    }
    //==================================================================================//

    // Setters
    public void setUser(User user) {
        this.mUser = user;
        updateProviders();
    }

    public void setProviders(List<User> providers) {
        this.mProviders = providers;
        updateProviders();
    }

    public void setServices(List<String> services) {
        this.mServices = services;
    }

    public void setPickedProvider(User pickedProvider) {
        mPickedProvider.setValue(pickedProvider);
    }

    public void setServiceFilter(List<String> serviceFilter) {
        mServiceFilter.setValue(serviceFilter);
        updateProviders();
    }

    public void setDistanceFilter(int distanceFilterPoint) {
        if (distanceFilterPoint >= 0 && distanceFilterPoint < DISTANCE_MULTIPLIERS.length) {
            mDistanceFilter.setValue(DISTANCE_MULTIPLIERS[distanceFilterPoint] * DIST_MULT);
        }
        mDistanceFilterPoint = distanceFilterPoint;
        updateProviders();
    }

    // Getters
    public LiveData<User> getPickedProvider() {
        return mPickedProvider;
    }

    public LiveData<List<String>> getServiceFilter() {
        return mServiceFilter;
    }

    public LiveData<Double> getDistanceFilter() {
        return mDistanceFilter;
    }

    public int getDistanceFilterPoint() {
        return mDistanceFilterPoint;
    }

    public LiveData<List<User>> getProvidersLiveData() {
        return mProvidersLiveData;
    }

    /**
     * Actualiza la lista de proveedores filtrados según el usuario actual, el filtro de distancia y el de servicios.
     */
    private void updateProviders() {
        Double distanceFilter = mDistanceFilter.getValue();
        List<String> serviceFilter = mServiceFilter.getValue();

        // Comprobamos que el usuario y su ubicación estén configurados
        if (mUser == null || mUser.getLocation() == null) {
            Log.d(TAG, "Usuario o ubicación no configurada. Se omite la actualización.");
            return;
        }

        double userLatitude = mUser.getLocation().getLatitude();
        double userLongitude = mUser.getLocation().getLongitude();
        double latUpperLimit = userLatitude + distanceFilter;
        double latLowerLimit = userLatitude - distanceFilter;
        double lonRightLimit = userLongitude + distanceFilter;
        double lonLeftLimit = userLongitude - distanceFilter;

        List<User> filteredProviders = new ArrayList<>();

        if (mProviders == null || mProviders.isEmpty()) {
            mProvidersLiveData.setValue(filteredProviders);
            return;
        }

        for (User provider : mProviders) {
            // Siempre se incluye el usuario actual
            if (Objects.equals(provider.getAuthId(), mUser.getAuthId())) {
                filteredProviders.add(provider);
                continue;
            }

            // Si la ubicación del proveedor es nula, saltamos
            if (provider.getLocation() == null) {
                continue;
            }

            // Verifica que el proveedor se encuentre dentro de los límites geográficos
            if (!isWithinRange(provider, latUpperLimit, latLowerLimit, lonLeftLimit, lonRightLimit)) {
                continue;
            }

            // Si no existe filtro de servicios, se agrega el proveedor
            if (serviceFilter == null || serviceFilter.isEmpty()) {
                filteredProviders.add(provider);
                continue;
            }

            // Añade el proveedor si su especialización coincide con algún servicio del filtro
            for (String service : serviceFilter) {
                if (provider.getSpecialization().contains(service)) {
                    filteredProviders.add(provider);
                    break;
                }
            }
        }

        mProvidersLiveData.setValue(filteredProviders);
    }

    /**
     * Verifica si un proveedor se encuentra dentro de los límites geográficos especificados.
     *
     * @param provider   Proveedor a comparar.
     * @param latUpper   Límite superior de latitud.
     * @param latLower   Límite inferior de latitud.
     * @param lonLeft    Límite izquierdo de longitud.
     * @param lonRight   Límite derecho de longitud.
     * @return true si el proveedor está dentro de los límites; false en caso contrario.
     */
    private boolean isWithinRange(User provider, double latUpper, double latLower, double lonLeft, double lonRight) {
        double providerLat = provider.getLocation().getLatitude();
        double providerLon = provider.getLocation().getLongitude();
        return providerLat < latUpper && providerLat > latLower &&
                providerLon > lonLeft && providerLon < lonRight;
    }


}