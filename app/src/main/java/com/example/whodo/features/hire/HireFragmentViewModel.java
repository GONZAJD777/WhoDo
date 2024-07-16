package com.example.whodo.features.hire;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.whodo.aplication.Callback;
import com.example.whodo.domain.user.User;
import com.example.whodo.domain.user.UserDTO;
import com.example.whodo.domain.user.dao.FirebaseUserDAO;
import com.example.whodo.domain.user.dao.UserDao;
import com.example.whodo.domain.workOrder.WorkOrder;
import com.example.whodo.domain.workOrder.WorkOrderDTO;
import com.example.whodo.domain.workOrder.WorkOrderMapper;
import com.example.whodo.domain.workOrder.dao.FirebaseWorkOrderDAO;
import com.example.whodo.domain.workOrder.dao.WorkOrderDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HireFragmentViewModel extends ViewModel {
    private final Double DIST_MULT=0.009009009009009;
    private User mUser = new User();
    private List<User> mProviders = new ArrayList<>();
    private List<String> mServices = new ArrayList<>();
    private final MutableLiveData <User> mPickedProviders = new MutableLiveData<>();

    private final MutableLiveData <List<User>> mProvidersLiveData = new MutableLiveData<>();
    //private final MutableLiveData <WorkOrder> mPickedWorkOrder = new MutableLiveData<>();
    private final MutableLiveData<List<String>> mServiceFilter= new MutableLiveData<>();
    private final MutableLiveData<Double> mDistanceFilter= new MutableLiveData<>();
    private int mDistanceFilterPoint;
    private double LatUpperLimit;
    private double LatLowerLimit;
    private double LonRigthLimit;
    private double LonLetfLimit;
    //private final WorkOrderDao<WorkOrderDTO> mWorkOrderDao;


    public HireFragmentViewModel() {
        //mWorkOrderDao = new FirebaseWorkOrderDAO();
        mDistanceFilter.setValue(10*DIST_MULT);
        mDistanceFilterPoint=3;
    }

    public void setUser(User pUser){ this.mUser=pUser; }
    public void setProviders (List<User> pProviders) {
        if(mProvidersLiveData.getValue()==null)
            mProvidersLiveData.setValue(pProviders);
        mProviders=pProviders;
    }
    public void setServices(List<String> pServices) { mServices=pServices; }
    public void setPickedProvider(User mPick) {mPickedProviders.setValue(mPick);}
    public LiveData<User> getPickedProvider () {return mPickedProviders;}
    private User getUser() { return this.mUser; }
    private List<User> getProviders() { return this.mProviders; }
    private List<String> getServices() { return this.mServices; }
    //**************************************************************************//
    public void setServiceFilter(List<String> pServiceFilter) {
        mServiceFilter.setValue(pServiceFilter);
        this.setProvidersLiveData(this.getProviders(),this.getDistanceFilter().getValue(),this.getServiceFilter().getValue());
    }
    public void setDistanceFilter (int pDistanceFilterPoint) {
        switch (pDistanceFilterPoint){
            case 0:
                mDistanceFilter.setValue(1*DIST_MULT);
                break;
            case 1:
                mDistanceFilter.setValue(2*DIST_MULT);
                break;
            case 2:
                mDistanceFilter.setValue(5*DIST_MULT);
                break;
            case 3:
                mDistanceFilter.setValue(10*DIST_MULT);
                break;
            case 4:
                mDistanceFilter.setValue(15*DIST_MULT);
                break;
            case 5:
                mDistanceFilter.setValue(20*DIST_MULT);
                break;
            case 6:
                mDistanceFilter.setValue(25*DIST_MULT);
                break;
            case 7:
                mDistanceFilter.setValue(30*DIST_MULT);
                break;
            case 8:
                mDistanceFilter.setValue(35*DIST_MULT);
                break;
            case 9:
                mDistanceFilter.setValue(40*DIST_MULT);
                break;
            case 10:
                mDistanceFilter.setValue(45*DIST_MULT);
                break;
            case 11:
                mDistanceFilter.setValue(50*DIST_MULT);
                break;
            case 12:
                mDistanceFilter.setValue(55*DIST_MULT);
                break;
            case 13:
                mDistanceFilter.setValue(60*DIST_MULT);
                break;
            case 14:
                mDistanceFilter.setValue(65*DIST_MULT);
                break;
            case 15:
                mDistanceFilter.setValue(70*DIST_MULT);
                break;
            case 16:
                mDistanceFilter.setValue(75*DIST_MULT);
                break;
            case 17:
                mDistanceFilter.setValue(80*DIST_MULT);
                break;
            case 18:
                mDistanceFilter.setValue(85*DIST_MULT);
                break;
            case 19:
                mDistanceFilter.setValue(90*DIST_MULT);
                break;
            case 20:
                mDistanceFilter.setValue(100*DIST_MULT);
                break;
        }
        mDistanceFilterPoint=pDistanceFilterPoint;
        this.setProvidersLiveData(this.getProviders(),this.getDistanceFilter().getValue(),this.getServiceFilter().getValue());
    }
    private void setProvidersLiveData (List<User> pProviders, Double pDistanceFilter , List<String> pServiceFilter) {
        List<User> ProvidersList = new ArrayList<>();
        LatUpperLimit=mUser.getLatitude()+pDistanceFilter;
        LatLowerLimit=mUser.getLatitude()-pDistanceFilter;
        LonRigthLimit=mUser.getLongitude()+pDistanceFilter;
        LonLetfLimit=mUser.getLongitude()-pDistanceFilter;

        if (!pProviders.isEmpty())
        {
            for(int i = 0; i< pProviders.size(); i++) {
                if (Objects.equals(pProviders.get(i).getUid(), this.getUser().getUid())) {
                    ProvidersList.add(pProviders.get(i));
                } else {
                    if (CheckRange(pProviders.get(i))) {
                        if (!pServiceFilter.isEmpty()) {
                            for (int j = 0; j < pServiceFilter.size(); j++) {
                                if (pProviders.get(i).getSpecialization().contains(pServiceFilter.get(j))) {
                                    if (!ProvidersList.contains(pProviders.get(i))) {
                                        ProvidersList.add(pProviders.get(i));
                                    }
                                }
                            }
                        } else {
                            ProvidersList.add(pProviders.get(i));
                        }
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
    public LiveData<List<String>> getServiceFilter(){ return mServiceFilter; }
    public LiveData<Double> getDistanceFilter() { return mDistanceFilter; }
    public int getDistanceFilterPoint() { return mDistanceFilterPoint; }
    public LiveData<List<User>> getProvidersLiveData(){ return mProvidersLiveData; }
    //**************************************************************************//

}
