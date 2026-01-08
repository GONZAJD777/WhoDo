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
import androidx.lifecycle.Observer;

import com.example.whodo.app.domain.paymentOrder.PaymentOrder;
import com.example.whodo.app.domain.paymentOrder.PaymentRequest;
import com.example.whodo.app.domain.paymentOrder.PaymentResponse;
import com.example.whodo.app.domain.paymentOrder.dao.Impl.PaymentOrderDaoImpl;
import com.example.whodo.app.domain.paymentOrder.dao.PaymentOrderDao;
import com.example.whodo.app.domain.user.UserFactory;
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
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class MainActivityViewModel extends AndroidViewModel implements ViewModelInterface {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String TAG = "LOGGER-MAIN-ACTIVITY-VIEWMODEL";
    private Integer mProvidersUpdateCounter;
    private Integer mProvidersUpdateTimer;
    private Double mMaxProviderDistance;
    private final List<Observer<?>> observers = new ArrayList<>();
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
    private final MutableLiveData<String> mPayment = new MutableLiveData<>();
    private MediatorLiveData<Boolean> allDataReady = new MediatorLiveData<>();
    private UserDao<User> mUserDao;
    private WorkOrderDao<WorkOrder> mWorkOrderDao;
    private PaymentOrderDao<PaymentOrder> mPaymentOrderDao;
    private final ParametersDao<Parameter> mParametersDao;
    private boolean workOrdersSubscribed = false;

    ////******************************************************************************////
    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        //private final SSEUserClient sseUserClient;
        mUserDao = new UserDaoImpl(application.getApplicationContext());
        mWorkOrderDao= new WorkOrderDaoImpl(application.getApplicationContext());
        mParametersDao= new ParametersDaoImpl(application.getApplicationContext());
        mPaymentOrderDao=new PaymentOrderDaoImpl(application.getApplicationContext());

        mSeletedTab.setValue(0);
        mTabLayoutVisibility.setValue(View.VISIBLE);
        mFragmentSelected.setValue(new HireFragment());
        mProvidersUpdateCounter=0;
        mProvidersUpdateTimer=2;

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            User currentUser = UserFactory.withAuthId(user.getUid());
            Log.d(TAG, "mAuth no es nulo y currentUser: " + currentUser);
            StartUserUpdateThread();
            try {
                initializeObservers();
                loadLoggedUser(currentUser);
                loadParameters();
                Observer<Boolean> readyObserver =ready -> {
                    if (Boolean.TRUE.equals(ready) && !workOrdersSubscribed) {
                        try {
                            String value = Utils.findParameterById(Objects.requireNonNull(mParameters.getValue()), "USR_MAX_DISTANCE").getValue();
                            mMaxProviderDistance = Double.parseDouble(value);
                            Log.d(TAG, "Distancia: " + mMaxProviderDistance);
                            loadUserProviders(mUser.getValue(), mMaxProviderDistance);
                            loadUserWorkOrders(Objects.requireNonNull(mUser.getValue()));
                            verifyAndUpdateFcmToken(mUser.getValue());
                            workOrdersSubscribed = true;
                        } catch (NumberFormatException e) {
                            Log.d(TAG, "Error al convertir el valor a Double: " + e.getMessage());
                        }
                    }
                };
                // Lo guardás en tu lista
                observers.add(readyObserver);
                // Lo registrás con observeForever
                allDataReady.observeForever(readyObserver);
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
    ////------- LOADING DATA -------////
    private void loadLoggedUser(User pUser){
        mUserDao.findUser(pUser, new Callback<User>() {
            @Override
            public void onSuccess(User user) {
            setLoggedUser(user);
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadUser() Method --> Problem trying to load loggedUser Info " + e);
            }
        });
    }
    private void loadUserProviders(User pUser,Double distance){
        mUserDao.findProviders(pUser, distance, new Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> providersList) {
                setProviders(providersList);
            }
            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadUserProviders() Method --> Problem trying to load user providers" + e);

            }
        });
    }
    private void loadUserWorkOrders(User pUser){
        WorkOrder mWorkOrder = new WorkOrder();
        mWorkOrder.getCustomer().setCustomerId(pUser.getId());
        mWorkOrder.getProvider().setProviderId(pUser.getId());
        mWorkOrderDao.find(this, mWorkOrder);
        mWorkOrderDao.findByUserID(mWorkOrder, new Callback<List<WorkOrder>>() {
            @Override
            public void onSuccess(List<WorkOrder> workOrderList) {
                Log.d(TAG, "loadUserWorkOrders() --> onSuccess mWorkOrders: "+ mWorkOrders);
                mWorkOrders.setValue(workOrderList);
            }
            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadUserWorkOrders() Method --> Problem trying to load user work orders" + e);
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
    ////------- LOADING DATA -------////

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
    private void StartUserUpdateThread() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Log.i(TAG, "mUser : " + Objects.requireNonNull(mUser.getValue()).toString());
                    Log.i(TAG, "mSnapshotUser : " + mSnapshotUser.toString());
                    updateUser(Objects.requireNonNull(mUser.getValue()), Objects.requireNonNull(mSnapshotUser));
                    if(Objects.equals(mProvidersUpdateCounter, mProvidersUpdateTimer)){
                        loadUserProviders(mUser.getValue(),mMaxProviderDistance);
                    }
                } catch (Exception e) {
                    Log.i(TAG, "StartUserUpdateThread --> BackgroundUpdateUser.UpdateUser(): " + e);
                } finally {
                    mProvidersUpdateCounter=(mProvidersUpdateCounter+1)%(mProvidersUpdateTimer+1);
                    handler.postDelayed(this, 60000);
                }
            }
        }).start();
    }
    public void updateLoggedUser(User pUser) {
        this.mUser.setValue(pUser);
    }
    public void updateUserImmidiate(User pUser){
        mUserDao.update(pUser, new Callback<User>() {
            @Override
            public void onSuccess(User pUser) {
                Log.i(TAG, "Logged User FMC token Updated in DB");
            }
            @Override
            public void onError(Exception e) {
                Log.i(TAG, "Error updating FMC token in DB: " +e);

            }
        });
    }
    ////HANDLING LOGGED USER////
    @Override
    public LiveData<User> getLoggedUser() {
        return this.mUser;
    }
    @Override
    public void setLoggedUser(User pUser) {
        if (pUser != null) {
            mUser.setValue(pUser);
            mSnapshotUser = pUser;
            Log.i(TAG, "mUser: " + Objects.requireNonNull(mUser.getValue()));
            Log.i(TAG, "mSnapshotUser: " + mSnapshotUser.toString());
        }
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
        if (!Objects.equals(pUserSnapshot.getBirthday(), pUser.getBirthday())) {
            Log.i(TAG, "UpdateUser CUMPLEAÑOS ANTIGUA: " + pUserSnapshot.getBirthday());
            Log.i(TAG, "UpdateUser CUMPLEAÑOS NUEVA: " + pUser.getBirthday());
            userToUpdate.setBirthday(pUser.getBirthday());
        }
        if (!Objects.equals(pUserSnapshot.getDescription(), pUser.getDescription())) {
            Log.i(TAG, "UpdateUser DESCRIPCION ANTIGUA: " + pUserSnapshot.getDescription());
            Log.i(TAG, "UpdateUser DESCRIPCION NUEVA: " + pUser.getDescription());
            userToUpdate.setDescription(pUser.getDescription());
        }
        if (!Objects.equals(pUserSnapshot.getEmail(), pUser.getEmail())) {
            Log.i(TAG, "UpdateUser CORREO ANTIGUA: " + pUserSnapshot.getEmail());
            Log.i(TAG, "UpdateUser CORREO NUEVA: " + pUser.getEmail());
            userToUpdate.setEmail(pUser.getEmail());
        }

        if (!(new HashSet<>(pUser.getLanguages()).containsAll(pUserSnapshot.getLanguages()) &&
                new HashSet<>(pUserSnapshot.getLanguages()).containsAll(pUser.getLanguages()))) {
            Log.i(TAG, "UpdateUser IDIOMAS ANTIGUA: " + pUserSnapshot.getLanguages());
            Log.i(TAG, "UpdateUser IDIOMAS NUEVA: " + pUser.getLanguages());
            userToUpdate.setLanguages(pUser.getLanguages());
        }
        if (!Objects.equals(pUserSnapshot.getLocation().getLatitude(), pUser.getLocation().getLatitude()) ||
                !Objects.equals(pUserSnapshot.getLocation().getLongitude(), pUser.getLocation().getLongitude())) {
            Log.i(TAG, "UpdateUser LATITUD ANTIGUA: " + pUserSnapshot.getLocation().getLatitude());
            Log.i(TAG, "UpdateUser LATITUD NUEVA: " + pUser.getLocation().getLatitude());
            Log.i(TAG, "UpdateUser LONGITUD ANTIGUA: " + pUserSnapshot.getLocation().getLongitude());
            Log.i(TAG, "UpdateUser LONGITUD NUEVA: " + pUser.getLocation().getLongitude());
            userToUpdate.getLocation().setLatitude(pUser.getLocation().getLatitude());
            userToUpdate.getLocation().setLongitude(pUser.getLocation().getLongitude());
        }
        if (!Objects.equals(pUserSnapshot.getName(), pUser.getName())) {
            Log.i(TAG, "UpdateUser NOMBRE ANTIGUA: " + pUserSnapshot.getName());
            Log.i(TAG, "UpdateUser NOMBRE NUEVA: " + pUser.getName());
            userToUpdate.setName(pUser.getName());
        }
        if (!Objects.equals(pUserSnapshot.getPassword(), pUser.getPassword())) {
            Log.i(TAG, "UpdateUser PASSWORD ANTIGUA: " + pUserSnapshot.getPassword());
            Log.i(TAG, "UpdateUser PASSWORD NUEVA: " + pUser.getPassword());
            userToUpdate.setPassword(pUser.getPassword());
        }
        if (!Objects.equals(pUserSnapshot.getPhone().getNumber(), pUser.getPhone().getNumber())) {
            Log.i(TAG, "UpdateUser TELEFONO ANTIGUA: " + pUserSnapshot.getPhone().getNumber());
            Log.i(TAG, "UpdateUser TELEFONO NUEVA: " + pUser.getPhone().getNumber());
            userToUpdate.getPhone().setNumber(pUser.getPhone().getNumber());
        }
        if (!Objects.equals(pUserSnapshot.getPhone().getCcn(), pUser.getPhone().getCcn())) {
            Log.i(TAG, "UpdateUser CCN ANTIGUA: " + pUserSnapshot.getPhone().getCcn());
            Log.i(TAG, "UpdateUser CCN NUEVA: " + pUser.getPhone().getCcn());
            userToUpdate.getPhone().setCcn(pUser.getPhone().getCcn());
        }
        if (!Objects.equals(pUserSnapshot.getType(), pUser.getType())) {
            Log.i(TAG, "UpdateUser TIPO ANTIGUA: " + pUserSnapshot.getType());
            Log.i(TAG, "UpdateUser TIPO NUEVA: " + pUser.getType());
            userToUpdate.setType(pUser.getType());
        }
        if (!(new HashSet<>(pUser.getSpecialization()).containsAll(pUserSnapshot.getSpecialization()) &&
                new HashSet<>(pUserSnapshot.getSpecialization()).containsAll(pUser.getSpecialization()))) {
            Log.i("WhoDo-Log ", "UpdateUser ESPECIALIZACION ANTIGUA: " + pUserSnapshot.getSpecialization());
            Log.i(TAG, "UpdateUser ESPECIALIZACION NUEVA: " + pUser.getSpecialization());
            userToUpdate.setSpecialization(pUser.getSpecialization());
        }
        if (!Objects.equals(pUserSnapshot.getProfilePicture(), pUser.getProfilePicture())) {
            Log.i(TAG, "UpdateUser IMAGEN ANTIGUA: " + pUserSnapshot.getProfilePicture());
            Log.i(TAG, "UpdateUser IMAGEN NUEVA: " + pUser.getProfilePicture());
            userToUpdate.setProfilePicture(pUser.getProfilePicture());
        }

        Log.i(TAG, "UpdateUser : " + userToUpdate.toString());

        mUserDao.update(userToUpdate, new Callback<User>() {
            @Override
            public void onSuccess(User user) {
                //Log.d(TAG, "updateUser() Method --> This is the image url: " + user.getProfilePicture());
                mSnapshotUser=user;
                // como el objeto mUser es el que actualiza la interfaz, y la uri de la imagen aun no se define sino que la direccion es la del storage
                // una vez actualizado el usuario, y obtenida la uri de la imagen subida al storage debemos actualizarla en en objeto para que no este indefinidamente
                // intentando actualizar, de lo contrario siempre esta comparando las strings de uri y siempre seran diferentes.
                Objects.requireNonNull(mUser.getValue()).setProfilePicture(user.getProfilePicture());
            }
            @Override
            public void onError(Exception e) {
                Log.d(TAG, "updateUser() Method --> User couldn't been updated " + e);
            }
        });
    }
    public void verifyAndUpdateFcmToken(User user) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String currentToken = task.getResult();
                    if (currentToken != null && !currentToken.equals(user.getFcmToken())) {
                        // Llamar al repositorio para actualizar en backend
                        mUserDao.updateFcmToken(UserFactory.withAuthIdAndFcmToken(user.getAuthId(),currentToken));
                    }
                });
    }

    ////HANDLING LOGGED USER////

    ////HANDLING PROVIDERS////
    @Override
    public LiveData<List<User>> getProviders() {
        return this.mProviders;
    }
    @Override
    public void setProviders (List<User> pProvidersList) {
        if (pProvidersList != null) {
            mProviders.setValue(pProvidersList);
            Log.i(TAG, "loadUserProviders() --> onSuccess mProviders: " + mProviders);
        }
    }
    ////HANDLING PROVIDERS////

    ////HANDLING WORKORDERS////
    @Override
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
    @Override
    public void updateWorkOrder(WorkOrder pWorkOrder) {
        mWorkOrderDao.update(pWorkOrder, new Callback<WorkOrder>() {
            @Override
            public void onSuccess(WorkOrder workOrder) {
                Log.d(TAG, "Se actualizo la orden de trabajo: " + workOrder.toString() );
            }
            @Override
            public void onError(Exception e) {
                Log.d(TAG, "Error al actualizar la orden de trabajo: " + e.toString() );
            }
        });
    }
    @Override
    public LiveData<List<WorkOrder>> getWorkOrder() {
        return mWorkOrders;
    }
    @Override
    public void setWorkOrder(List<WorkOrder> pWorkOrdersList) {
        if (pWorkOrdersList != null) {
            mWorkOrders.setValue(pWorkOrdersList);
            Log.d(TAG, "loadUserWorkOrders() --> onSuccess mWorkOrders: "+ mWorkOrders);
            if (mWorkOrders.isInitialized() && mPickedWorkOrder.isInitialized()) {
                for (WorkOrder pWorkOrder : Objects.requireNonNull(mWorkOrders.getValue())) {
                    if (mPickedWorkOrder.getValue() != null && Objects.equals(mPickedWorkOrder.getValue().getOrderId(), pWorkOrder.getOrderId())) {
                        mPickedWorkOrder.setValue(pWorkOrder);
                    }
                }
            }
        }
    }
    public void setPickedWorkOrder(WorkOrder pPickedWorkOrder) {
        mPickedWorkOrder.setValue(pPickedWorkOrder);
    }
    public LiveData<WorkOrder> getPickedWorkOrder() {
        return mPickedWorkOrder;
    }
    ////HANDLING WORKORDERS////

    ////HANDLING PAYMENTORDERS////
    public void createPayment(PaymentRequest pPaymentRequest) {
        mPaymentOrderDao.createPayment(pPaymentRequest, new Callback<>() {
            @Override
            public void onSuccess(PaymentResponse pPaymentUrl) {
                setPayment(pPaymentUrl.getUrl());
                Log.d(TAG, "createPayment() Method --> Payment has been created " + pPaymentUrl);
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "createPayment() Method --> Payment creation error " + e);
            }
        });
    }
    public void setPayment (String pPaymentUrl){
        mPayment.setValue(pPaymentUrl);
    }
    public LiveData<String> getPayment (){
        return mPayment;
    }
    ////HANDLING PAYMENTORDERS////

    @Override
    public void onCleared() {
        super.onCleared();
        mWorkOrderDao.closeConnection();
        mUserDao.closeConnection();
        Log.d(TAG, "se cierran las conexiones de SSE clients");
    }
}
