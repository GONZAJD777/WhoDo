package com.example.whodo.domain.workOrder.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.aplication.Callback;
import com.example.whodo.domain.user.UserDTO;
import com.example.whodo.domain.workOrder.WorkOrder;
import com.example.whodo.domain.workOrder.WorkOrderDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirebaseWorkOrderDAO implements WorkOrderDao<WorkOrderDTO>{
    private final String TAG="FirebaseWorkOrderDao";
    //DATABASE REF
    private final FirebaseDatabase mDatabaseInstance = FirebaseDatabase.getInstance();
    private final DatabaseReference mDatabaseReference = mDatabaseInstance.getReference();
    private final DatabaseReference WorkOrderDBRef = mDatabaseReference.child("WORK_ORDERS");
    //IMAGES STORAGE REF
    //private final FirebaseStorage mStorageInstance = FirebaseStorage.getInstance();
    //private final StorageReference mStorageReference = mStorageInstance.getReference();
    //private final StorageReference mImageStorageRef = mStorageReference.child("WHODO-IMAGES");

    @Override
    public LiveData<List<WorkOrderDTO>> find(WorkOrderDTO pWorkOrderDTO) {

        MutableLiveData<List<WorkOrderDTO>> mWorkOrderDTO = new MutableLiveData<>();
        List<WorkOrderDTO> mProviderIdWorkOrders = new ArrayList<>();
        List<WorkOrderDTO> mCustomerIdWorkOrders = new ArrayList<>();
        List<WorkOrderDTO> mAllWorkOrders = new ArrayList<>();
        Log.i(TAG,  "find --> String recibido como parametro: " + pWorkOrderDTO);

        ValueEventListener mCustomerIdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCustomerIdWorkOrders.clear();
                mAllWorkOrders.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        WorkOrderDTO auxWorkOrder=ds.getValue(WorkOrderDTO.class);
                        mCustomerIdWorkOrders.add(auxWorkOrder);
                        assert auxWorkOrder != null;
                        Log.i(TAG,  "find:onDataChange -->" + auxWorkOrder.getOrderId());
                    } catch (Exception e) {
                        Log.i(TAG,  "Exception Mapping Snapshot to UserDTO: " + e);
                    }
                }
                mAllWorkOrders.addAll(mProviderIdWorkOrders);
                mAllWorkOrders.addAll(mCustomerIdWorkOrders);
                mWorkOrderDTO.setValue(mAllWorkOrders);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "findOne:onCancelled -->" + databaseError.toException());
            }
        };
        ValueEventListener mProviderIdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProviderIdWorkOrders.clear();
                mAllWorkOrders.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        WorkOrderDTO auxWorkOrder=ds.getValue(WorkOrderDTO.class);
                        mProviderIdWorkOrders.add(auxWorkOrder);
                        assert auxWorkOrder != null;
                        Log.i(TAG,  "find:onDataChange -->" + auxWorkOrder.getOrderId());
                    } catch (Exception e) {
                        Log.i(TAG,  "Exception Mapping Snapshot to UserDTO: " + e);
                    }
                }
                mAllWorkOrders.addAll(mProviderIdWorkOrders);
                mAllWorkOrders.addAll(mCustomerIdWorkOrders);
                mWorkOrderDTO.setValue(mAllWorkOrders);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "findOne:onCancelled -->" + databaseError.toException());
            }
        };
        WorkOrderDBRef.orderByChild("customerId").equalTo(pWorkOrderDTO.getCustomerId()).addValueEventListener(mCustomerIdListener);
        WorkOrderDBRef.orderByChild("providerId").equalTo(pWorkOrderDTO.getProviderId()).addValueEventListener(mProviderIdListener);

        return mWorkOrderDTO;

    }

    @Override
    public void create(WorkOrderDTO workOrderDTO, Callback<WorkOrderDTO> callback) {
        DatabaseReference createObject = WorkOrderDBRef.push();
        workOrderDTO.setOrderId(createObject.getKey());
        WorkOrderDBRef.child(Objects.requireNonNull(createObject.getKey())).setValue(workOrderDTO).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // La operación se completó con éxito
                Log.w(TAG, "create:isSuccesful -->" +createObject.getKey());
                callback.onSuccess(workOrderDTO);
            } else {
                // Hubo un error al establecer el valor
                System.out.println("Error al establecer el valor: " + task.getException());
                //callback.onError(task.getException());
            }
        });
    }

    @Override
    public void update(WorkOrderDTO workOrderDTO) {

    }

}
