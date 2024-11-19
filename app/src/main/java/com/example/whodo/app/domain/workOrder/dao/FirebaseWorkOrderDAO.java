package com.example.whodo.app.domain.workOrder.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.workOrder.WorkOrderDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void update(WorkOrderDTO workOrderDTO, Callback<WorkOrderDTO> callback) {
        Map<String, Object> updates = new HashMap<>();

        if (workOrderDTO.getCustomerId() != null) {
            updates.put("customerId", workOrderDTO.getCustomerId());
        }
        if (workOrderDTO.getCustomerName() != null) {
            updates.put("customerName", workOrderDTO.getCustomerName());
        }
        if (workOrderDTO.getCustomerAddress() != null) {
            updates.put("customerAddress", workOrderDTO.getCustomerAddress());
        }
        if (workOrderDTO.getCustomerLat() != 0) {
            updates.put("customerLat", workOrderDTO.getCustomerLat());
        }
        if (workOrderDTO.getCustomerLng() != 0) {
            updates.put("customerLng", workOrderDTO.getCustomerLng());
        }
        if (workOrderDTO.getCustomerPhoneNumber() != null) {
            updates.put("customerPhoneNumber", workOrderDTO.getCustomerPhoneNumber());
        }
        //*********************************************************************//
        if (workOrderDTO.getProviderId() != null) {
            updates.put("providerId", workOrderDTO.getProviderId());
        }
        if (workOrderDTO.getProviderName() != null) {
            updates.put("providerName", workOrderDTO.getProviderName());
        }
        if (workOrderDTO.getProviderAddress() != null) {
            updates.put("providerAddress", workOrderDTO.getProviderAddress());
        }
        if (workOrderDTO.getProviderLat() != 0) {
            updates.put("providerLat", workOrderDTO.getProviderLat());
        }
        if (workOrderDTO.getProviderLng() != 0) {
            updates.put("providerLng", workOrderDTO.getProviderLng());
        }
        if (workOrderDTO.getProviderPhoneNumber() != null) {
            updates.put("providerPhoneNumber", workOrderDTO.getProviderPhoneNumber());
        }
        //*********************************************************************//
        if (workOrderDTO.getSpecialization() != null) {
            updates.put("specialization", workOrderDTO.getSpecialization());
        }
        if (workOrderDTO.getDescription() != null) {
            updates.put("description", workOrderDTO.getDescription());
        }
        if (workOrderDTO.getDetail() != null) {
            updates.put("detail", workOrderDTO.getDetail());
        }
        if (workOrderDTO.getCreationDate() != null) {
            updates.put("creationDate", workOrderDTO.getCreationDate());
        }
        if (workOrderDTO.getTimeLimit() != null) {
            updates.put("timeLimit", workOrderDTO.getTimeLimit());
        }
        if (workOrderDTO.getState() != null) {
            updates.put("state", workOrderDTO.getState());
        }
        if (workOrderDTO.getStateChangeDate() != null) {
            updates.put("stateChangeDate", workOrderDTO.getStateChangeDate());
        }
        //*********************************************************************//
        if (workOrderDTO.getInspectionDate() != null) {
            updates.put("inspectionDate", workOrderDTO.getInspectionDate());
        }
        if (workOrderDTO.getInspectionCharges() != null) {
            updates.put("inspectionCharges", workOrderDTO.getInspectionCharges());
        }
        if (workOrderDTO.getInspectionFee() != null) {
            updates.put("inspectionFee", workOrderDTO.getInspectionFee());
        }
        if (workOrderDTO.getInspectionPaymentOrder() != null) {
            updates.put("inspectionPaymentOrder", workOrderDTO.getInspectionPaymentOrder());
        }
        if (workOrderDTO.getInspectionFullfilment() != null) {
            updates.put("inspectionFullfilment", workOrderDTO.getInspectionFullfilment());
        }
        if (workOrderDTO.getInspectionRescheduled() != null) {
            updates.put("inspectionRescheduled", workOrderDTO.getInspectionRescheduled());
        }
        //*********************************************************************//

        if (workOrderDTO.getWorkStartDate() != null) {
            updates.put("workStartDate", workOrderDTO.getWorkStartDate());
        }
        if (workOrderDTO.getWorkEndDate() != null) {
            updates.put("workEndDate", workOrderDTO.getWorkEndDate());
        }
        if (workOrderDTO.getWorkLaborCost() != null) {
            updates.put("workLaborCost", workOrderDTO.getWorkLaborCost());
        }
        if (workOrderDTO.getWorkMaterialsCost() != null) {
            updates.put("workMaterialsCost", workOrderDTO.getWorkMaterialsCost());
        }
        if (workOrderDTO.getWorkFee() != null) {
            updates.put("workFee", workOrderDTO.getWorkFee());
        }
        if (workOrderDTO.getWorkPaymentOrder() != null) {
            updates.put("workPaymentOrder", workOrderDTO.getWorkPaymentOrder());
        }
        if (workOrderDTO.getWorkWarrantyEndDate() != null) {
            updates.put("workWarrantyEndDate", workOrderDTO.getWorkWarrantyEndDate());
        }
        if (workOrderDTO.getWorkLimitTimeExtension() != null) {
            updates.put("workLimitTimeExtension", workOrderDTO.getWorkLimitTimeExtension());
        }
        //*********************************************************************//
        if (workOrderDTO.getImpressions() != null) {
            updates.put("impressions", workOrderDTO.getImpressions());
        }
        if (workOrderDTO.getAppereanceScore() != null) {
            updates.put("appereanceScore", workOrderDTO.getAppereanceScore());
        }
        if (workOrderDTO.getCleanlinessScore() != null) {
            updates.put("cleanlinessScore", workOrderDTO.getCleanlinessScore());
        }
        if (workOrderDTO.getSpeedScore() != null) {
            updates.put("speedScore", workOrderDTO.getSpeedScore());
        }
        if (workOrderDTO.getQualityScore() != null) {
            updates.put("qualityScore", workOrderDTO.getQualityScore());
        }
        WorkOrderDBRef.child(workOrderDTO.getOrderId()).updateChildren(updates).addOnSuccessListener(aVoid -> {
                    Log.i(TAG, " update() operation --> WorkOrder Update Success");
                })
                .addOnFailureListener(e -> {
                    Log.i(TAG, " update() operation --> WorkOrder Update Failed: " + e);
                });
    }

}
