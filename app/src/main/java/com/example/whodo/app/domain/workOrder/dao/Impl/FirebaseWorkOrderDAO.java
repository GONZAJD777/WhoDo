package com.example.whodo.app.domain.workOrder.dao.Impl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.domain.workOrder.dao.WorkOrderDao;
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

public class FirebaseWorkOrderDAO implements WorkOrderDao<WorkOrder> {
    private final String TAG="FirebaseWorkOrderDao";
    //DATABASE REF
    private final FirebaseDatabase mDatabaseInstance = FirebaseDatabase.getInstance();
    private final DatabaseReference mDatabaseReference = mDatabaseInstance.getReference();
    private final DatabaseReference WorkOrderDBRef = mDatabaseReference.child("WORK_ORDERS");

    @Override
    public LiveData<List<WorkOrder>> find(WorkOrder pWorkOrder) {

        MutableLiveData<List<WorkOrder>> mWorkOrderDTO = new MutableLiveData<>();
        List<WorkOrder> mProviderIdWorkOrders = new ArrayList<>();
        List<WorkOrder> mCustomerIdWorkOrders = new ArrayList<>();
        List<WorkOrder> mAllWorkOrders = new ArrayList<>();
        Log.i(TAG,  "find --> String recibido como parametro: " + pWorkOrder);

        ValueEventListener mCustomerIdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCustomerIdWorkOrders.clear();
                mAllWorkOrders.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        WorkOrder auxWorkOrder=ds.getValue(WorkOrder.class);
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
                        WorkOrder auxWorkOrder=ds.getValue(WorkOrder.class);
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
        WorkOrderDBRef.orderByChild("customerId").equalTo(pWorkOrder.getCustomer().getCustomerId()).addValueEventListener(mCustomerIdListener);
        WorkOrderDBRef.orderByChild("providerId").equalTo(pWorkOrder.getProvider().getProviderId()).addValueEventListener(mProviderIdListener);

        return mWorkOrderDTO;

    }

