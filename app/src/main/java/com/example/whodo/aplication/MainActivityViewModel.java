package com.example.whodo.aplication;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.whodo.domain.user.dao.UserDao;
import com.example.whodo.domain.user.UserDTO;
import com.example.whodo.domain.user.UserMapper;
import com.example.whodo.domain.workOrder.WorkOrder;
import com.example.whodo.domain.workOrder.WorkOrderDTO;
import com.example.whodo.domain.workOrder.WorkOrderMapper;
import com.example.whodo.domain.workOrder.dao.FirebaseWorkOrderDAO;
import com.example.whodo.domain.workOrder.dao.WorkOrderDao;
import com.example.whodo.features.favorites.MainFavoritesFragment;
import com.example.whodo.features.hire.MainHireFragment;
import com.example.whodo.features.hire.WorkOrderFragment;
import com.example.whodo.features.messages.MainMessagesFragment;
import com.example.whodo.features.profile.MainProfileFragment;
import com.example.whodo.domain.user.User;
import com.example.whodo.features.activity.MainActivityFragment;
import com.example.whodo.features.profile.fragments.CommentsFragment;
import com.example.whodo.features.profile.fragments.EditProfileFragment;
import com.example.whodo.features.profile.fragments.LegalTermsFragment;
import com.example.whodo.features.profile.fragments.PersonalInfoFragment;
import com.example.whodo.features.profile.fragments.PrivacyPoliticsFragment;
import com.example.whodo.features.profile.fragments.ProviderModeFragment;
import com.example.whodo.features.profile.fragments.RecomendationsFragment;
import com.example.whodo.features.profile.fragments.SecurityFragment;
import com.example.whodo.features.profile.fragments.SupportFragment;
import com.example.whodo.features.profile.fragments.TutorialFragment;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivityViewModel extends ViewModel {

    private final String TAG1="MAIN-ACTIVITY-VIEWMODEL";
    private UserDao<UserDTO> mUserDao ;
    private User mSnapshotUser; // No es LiveData
    private final MutableLiveData<User> mUser = new MutableLiveData<>();
    private final MutableLiveData<List<User>> mProviders = new MutableLiveData<>();
    private final MutableLiveData<List<WorkOrder>> mWorkOrders = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> mServices = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> mLanguages= new MutableLiveData<>();
    private final MutableLiveData<Fragment> mFragmentSelected = new MutableLiveData<>(new MainHireFragment());
    private final MutableLiveData<Integer> mFragmentVisibility = new MutableLiveData<>(View.VISIBLE);

    private final MutableLiveData <WorkOrder> mPickedWorkOrder = new MutableLiveData<>();
    private final WorkOrderDao<WorkOrderDTO> mWorkOrderDao;


    public MainActivityViewModel(UserDao<UserDTO> pUserDao) {
        mUserDao=pUserDao;
        mWorkOrderDao = new FirebaseWorkOrderDAO();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        User currentUser = new User(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        mUserDao.findOne(UserMapper.toDTO(currentUser)).observeForever(userDto -> {
            if (userDto != null) {
                User user = UserMapper.toEntity(userDto);
                mUser.setValue(user);
                mSnapshotUser = new User(user);
                //*************************** PROVIDERS ***************************//
                mUserDao.findProviders(UserMapper.toDTO(mSnapshotUser), new Callback<List<UserDTO>>() {
                    @Override
                    public void onSuccess(List<UserDTO> userDTOList) {
                        if (userDTOList != null) {
                            List<User> AuxUserList = new ArrayList<>(); // Crear una nueva lista para almacenar los objetos User

                            for (UserDTO userDTO : userDTOList) {
                                User user = UserMapper.toEntity(userDTO); // Mapear UserDTO a User
                                AuxUserList.add(user); // Agregar el objeto User a la lista
                            }
                            mProviders.setValue(AuxUserList);
                        }
                    }
                    @Override
                    public void onError(Exception e) { }
                });
                //*************************** PROVIDERS ***************************//

                //*************************** WORKORDERS ***************************//
                WorkOrder mWorkOrder = new WorkOrder();
                mWorkOrder.setCustomerId(user.getUid());
                mWorkOrder.setProviderId(user.getUid());
                mWorkOrderDao.find(WorkOrderMapper.toDto(mWorkOrder)).observeForever( workOrderDTOList -> {
                    if (workOrderDTOList != null) {
                        List<WorkOrder> AuxWorkOrderList = new ArrayList<>(); // Crear una nueva lista para almacenar los objetos User

                        for (WorkOrderDTO workOrderDTO : workOrderDTOList) {
                            WorkOrder workOrder = WorkOrderMapper.toEntity(workOrderDTO); // Mapear UserDTO a User
                            AuxWorkOrderList.add(workOrder); // Agregar el objeto User a la lista
                        }
                        mWorkOrders.setValue(AuxWorkOrderList);
                    }
                });
                //*************************** WORKORDERS ***************************//
            }
        });
        mUserDao.findLanguages(new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> pLanguages) { mLanguages.setValue((ArrayList<String>) pLanguages); }
            @Override
            public void onError(Exception e) { }
        });
        mUserDao.findServices(new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> pServices) { mServices.setValue((ArrayList<String>) pServices); }
            @Override
            public void onError(Exception e) { }
        });

        StartUserUpdateThread();
    }
    public LiveData<User> getLoggedUser() { return this.mUser; }
    public LiveData<List<User>> getProviders() { return this.mProviders; }
    public LiveData<ArrayList<String>> getServices() { return this.mServices; }
    public LiveData<ArrayList<String>> getLanguages() { return this.mLanguages; }
    public LiveData<Fragment> getSelectedFragment() { return mFragmentSelected; }
    public LiveData<Integer> getFragmentVisibility() { return mFragmentVisibility; }
    //**************************************************************
    public void setSelectedFragment (int pTab,int pVisibility){
        switch (pTab)
        {
            case 0:  mFragmentSelected.setValue(new MainHireFragment()); break;
            case 1:  mFragmentSelected.setValue(new MainFavoritesFragment()); break;
            case 2:  mFragmentSelected.setValue(new MainActivityFragment()); break;
            case 3:  mFragmentSelected.setValue(new MainMessagesFragment()); break;
            case 4:  mFragmentSelected.setValue(new MainProfileFragment()); break;
            case 5:  mFragmentSelected.setValue(new EditProfileFragment()); break;
            case 6:  mFragmentSelected.setValue(new PersonalInfoFragment()); break;
            case 7:  mFragmentSelected.setValue(new SecurityFragment()); break;
            case 8:  mFragmentSelected.setValue(new ProviderModeFragment()); break;
            case 9:  mFragmentSelected.setValue(new TutorialFragment()); break;
            case 10:  mFragmentSelected.setValue(new RecomendationsFragment()); break;
            case 11:  mFragmentSelected.setValue(new SupportFragment()); break;
            case 12:  mFragmentSelected.setValue(new CommentsFragment()); break;
            case 13:  mFragmentSelected.setValue(new LegalTermsFragment()); break;
            case 14:  mFragmentSelected.setValue(new PrivacyPoliticsFragment()); break;
            case 15:  mFragmentSelected.setValue(new WorkOrderFragment()); break;
        }
        mFragmentVisibility.setValue(pVisibility);
    }
    public void updateLoggedUser(User pUser){ this.mUser.setValue(pUser); }
    private void StartUserUpdateThread () {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                try{
                    updateUser(Objects.requireNonNull(mUser.getValue()), Objects.requireNonNull(mSnapshotUser));
                }
                catch (Exception e)
                {
                    Log.i(TAG1, "StartUserUpdateThread --> BackgroundUpdateUser.UpdateUser(): "+ e );
                }
                finally {
                    handler.postDelayed(this, 60000);
                }
            }
        }).start();
    }
    private void updateUser(User pUser,User pUserSnapshot){
        Log.i(TAG1,  "Operacion CRUD.UpdateUser() Se actualizara el usuario:" + "USERS/"+pUser.getUid());
        User userToUpdate = new User(pUser.getUid());
        if (!Objects.equals(pUser.getAddress(), pUserSnapshot.getAddress())) {
            Log.i(TAG1,"UpdateUser DIRECCION ANTIGUA: " + pUserSnapshot.getAddress() );
            Log.i(TAG1,"UpdateUser DIRECCION NUEVA: "+ pUser.getAddress());
            userToUpdate.setAddress(pUser.getAddress());
        }
        if (!Objects.equals(pUser.getBirthday(), pUserSnapshot.getBirthday())) {
            Log.i(TAG1,"UpdateUser CUMPLEAÑOS ANTIGUA: "+pUserSnapshot.getBirthday() );
            Log.i(TAG1,"UpdateUser CUMPLEAÑOS NUEVA: "+ pUser.getBirthday());
            userToUpdate.setBirthday(pUser.getBirthday());
        }
        if (!Objects.equals(pUser.getDescription(), pUserSnapshot.getDescription())) {
            Log.i(TAG1,"UpdateUser DESCRIPCION ANTIGUA: "+pUserSnapshot.getDescription() );
            Log.i(TAG1,"UpdateUser DESCRIPCION NUEVA: "+ pUser.getDescription());
            userToUpdate.setDescription(pUser.getDescription());
        }
        if (!Objects.equals(pUser.getEmail(), pUserSnapshot.getEmail())) {
            Log.i(TAG1,"UpdateUser CORREO ANTIGUA: "+pUserSnapshot.getEmail() );
            Log.i(TAG1,"UpdateUser CORREO NUEVA: "+ pUser.getEmail());
            userToUpdate.setEmail(pUser.getEmail());
        }
        if (!Objects.equals(pUser.getIsValidated(), pUserSnapshot.getIsValidated())) {
            Log.i(TAG1,"UpdateUser iSVALIDATED ANTIGUA: "+pUserSnapshot.getIsValidated() );
            Log.i(TAG1,"UpdateUser iSVALIDATED NUEVA: "+ pUser.getIsValidated());
            userToUpdate.setIsValidated(pUser.getIsValidated());
        }
        if (!Objects.equals(pUser.getLanguages(), pUserSnapshot.getLanguages())) {
            Log.i(TAG1,"UpdateUser IDIOMAS ANTIGUA: "+pUserSnapshot.getLanguages() );
            Log.i(TAG1,"UpdateUser IDIOMAS NUEVA: "+ pUser.getLanguages());
            userToUpdate.setLanguages(pUser.getLanguages());
        }
        if (!Objects.equals(pUser.getLatitude(), pUserSnapshot.getLatitude()) || !Objects.equals(pUser.getLongitude(), pUserSnapshot.getLongitude()) ) {
            Log.i(TAG1,"UpdateUser LATITUD ANTIGUA: " + pUserSnapshot.getLatitude() );
            Log.i(TAG1,"UpdateUser LATITUD NUEVA: "+ pUser.getLatitude());
            Log.i(TAG1,"UpdateUser LONGITUD ANTIGUA: " + pUserSnapshot.getLongitude() );
            Log.i(TAG1,"UpdateUser LONGITUD NUEVA: " + pUser.getLongitude());
            userToUpdate.setLatitude(pUser.getLatitude());
            userToUpdate.setLongitude(pUser.getLongitude());
        }
        if (!Objects.equals(pUser.getName(), pUserSnapshot.getName())) {
            Log.i(TAG1,"UpdateUser NOMBRE ANTIGUA: "+pUserSnapshot.getName() );
            Log.i(TAG1,"UpdateUser NOMBRE NUEVA: "+ pUser.getName());
            userToUpdate.setName(pUser.getName());
        }
        if (!Objects.equals(pUser.getPassword(), pUserSnapshot.getPassword())) {
            Log.i(TAG1,"UpdateUser PASSWORD ANTIGUA: "+pUserSnapshot.getPassword() );
            Log.i(TAG1,"UpdateUser PASSWORD NUEVA: "+ pUser.getPassword());
            userToUpdate.setPassword(pUser.getPassword());
        }
        if (!Objects.equals(pUser.getPhone(), pUserSnapshot.getPhone())) {
            Log.i(TAG1,"UpdateUser TELEFONO ANTIGUA: "+pUserSnapshot.getPhone() );
            Log.i(TAG1,"UpdateUser TELEFONO NUEVA: "+ pUser.getPhone());
            userToUpdate.setPhone(pUser.getPhone());
        }
        if (!Objects.equals(pUser.getPhone_ccn(), pUserSnapshot.getPhone_ccn())) {
            Log.i(TAG1,"UpdateUser CCN ANTIGUA: "+pUserSnapshot.getPhone_ccn() );
            Log.i(TAG1,"UpdateUser CCN NUEVA: "+ pUser.getPhone_ccn());
            userToUpdate.setPhone_ccn(pUser.getPhone_ccn());
        }
        if (!Objects.equals(pUser.getType(), pUserSnapshot.getType())) {
            Log.i(TAG1, "UpdateUser TIPO ANTIGUA: "+pUserSnapshot.getType() );
            Log.i(TAG1,"UpdateUser TIPO NUEVA: "+ pUser.getType());
            userToUpdate.setType(pUser.getType());
        }
        if (!Objects.equals(pUser.getSpecialization(), pUserSnapshot.getSpecialization())) {
            Log.i("WhoDo-Log ", "UpdateUser ESPECIALIZACION ANTIGUA: "+pUserSnapshot.getSpecialization() );
            Log.i(TAG1,"UpdateUser ESPECIALIZACION NUEVA: "+ pUser.getSpecialization());
            userToUpdate.setSpecialization(pUser.getSpecialization());
        }
        if (!Objects.equals(pUser.getProfilePicture(), pUserSnapshot.getProfilePicture())) {
            Log.i(TAG1,"UpdateUser IMAGEN ANTIGUA: "+pUserSnapshot.getProfilePicture() );
            Log.i(TAG1,"UpdateUser IMAGEN NUEVA: "+ pUser.getProfilePicture());
            userToUpdate.setProfilePicture(pUser.getProfilePicture());
        }
        mUserDao.update(UserMapper.toDTO(userToUpdate));
    }

    //HANDLING WORKORDERS
    public void setPickedWorkOrder(WorkOrder pPickedWorkOrder){ mPickedWorkOrder.setValue(pPickedWorkOrder); }
    public LiveData<WorkOrder> getPickedWorkOrder(){ return mPickedWorkOrder; }
    public void setWorkOrder (WorkOrder pWorkOrder) {
        mWorkOrderDao.create(WorkOrderMapper.toDto(pWorkOrder), new Callback<WorkOrderDTO>() {
            @Override
            public void onSuccess(WorkOrderDTO workOrderDTO) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public LiveData<List<WorkOrder>> getWorkOrder () {return mWorkOrders;}
    public User getCustomerWorkOrder (String pCustomerId){
        User mProvider = new User();
       for (User provider : Objects.requireNonNull(mProviders.getValue())) {
           if (pCustomerId==provider.getUid()) mProvider=provider;
       }
       return mProvider;
    }

}
