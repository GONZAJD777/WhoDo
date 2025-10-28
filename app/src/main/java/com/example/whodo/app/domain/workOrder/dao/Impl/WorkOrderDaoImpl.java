package com.example.whodo.app.domain.workOrder.dao.Impl;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.BuildConfig;
import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.user.UserApiRestRequestDTO;
import com.example.whodo.app.domain.user.UserMapper;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.domain.workOrder.WorkOrderApiRestRequestDTO;
import com.example.whodo.app.domain.workOrder.WorkOrderMapper;
import com.example.whodo.app.domain.workOrder.dao.WorkOrderDao;
import com.example.whodo.app.network.reactive.workOrder.SSEWorkOrderClient;
import com.example.whodo.app.network.rest.RetrofitClient;
import com.example.whodo.app.network.rest.api.WorkOrderApi;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class WorkOrderDaoImpl implements WorkOrderDao<WorkOrder> {
    private final String TAG = "MONGODB-WORKORDER-DAO";
    private final WorkOrderApi mWorkOrderApi;
    private final String mBaseUrl= BuildConfig.BASE_URL;
    public WorkOrderDaoImpl(Context pContext) {
        RetrofitClient retrofitClient = new RetrofitClient(mBaseUrl,pContext);
        this.mWorkOrderApi = retrofitClient.createService(WorkOrderApi.class);

    }
    @Override
    public LiveData<List<WorkOrder>> find(WorkOrder workOrder) {

        MutableLiveData<List<WorkOrder>> mWorkOrder = new MutableLiveData<>();
        SSEWorkOrderClient sseWorkOrderClient = new SSEWorkOrderClient(mBaseUrl + "work-orders/stream/getAllUserWorkOrders?userId="+workOrder.getCustomer().getCustomerId());
        Log.d(TAG, "sseWorkOrderClient -->" + mBaseUrl + "work-orders/stream/getAllUserWorkOrders?userId=" + workOrder.getCustomer().getCustomerId());

        sseWorkOrderClient.startListening(mWorkOrder);

        return mWorkOrder;
    }
    @Override
    public void create(WorkOrder workOrder, Callback<WorkOrder> callback) {
        WorkOrderApiRestRequestDTO mWorkOrderApiRestRequestDTO = WorkOrderMapper.toWorkOrderApiRestRequestDTO(workOrder);
        Call<WorkOrder> call = mWorkOrderApi.createWorkOrder(mWorkOrderApiRestRequestDTO);
        Log.d(TAG, "Endpoint Requested -->" + mWorkOrderApi.createWorkOrder(mWorkOrderApiRestRequestDTO).request().url());
        Log.d(TAG, "create() -->" + mWorkOrderApiRestRequestDTO.toString() );

        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WorkOrder> call, @NonNull Response<WorkOrder> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<WorkOrder> call, @NonNull Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }
    @Override
    public void update(WorkOrder pWorkOrder, Callback<WorkOrder> callback) {
        if (!validateUpdate(pWorkOrder).isEmpty()){
                WorkOrderApiRestRequestDTO mWorkOrderApiRestRequestDTO = WorkOrderMapper.toWorkOrderApiRestRequestDTO(pWorkOrder);
                Log.d(TAG, "Endpoint Requested -->" + mWorkOrderApi.updateWorkOrder(mWorkOrderApiRestRequestDTO).request().url());
                Log.d(TAG, "Update -->" + pWorkOrder);
                Call<WorkOrder> call = mWorkOrderApi.updateWorkOrder(mWorkOrderApiRestRequestDTO);

                call.enqueue(new retrofit2.Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<WorkOrder> call, @NonNull Response<WorkOrder> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(new Exception("Error en la respuesta: " + response.code()));
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<WorkOrder> call, @NonNull Throwable t) {
                        callback.onError(new Exception(t));
                    }
                });
            } else {
            Log.d(TAG, "Update Canceled --> Nothing to Update" );
        }
    }
    private Map<String, Object> validateUpdate(WorkOrder workOrder){
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

        return updates;
    }

}
