package com.example.whodo.features.hire;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.whodo.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HireFragmentViewModel extends ViewModel {

    private User mUser = new User();
    private List<User> mProviders = new ArrayList<>();
    private final MutableLiveData <List<User>> mProvidersLiveData = new MutableLiveData<>();
    private List<String> mServices = new ArrayList<>();

    private final MutableLiveData<Integer> ServiceDistanceFilter= new MutableLiveData<>();
    private double LatUpperLimit;
    private double LatLowerLimit;
    private double LonRigthLimit;
    private double LonLetfLimit;

    public HireFragmentViewModel() {
        ServiceDistanceFilter.setValue(10);
    }

    public void setUser(User pUser){ this.mUser=pUser; }
    private User getUser() { return this.mUser; }
    public void setProviders (List<User> pProviders) {
        if(mProvidersLiveData!=null){mProvidersLiveData.setValue(pProviders);}
        mProviders=pProviders; }
    private List<User> getProviders() { return this.mProviders; }
    public void setServices(List<String> pServices) { mServices=pServices; }
    private List<String> getServices() { return this.mServices; }
    //**************************************************************************//

    public void SetDistanceFilter (int pDistanceFilterPoint) {
        switch (pDistanceFilterPoint){
            case 0:
                ServiceDistanceFilter.setValue(1);
                break;
            case 1:
                ServiceDistanceFilter.setValue(2);
                break;
            case 2:
                ServiceDistanceFilter.setValue(5);
                break;
            case 3:
                ServiceDistanceFilter.setValue(10);
                break;
            case 4:
                ServiceDistanceFilter.setValue(15);
                break;
            case 5:
                ServiceDistanceFilter.setValue(20);
                break;
            case 6:
                ServiceDistanceFilter.setValue(25);
                break;
            case 7:
                ServiceDistanceFilter.setValue(30);
                break;
            case 8:
                ServiceDistanceFilter.setValue(35);
                break;
            case 9:
                ServiceDistanceFilter.setValue(40);
                break;
            case 10:
                ServiceDistanceFilter.setValue(45);
                break;
            case 11:
                ServiceDistanceFilter.setValue(50);
                break;
            case 12:
                ServiceDistanceFilter.setValue(55);
                break;
            case 13:
                ServiceDistanceFilter.setValue(60);
                break;
            case 14:
                ServiceDistanceFilter.setValue(65);
                break;
            case 15:
                ServiceDistanceFilter.setValue(70);
                break;
            case 16:
                ServiceDistanceFilter.setValue(75);
                break;
            case 17:
                ServiceDistanceFilter.setValue(80);
                break;
            case 18:
                ServiceDistanceFilter.setValue(85);
                break;
            case 19:
                ServiceDistanceFilter.setValue(90);
                break;
            case 20:
                ServiceDistanceFilter.setValue(100);
                break;
        }
        this.setProvidersLiveData(Objects.requireNonNull(mProvidersLiveData.getValue()),ServiceDistanceFilter.getValue(),mServices);

    }
    public LiveData<Integer> getDistanceFilter() { return ServiceDistanceFilter; }
    public LiveData<List<User>> getProvidersLiveData(){ return mProvidersLiveData; }
    private void setProvidersLiveData (List<User> pProviders, int pDistanceFilter , List<String> pServiceFilter) {
            List<User> ProvidersList = new ArrayList<>();
            LatUpperLimit=mUser.getLatitude()+pDistanceFilter;
            LatLowerLimit=mUser.getLatitude()-pDistanceFilter;
            LonRigthLimit=mUser.getLongitude()+pDistanceFilter;
            LonLetfLimit=mUser.getLongitude()-pDistanceFilter;

        if (!pProviders.isEmpty())
        {
            for(int i = 0; i< pProviders.size(); i++) {
                if (CheckRange(pProviders.get(i))) {
                    if (!pServiceFilter.isEmpty()) {
                        for (int j = 0; j < pServiceFilter.size(); j++) {
                            if (pProviders.get(i).getSpecialization().contains(pServiceFilter.get(j))) {
                                if (!ProvidersList.contains(pProviders.get(i))){
                                    ProvidersList.add(pProviders.get(i));
                                    //addMarkers(mMap, pProviders.get(i));
                                    //addHireItem(pProviders.get(i));//Agrego el item al reel
                                }
                            }
                        }
                    } else {
                        ProvidersList.add(pProviders.get(i));
                    }
                }
            }
            mProvidersLiveData.setValue(ProvidersList);
        }



    }
    private boolean CheckRange (User pProvider) {
        return pProvider.getLatitude() < LatUpperLimit &&
                pProvider.getLatitude() > LatLowerLimit &&
                pProvider.getLongitude() > LonLetfLimit &&
                pProvider.getLongitude() < LonRigthLimit;
    }
}