    @Override
    public void create(WorkOrder workOrder, Callback<WorkOrder> callback) {
        DatabaseReference createObject = WorkOrderDBRef.push();
        workOrder.setOrderId(createObject.getKey());
        WorkOrderDBRef.child(Objects.requireNonNull(createObject.getKey())).setValue(workOrder).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // La operación se completó con éxito
                Log.w(TAG, "create:isSuccesful -->" +createObject.getKey());
                callback.onSuccess(workOrder);
            } else {
                // Hubo un error al establecer el valor
                System.out.println("Error al establecer el valor: " + task.getException());
                //callback.onError(task.getException());
            }
        });
    }

    @Override
    public void update(WorkOrder workOrder, Callback<WorkOrder> callback) {
        Map<String, Object> updates = new HashMap<>();

        if (workOrder.getSpecialization() != null) {
            updates.put("specialization", workOrder.getSpecialization());
        }
        if (workOrder.getDescription() != null) {
            updates.put("description", workOrder.getDescription());
        }
        if (workOrder.getCreationDate() != null) {
            updates.put("creationDate", workOrder.getCreationDate());
        }
        if (workOrder.getTimeLimit() != null) {
            updates.put("timeLimit", workOrder.getTimeLimit());
        }
        if (workOrder.getState() != null) {
            updates.put("state", workOrder.getState());
        }
        if (workOrder.getStateChangeDate() != null) {
            updates.put("stateChangeDate", workOrder.getStateChangeDate());
        }
        //*********************************************************************//
        if (workOrder.getCustomer().getCustomerId() != null) {
            updates.put("customerId", workOrder.getCustomer().getCustomerId());
        }
        if (workOrder.getCustomer().getCustomerName() != null) {
            updates.put("customerName", workOrder.getCustomer().getCustomerName());
        }
        if (workOrder.getCustomer().getCustomerAddress() != null) {
            updates.put("customerAddress", workOrder.getCustomer().getCustomerAddress());
        }
        if (workOrder.getCustomer().getCustomerLocation() != null) {
            if (workOrder.getCustomer().getCustomerLocation().getLatitude() != null) {
                updates.put("latitude", workOrder.getCustomer().getCustomerLocation().getLatitude());
            }
            if ( workOrder.getCustomer().getCustomerLocation().getLongitude() != null) {
                updates.put("longitude", workOrder.getCustomer().getCustomerLocation().getLongitude());
            }
        }
        if (workOrder.getCustomer().getCustomerPhone() != null) {
            if (workOrder.getCustomer().getCustomerPhone().getNumber() != null) {
                updates.put("number", workOrder.getCustomer().getCustomerPhone().getNumber());
            }
            if ( workOrder.getCustomer().getCustomerPhone().getCcn() != null) {
                updates.put("ccn", workOrder.getCustomer().getCustomerPhone().getCcn());
            }
        }
        //*********************************************************************//
        if (workOrder.getProvider().getProviderId() != null) {
            updates.put("providerId", workOrder.getProvider().getProviderId());
        }
        if (workOrder.getProvider().getProviderName() != null) {
            updates.put("providerName", workOrder.getProvider().getProviderName());
        }
        if (workOrder.getProvider().getProviderAddress() != null) {
            updates.put("providerAddress", workOrder.getProvider().getProviderAddress());
        }
        if (workOrder.getProvider().getProviderLocation() != null) {
            if (workOrder.getProvider().getProviderLocation().getLatitude() != null) {
                updates.put("latitude", workOrder.getProvider().getProviderLocation().getLatitude());
            }
            if ( workOrder.getProvider().getProviderLocation().getLongitude() != null) {
                updates.put("longitude", workOrder.getProvider().getProviderLocation().getLongitude());
            }
        }
        if (workOrder.getProvider().getProviderPhone() != null) {
            if (workOrder.getProvider().getProviderPhone().getNumber() != null) {
                updates.put("number", workOrder.getProvider().getProviderPhone().getNumber());
            }
            if ( workOrder.getProvider().getProviderPhone().getCcn() != null) {
                updates.put("ccn", workOrder.getProvider().getProviderPhone().getCcn());
            }
        }
        //*********************************************************************//

        if (workOrder.getInspection().getInspectionDate() != null) {
            updates.put("inspectionDate", workOrder.getInspection().getInspectionDate());
        }
        if (workOrder.getInspection().getInspectionCharges() != null) {
            updates.put("inspectionCharges", workOrder.getInspection().getInspectionCharges());
        }
        if (workOrder.getInspection().getInspectionTimeLimit() != null) {
            updates.put("inspectionTimeLimit", workOrder.getInspection().getInspectionTimeLimit());
        }
        if (workOrder.getInspection().getInspectionFee() != null) {
            updates.put("inspectionFee", workOrder.getInspection().getInspectionFee());
        }
        if (workOrder.getInspection().getInspectionPaymentOrder() != null) {
            updates.put("inspectionPaymentOrder", workOrder.getInspection().getInspectionPaymentOrder());
        }
        if (workOrder.getInspection().getInspectionFullfilment() != null) {
            updates.put("inspectionFullfilment", workOrder.getInspection().getInspectionFullfilment());
        }
        if (workOrder.getInspection().getInspectionRescheduled() != null) {
            updates.put("inspectionRescheduled", workOrder.getInspection().getInspectionRescheduled());
        }
        //*********************************************************************//

        if (workOrder.getWork().getProposalTimeLimitDate() != null) {
            updates.put("proposalTimeLimitDate", workOrder.getWork().getProposalTimeLimitDate());
        }
        if (workOrder.getWork().getDetail() != null) {
            updates.put("detail", workOrder.getWork().getDetail());
        }
        if (workOrder.getWork().getWorkStartDate() != null) {
            updates.put("workStartDate", workOrder.getWork().getWorkStartDate());
        }
        if (workOrder.getWork().getWorkEndDate() != null) {
            updates.put("workEndDate", workOrder.getWork().getWorkEndDate());
        }
        if (workOrder.getWork().getWorkLaborCost() != null) {
            updates.put("workLaborCost", workOrder.getWork().getWorkLaborCost());
        }
        if (workOrder.getWork().getWorkMaterialsCost() != null) {
            updates.put("workMaterialsCost", workOrder.getWork().getWorkMaterialsCost());
        }
        if (workOrder.getWork().getWorkFee() != null) {
            updates.put("workFee", workOrder.getWork().getWorkFee());
        }
        if (workOrder.getWork().getWorkPaymentOrder() != null) {
            updates.put("workPaymentOrder", workOrder.getWork().getWorkPaymentOrder());
        }
        if (workOrder.getWork().getWorkWarrantyEndDate() != null) {
            updates.put("workWarrantyEndDate", workOrder.getWork().getWorkWarrantyEndDate());
        }
        if (workOrder.getWork().getWorkLimitTimeExtension() != null) {
            updates.put("workLimitTimeExtension", workOrder.getWork().getWorkLimitTimeExtension());
        }
        //*********************************************************************//
        if (workOrder.getFeedback().getImpressions() != null) {
            updates.put("impressions", workOrder.getFeedback().getImpressions());
        }
        if (workOrder.getFeedback().getAppereanceScore() != null) {
            updates.put("appereanceScore", workOrder.getFeedback().getAppereanceScore());
        }
        if (workOrder.getFeedback().getCleanlinessScore() != null) {
            updates.put("cleanlinessScore", workOrder.getFeedback().getCleanlinessScore());
        }
        if (workOrder.getFeedback().getSpeedScore() != null) {
            updates.put("speedScore", workOrder.getFeedback().getSpeedScore());
        }
        if (workOrder.getFeedback().getQualityScore() != null) {
            updates.put("qualityScore", workOrder.getFeedback().getQualityScore());
        }
        WorkOrderDBRef.child(workOrder.getOrderId()).updateChildren(updates).addOnSuccessListener(aVoid -> {
                    Log.i(TAG, " update() operation --> WorkOrder Update Success");
                })
                .addOnFailureListener(e -> {
                    Log.i(TAG, " update() operation --> WorkOrder Update Failed: " + e);
                });
    }

}
