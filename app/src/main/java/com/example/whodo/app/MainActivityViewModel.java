package com.example.whodo.app;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.user.dao.Impl.UserDaoImpl;
import com.example.whodo.app.domain.user.dao.UserDao;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.domain.workOrder.dao.Impl.WorkOrderDaoImpl;
import com.example.whodo.app.domain.workOrder.dao.WorkOrderDao;
import com.example.whodo.app.features.favorites.FavoritesFragment;
import com.example.whodo.app.features.hire.HireFragment;
import com.example.whodo.app.features.activity.workOrder.WorkOrderFragment;
import com.example.whodo.app.features.messages.MessagesFragment;
import com.example.whodo.app.features.profile.MainProfileFragment;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.features.activity.ActivityFragment;
import com.example.whodo.app.features.profile.fragments.CommentsFragment;
import com.example.whodo.app.features.profile.fragments.EditProfileFragment;
import com.example.whodo.app.features.profile.fragments.LegalTermsFragment;
import com.example.whodo.app.features.profile.fragments.PersonalInfoFragment;
import com.example.whodo.app.features.profile.fragments.PrivacyPoliticsFragment;
import com.example.whodo.app.features.profile.fragments.ProviderModeFragment;
import com.example.whodo.app.features.profile.fragments.RecomendationsFragment;
import com.example.whodo.app.features.profile.fragments.SecurityFragment;
import com.example.whodo.app.features.profile.fragments.SupportFragment;
import com.example.whodo.app.features.profile.fragments.TutorialFragment;
import com.example.whodo.app.network.ApiResponse;
import com.example.whodo.app.resources.parameters.Impl.ParametersDaoImpl;
import com.example.whodo.app.resources.parameters.Parameter;
import com.example.whodo.app.resources.parameters.ParametersDao;
import com.example.whodo.app.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class MainActivityViewModel extends AndroidViewModel {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String TAG = "MAIN-ACTIVITY-VIEWMODEL";

    private User mSnapshotUser= new User(); // No es LiveData
    private final MutableLiveData<User> mWorkOrdersCustomer = new MutableLiveData<>();
    private final MutableLiveData<Fragment> mFragmentSelected = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTabLayoutVisibility = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSeletedTab = new MutableLiveData<>();
    private final MutableLiveData<WorkOrder> mPickedWorkOrder = new MutableLiveData<>();

    private final MutableLiveData<User> mUser = new MutableLiveData<>();
    private final MutableLiveData<List<WorkOrder>> mWorkOrders = new MutableLiveData<>();
    private final MutableLiveData<List<User>> mProviders = new MutableLiveData<>();
    private final MutableLiveData<List<Parameter>> mParameters = new MutableLiveData<>();
    private MediatorLiveData<Boolean> allDataReady = new MediatorLiveData<>();

    private UserDao<User> mUserDao;
    private WorkOrderDao<WorkOrder> mWorkOrderDao;
    private final ParametersDao<Parameter> mParametersDao;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        //private final SSEUserClient sseUserClient;
        mUserDao = new UserDaoImpl(application.getApplicationContext());
        mWorkOrderDao= new WorkOrderDaoImpl(application.getApplicationContext());
        mParametersDao= new ParametersDaoImpl(application.getApplicationContext());

        mSeletedTab.setValue(0);
        mTabLayoutVisibility.setValue(View.VISIBLE);
        mFragmentSelected.setValue(new HireFragment());

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            User currentUser = new User(user.getUid());
            Log.d(TAG, "mAuth no es nulo y currentUser: " + currentUser);
            StartUserUpdateThread();
            try {
                initializeObservers();
                loadUserInfo(currentUser);
                loadParameters();
                allDataReady.observeForever(ready -> {
                    if (Boolean.TRUE.equals(ready)) {
                        try {
                            String value = Utils.findParameterById(Objects.requireNonNull(mParameters.getValue()), "USR_MAX_DISTANCE").getValue();
                            Double distance = Double.parseDouble(value);
                            Log.d(TAG, "Distancia: " + distance);
                            loadUserProviders(mUser.getValue(), distance);
                            loadUserWorkOrders(Objects.requireNonNull(mUser.getValue()));
                        } catch (NumberFormatException e) {
                            Log.d(TAG, "Error al convertir el valor a Double: " + e.getMessage());
                        }
                    }
                });
            }catch (Exception e){
                Log.d(TAG, "Error al llamar a la base de datos --> espera a la actualizacion de datos." + e);
            }
        } else {
            Log.e(TAG, "Usuario no autenticado");
        }

    }

    private void initializeObservers() {
        allDataReady.addSource(mUser, user -> checkReady());
        allDataReady.addSource(mParameters, params -> checkReady());
    }

    private void checkReady() {
        if (mUser.getValue() != null && mParameters.getValue() != null) {
            allDataReady.setValue(true);
        }
    }

    public LiveData<User> getLoggedUser() {
        return this.mUser;
    }
    public LiveData<List<User>> getProviders() {
        return this.mProviders;
    }
    public LiveData<List<Parameter>> getParameters() {
        return this.mParameters;
    }

    public LiveData<Fragment> getSelectedFragment() {
        return this.mFragmentSelected;
    }

    public LiveData<Integer> getTabLayoutVisibility() {
        return this.mTabLayoutVisibility;
    }

    public LiveData<Integer> getSelectedTab() {
        return this.mSeletedTab;
    }

    //**************************************************************
    public void setSelectedTab(int pTab) {
        this.mSeletedTab.setValue(pTab);
    }

    public void setSelectedFragment(int pFragment, int pTabLayoutVisibility) {
        mTabLayoutVisibility.setValue(pTabLayoutVisibility);

        switch (pFragment) {
            case 0:
                mFragmentSelected.setValue(new HireFragment());
                break;
            case 1:
                mFragmentSelected.setValue(new FavoritesFragment());
                break;
            case 2:
                mFragmentSelected.setValue(new ActivityFragment());
                break;
            case 3:
                mFragmentSelected.setValue(new MessagesFragment());
                break;
            case 4:
                mFragmentSelected.setValue(new MainProfileFragment());
                break;
            case 5:
                mFragmentSelected.setValue(new EditProfileFragment());
                break;
            case 6:
                mFragmentSelected.setValue(new PersonalInfoFragment());
                break;
            case 7:
                mFragmentSelected.setValue(new SecurityFragment());
                break;
            case 8:
                mFragmentSelected.setValue(new ProviderModeFragment());
                break;
            case 9:
                mFragmentSelected.setValue(new TutorialFragment());
                break;
            case 10:
                mFragmentSelected.setValue(new RecomendationsFragment());
                break;
            case 11:
                mFragmentSelected.setValue(new SupportFragment());
                break;
            case 12:
                mFragmentSelected.setValue(new CommentsFragment());
                break;
            case 13:
                mFragmentSelected.setValue(new LegalTermsFragment());
                break;
            case 14:
                mFragmentSelected.setValue(new PrivacyPoliticsFragment());
                break;
            case 15:
                mFragmentSelected.setValue(new WorkOrderFragment());
                break;
        }
    }

    public void updateLoggedUser(User pUser) {
        this.mUser.setValue(pUser);
    }

    private void StartUserUpdateThread() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Log.i(TAG, "mUser : " + Objects.requireNonNull(mUser.getValue()).toString());
                    Log.i(TAG, "mSnapshotUser : " + mSnapshotUser.toString());
                    updateUser(Objects.requireNonNull(mUser.getValue()), Objects.requireNonNull(mSnapshotUser));
                } catch (Exception e) {
                    Log.i(TAG, "StartUserUpdateThread --> BackgroundUpdateUser.UpdateUser(): " + e);
                } finally {
                    handler.postDelayed(this, 60000);
                }
            }
        }).start();
    }

    public void createUser(User pUser, Callback<User> callback) {
        mUserDao.create(pUser, new Callback<User>() {
            @Override
            public void onSuccess(User pUser) {
                callback.onSuccess(pUser);
                Log.d(TAG, "createUser() Method --> User has been created " + pUser);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
                Log.d(TAG, "createUser() Method --> User couldn't been created" + e);
            }
        });
    }

    private void updateUser(User pUser, User pUserSnapshot) {
        Log.i(TAG, "Operacion CRUD.UpdateUser() Se actualizara el usuario:" + "USERS/" + pUser.getId());
        User userToUpdate = new User();
        userToUpdate.setId(pUser.getId());
        if (!Objects.equals(pUser.getAddress(), pUserSnapshot.getAddress())) {
            Log.i(TAG, "UpdateUser DIRECCION ANTIGUA: " + pUserSnapshot.getAddress());
            Log.i(TAG, "UpdateUser DIRECCION NUEVA: " + pUser.getAddress());
            userToUpdate.setAddress(pUser.getAddress());
        }
        if (!Objects.equals(pUser.getBirthday(), pUserSnapshot.getBirthday())) {
            Log.i(TAG, "UpdateUser CUMPLEAÑOS ANTIGUA: " + pUserSnapshot.getBirthday());
            Log.i(TAG, "UpdateUser CUMPLEAÑOS NUEVA: " + pUser.getBirthday());
            userToUpdate.setBirthday(pUser.getBirthday());
        }
        if (!Objects.equals(pUser.getDescription(), pUserSnapshot.getDescription())) {
            Log.i(TAG, "UpdateUser DESCRIPCION ANTIGUA: " + pUserSnapshot.getDescription());
            Log.i(TAG, "UpdateUser DESCRIPCION NUEVA: " + pUser.getDescription());
            userToUpdate.setDescription(pUser.getDescription());
        }
        if (!Objects.equals(pUser.getEmail(), pUserSnapshot.getEmail())) {
            Log.i(TAG, "UpdateUser CORREO ANTIGUA: " + pUserSnapshot.getEmail());
            Log.i(TAG, "UpdateUser CORREO NUEVA: " + pUser.getEmail());
            userToUpdate.setEmail(pUser.getEmail());
        }

        if (!(new HashSet<>(pUserSnapshot.getLanguages()).containsAll(pUser.getLanguages()) && new HashSet<>(pUser.getLanguages()).containsAll(pUserSnapshot.getLanguages())) ) {
            Log.i(TAG, "UpdateUser IDIOMAS ANTIGUA: " + pUserSnapshot.getLanguages());
            Log.i(TAG, "UpdateUser IDIOMAS NUEVA: " + pUser.getLanguages());
            userToUpdate.setLanguages(pUser.getLanguages());
        }
        if (!Objects.equals(pUser.getLocation().getLatitude(), pUserSnapshot.getLocation().getLatitude()) || !Objects.equals(pUser.getLocation().getLongitude(), pUserSnapshot.getLocation().getLongitude())) {
            Log.i(TAG, "UpdateUser LATITUD ANTIGUA: " + pUserSnapshot.getLocation().getLatitude());
            Log.i(TAG, "UpdateUser LATITUD NUEVA: " + pUser.getLocation().getLatitude());
            Log.i(TAG, "UpdateUser LONGITUD ANTIGUA: " + pUserSnapshot.getLocation().getLongitude());
            Log.i(TAG, "UpdateUser LONGITUD NUEVA: " + pUser.getLocation().getLongitude());
            userToUpdate.getLocation().setLatitude(pUser.getLocation().getLatitude());
            userToUpdate.getLocation().setLongitude(pUser.getLocation().getLongitude());
        }
        if (!Objects.equals(pUser.getName(), pUserSnapshot.getName())) {
            Log.i(TAG, "UpdateUser NOMBRE ANTIGUA: " + pUserSnapshot.getName());
            Log.i(TAG, "UpdateUser NOMBRE NUEVA: " + pUser.getName());
            userToUpdate.setName(pUser.getName());
        }
        if (!Objects.equals(pUser.getPassword(), pUserSnapshot.getPassword())) {
            Log.i(TAG, "UpdateUser PASSWORD ANTIGUA: " + pUserSnapshot.getPassword());
            Log.i(TAG, "UpdateUser PASSWORD NUEVA: " + pUser.getPassword());
            userToUpdate.setPassword(pUser.getPassword());
        }
        if (!Objects.equals(pUser.getPhone().getNumber(), pUserSnapshot.getPhone().getNumber())) {
            Log.i(TAG, "UpdateUser TELEFONO ANTIGUA: " + pUserSnapshot.getPhone().getNumber());
            Log.i(TAG, "UpdateUser TELEFONO NUEVA: " + pUser.getPhone().getNumber());
            userToUpdate.getPhone().setNumber(pUser.getPhone().getNumber());
        }
        if (!Objects.equals(pUser.getPhone().getCcn(), pUserSnapshot.getPhone().getCcn())) {
            Log.i(TAG, "UpdateUser CCN ANTIGUA: " + pUserSnapshot.getPhone().getCcn());
            Log.i(TAG, "UpdateUser CCN NUEVA: " + pUser.getPhone().getCcn());
            userToUpdate.getPhone().setCcn(pUser.getPhone().getCcn());
        }
        if (!Objects.equals(pUser.getType(), pUserSnapshot.getType())) {
            Log.i(TAG, "UpdateUser TIPO ANTIGUA: " + pUserSnapshot.getType());
            Log.i(TAG, "UpdateUser TIPO NUEVA: " + pUser.getType());
            userToUpdate.setType(pUser.getType());
        }
        if (!(new HashSet<>(pUserSnapshot.getSpecialization()).containsAll(pUser.getSpecialization()) && new HashSet<>(pUser.getSpecialization()).containsAll(pUserSnapshot.getSpecialization()))) {
            Log.i("WhoDo-Log ", "UpdateUser ESPECIALIZACION ANTIGUA: " + pUserSnapshot.getSpecialization());
            Log.i(TAG, "UpdateUser ESPECIALIZACION NUEVA: " + pUser.getSpecialization());
            userToUpdate.setSpecialization(pUser.getSpecialization());
        }
        if (!Objects.equals(pUser.getProfilePicture(), pUserSnapshot.getProfilePicture())) {
            Log.i(TAG, "UpdateUser IMAGEN ANTIGUA: " + pUserSnapshot.getProfilePicture());
            Log.i(TAG, "UpdateUser IMAGEN NUEVA: " + pUser.getProfilePicture());
            userToUpdate.setProfilePicture(pUser.getProfilePicture());
        }
        Log.i(TAG, "UpdateUser : " + userToUpdate.toString());

        mUserDao.update(userToUpdate);
    }

    public void updateUserImmidiate(User pUser){
        mUserDao.update(pUser);
    }

    //HANDLING WORKORDERS
    public void createWorkOrder(WorkOrder pWorkOrder) {
        mWorkOrderDao.create(pWorkOrder, new Callback<WorkOrder>() {
            @Override
            public void onSuccess(WorkOrder workOrder) {
                //callback.onSuccess(pUser);
                Log.d(TAG, "createWorkOrder() Method --> WorkOrder has been created " + workOrder);
            }
            @Override
            public void onError(Exception e) {
                Log.d(TAG, "createWorkOrder() Method --> WorkOrder creation error " + e);
            }
        });
    }

    public void updateWorkOrder(WorkOrder pWorkOrder) {
        mWorkOrderDao.update(pWorkOrder, new Callback<WorkOrder>() {
            @Override
            public void onSuccess(WorkOrder workOrder) {
            }
            @Override
            public void onError(Exception e) {
            }
        });
    }

    public LiveData<List<WorkOrder>> getWorkOrder() {
        return mWorkOrders;
    }

    public void setPickedWorkOrder(WorkOrder pPickedWorkOrder) {
        mPickedWorkOrder.setValue(pPickedWorkOrder);
    }

    public LiveData<WorkOrder> getPickedWorkOrder() {
        return mPickedWorkOrder;
    }
    //HANDLING WORKORDERS

    //------- LOADING DATA -------//

    private void loadUserInfo(User pUser){
        mUserDao.findUser(pUser).observeForever(user -> {
            if (user != null) {
                mUser.setValue(user);
                mSnapshotUser = user;
                Log.i(TAG, "mUser: " + Objects.requireNonNull(mUser.getValue()));
                Log.i(TAG, "mSnapshotUser: " + mSnapshotUser.toString());
            }
        });
    }

    private void loadUserWorkOrders(User pUser){
        WorkOrder mWorkOrder = new WorkOrder();
        mWorkOrder.getCustomer().setCustomerId(pUser.getId());
        mWorkOrder.getProvider().setProviderId(pUser.getId());
        mWorkOrderDao.find(mWorkOrder).observeForever(workOrderList -> {
            if (workOrderList != null) {
                mWorkOrders.setValue(workOrderList);
                Log.d(TAG, "loadUserWorkOrders() --> onSuccess mWorkOrders: "+ mWorkOrders);

                if (mWorkOrders.isInitialized() && mPickedWorkOrder.isInitialized()) {
                    for (WorkOrder pWorkOrder : Objects.requireNonNull(mWorkOrders.getValue())) {
                        if (mPickedWorkOrder.getValue() != null && Objects.equals(mPickedWorkOrder.getValue().getOrderId(), pWorkOrder.getOrderId())) {
                            mPickedWorkOrder.setValue(pWorkOrder);
                        }
                    }
                }
            }
        });
    }

    private void loadUserProviders(User pUser,Double distance){
        mUserDao.findProviders(pUser,distance).observeForever(providers -> {
            if (providers != null) {
                List<User> AuxUserList = new ArrayList<>(providers);
                mProviders.setValue(AuxUserList);
                Log.i(TAG, "loadUserProviders() --> onSuccess mProviders: " + mProviders);
            }
        });
    }

    private void loadParameters(){
        mParametersDao.getParameters(new Callback<ApiResponse<List<Parameter>>>() {
            @Override
            public void onSuccess(ApiResponse<List<Parameter>> pParametersList) {
                if (pParametersList != null) {
                    mParameters.setValue(pParametersList.getData());
                    Log.d(TAG, "pParametersList -->" + pParametersList);
                }
            }
            @Override
            public void onError(Exception e) {
                Log.d(TAG, "Error -->" + e.getMessage());
            }
        });
    }

    
}
