package com.example.whodo.app.domain.workOrder;

public class WorkOrderMapper {

    public static WorkOrderApiRestRequestDTO toWorkOrderApiRestRequestDTO(WorkOrder pWorkOrder) {
        if (pWorkOrder == null) {
            return null;
        }
        WorkOrderApiRestRequestDTO mWorkOrderApiRestRequestDTO = new WorkOrderApiRestRequestDTO();

        mWorkOrderApiRestRequestDTO.setOrderId(pWorkOrder.getOrderId());
        mWorkOrderApiRestRequestDTO.setSpecialization(pWorkOrder.getSpecialization());
        mWorkOrderApiRestRequestDTO.setDescription(pWorkOrder.getDescription());
        mWorkOrderApiRestRequestDTO.setState(pWorkOrder.getState());
        if(pWorkOrder.getTimeLimit()!=null){
            mWorkOrderApiRestRequestDTO.setTimeLimit(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getTimeLimit()));
        }
        if(pWorkOrder.getCreationDate()!=null){
            mWorkOrderApiRestRequestDTO.setCreationDate(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getCreationDate()));
        }
        if(pWorkOrder.getStateChangeDate()!=null){
            mWorkOrderApiRestRequestDTO.setStateChangeDate(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getStateChangeDate()));
        }


        mWorkOrderApiRestRequestDTO.getCustomer().setCustomerId(pWorkOrder.getCustomer().getCustomerId());
        mWorkOrderApiRestRequestDTO.getCustomer().setCustomerName(pWorkOrder.getCustomer().getCustomerName());
        mWorkOrderApiRestRequestDTO.getCustomer().setCustomerAddress(pWorkOrder.getCustomer().getCustomerAddress());
        mWorkOrderApiRestRequestDTO.getCustomer().setCustomerLocation(pWorkOrder.getCustomer().getCustomerLocation());
        mWorkOrderApiRestRequestDTO.getCustomer().setCustomerPhone(pWorkOrder.getCustomer().getCustomerPhone());

        mWorkOrderApiRestRequestDTO.getProvider().setProviderId(pWorkOrder.getProvider().getProviderId());
        mWorkOrderApiRestRequestDTO.getProvider().setProviderName(pWorkOrder.getProvider().getProviderName());
        mWorkOrderApiRestRequestDTO.getProvider().setProviderAddress(pWorkOrder.getProvider().getProviderAddress());
        mWorkOrderApiRestRequestDTO.getProvider().setProviderLocation(pWorkOrder.getProvider().getProviderLocation());
        mWorkOrderApiRestRequestDTO.getProvider().setProviderPhone(pWorkOrder.getProvider().getProviderPhone());

        if(pWorkOrder.getInspection().getInspectionDate()!=null){
            mWorkOrderApiRestRequestDTO.getInspection().setInspectionDate(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getInspection().getInspectionDate()));
        }
        mWorkOrderApiRestRequestDTO.getInspection().setInspectionCharges(pWorkOrder.getInspection().getInspectionCharges());
        if (pWorkOrder.getInspection().getInspectionTimeLimit()!=null){
            mWorkOrderApiRestRequestDTO.getInspection().setInspectionTimeLimit(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getInspection().getInspectionTimeLimit()));
        }
        mWorkOrderApiRestRequestDTO.getInspection().setInspectionPaymentOrder(pWorkOrder.getInspection().getInspectionPaymentOrder());
        mWorkOrderApiRestRequestDTO.getInspection().setInspectionFee(pWorkOrder.getInspection().getInspectionFee());
        mWorkOrderApiRestRequestDTO.getInspection().setInspectionFullfilment(pWorkOrder.getInspection().getInspectionFullfilment());
        mWorkOrderApiRestRequestDTO.getInspection().setInspectionRescheduled(pWorkOrder.getInspection().getInspectionRescheduled());


        if(pWorkOrder.getWork().getProposalTimeLimitDate()!=null){
            mWorkOrderApiRestRequestDTO.getWork().setProposalTimeLimitDate(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getWork().getProposalTimeLimitDate()));
        }
        if(pWorkOrder.getWork().getWorkStartDate()!=null){
            mWorkOrderApiRestRequestDTO.getWork().setWorkStartDate(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getWork().getWorkStartDate()));
        }
        if(pWorkOrder.getWork().getWorkEndDate()!=null){
            mWorkOrderApiRestRequestDTO.getWork().setWorkEndDate(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getWork().getWorkEndDate()));
        }
        if(pWorkOrder.getWork().getWorkWarrantyEndDate()!=null){
            mWorkOrderApiRestRequestDTO.getWork().setWorkWarrantyEndDate(new WorkOrderApiRestRequestDTO.WorkOrderDate(pWorkOrder.getWork().getWorkWarrantyEndDate()));
        }
        mWorkOrderApiRestRequestDTO.getWork().setWorkLaborCost(pWorkOrder.getWork().getWorkLaborCost());
        mWorkOrderApiRestRequestDTO.getWork().setWorkMaterialsCost(pWorkOrder.getWork().getWorkMaterialsCost());
        mWorkOrderApiRestRequestDTO.getWork().setDetail(pWorkOrder.getWork().getDetail());
        mWorkOrderApiRestRequestDTO.getWork().setWorkFee(pWorkOrder.getWork().getWorkFee());
        mWorkOrderApiRestRequestDTO.getWork().setWorkPaymentOrder(pWorkOrder.getWork().getWorkPaymentOrder());
        mWorkOrderApiRestRequestDTO.getWork().setWorkLimitTimeExtension(pWorkOrder.getWork().getWorkLimitTimeExtension());

        mWorkOrderApiRestRequestDTO.getFeedback().setImpressions(pWorkOrder.getFeedback().getImpressions());
        mWorkOrderApiRestRequestDTO.getFeedback().setAppereanceScore(pWorkOrder.getFeedback().getAppereanceScore());
        mWorkOrderApiRestRequestDTO.getFeedback().setCleanlinessScore(pWorkOrder.getFeedback().getCleanlinessScore());
        mWorkOrderApiRestRequestDTO.getFeedback().setSpeedScore(pWorkOrder.getFeedback().getSpeedScore());
        mWorkOrderApiRestRequestDTO.getFeedback().setQualityScore(pWorkOrder.getFeedback().getQualityScore());

        return mWorkOrderApiRestRequestDTO;
    }
}

