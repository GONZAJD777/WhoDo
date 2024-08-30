package com.example.whodo.app.domain.workOrder;


public class WorkOrderMapper {

    public static WorkOrderDTO toDto(WorkOrder pEntity) {
        WorkOrderDTO mWorkOrderDTO = new WorkOrderDTO();

        mWorkOrderDTO.setOrderId(pEntity.getOrderId());
        mWorkOrderDTO.setCustomerId(pEntity.getCustomerId());
        mWorkOrderDTO.setCustomerName(pEntity.getCustomerName());
        mWorkOrderDTO.setCustomerAddress(pEntity.getCustomerAddress());
        mWorkOrderDTO.setCustomerLat(pEntity.getCustomerLat());
        mWorkOrderDTO.setCustomerLng(pEntity.getCustomerLng());
        mWorkOrderDTO.setCustomerPhoneNumber(pEntity.getCustomerPhoneNumber());

        mWorkOrderDTO.setProviderId(pEntity.getProviderId());
        mWorkOrderDTO.setProviderName(pEntity.getProviderName());
        mWorkOrderDTO.setProviderAddress(pEntity.getProviderAddress());
        mWorkOrderDTO.setProviderLat(pEntity.getProviderLat());
        mWorkOrderDTO.setProviderLng(pEntity.getProviderLng());
        mWorkOrderDTO.setProviderPhoneNumber(pEntity.getProviderPhoneNumber());

        mWorkOrderDTO.setSpecialization(pEntity.getSpecialization());
        mWorkOrderDTO.setDescription(pEntity.getDescription());
        mWorkOrderDTO.setDetail(pEntity.getDetail());
        mWorkOrderDTO.setTimeLimit(pEntity.getTimeLimit());
        mWorkOrderDTO.setCreationDate(pEntity.getCreationDate());
        mWorkOrderDTO.setState(pEntity.getState());
        mWorkOrderDTO.setStateChangeDate(pEntity.getStateChangeDate());

        mWorkOrderDTO.setInspectionDate(pEntity.getInspectionDate());
        mWorkOrderDTO.setInspectionCharges(pEntity.getInspectionCharges());
        mWorkOrderDTO.setInspectionPaymentOrder(pEntity.getInspectionPaymentOrder());
        mWorkOrderDTO.setInspectionFee(pEntity.getInspectionFee());

        mWorkOrderDTO.setWorkStartDate(pEntity.getWorkStartDate());
        mWorkOrderDTO.setWorkEndDate(pEntity.getWorkEndDate());
        mWorkOrderDTO.setWorkLaborCost(pEntity.getWorkLaborCost());
        mWorkOrderDTO.setWorkMaterialsCost(pEntity.getWorkMaterialsCost());
        mWorkOrderDTO.setWorkFee(pEntity.getWorkFee());
        mWorkOrderDTO.setWorkPaymentOrder(pEntity.getWorkPaymentOrder());
        mWorkOrderDTO.setWorkWarrantyEndDate(pEntity.getWorkWarrantyEndDate());

        mWorkOrderDTO.setImpressions(pEntity.getImpressions());
        mWorkOrderDTO.setAppereanceScore(pEntity.getAppereanceScore());
        mWorkOrderDTO.setCleanlinessScore(pEntity.getCleanlinessScore());
        mWorkOrderDTO.setSpeedScore(pEntity.getSpeedScore());
        mWorkOrderDTO.setQualityScore(pEntity.getQualityScore());

        return mWorkOrderDTO;
    }

    public static WorkOrder toEntity(WorkOrderDTO pWorkOrderDTO) {
        WorkOrder mEntity = new WorkOrder();
        mEntity.setOrderId(pWorkOrderDTO.getOrderId());

        mEntity.setCustomerId(pWorkOrderDTO.getCustomerId());
        mEntity.setCustomerName(pWorkOrderDTO.getCustomerName());
        mEntity.setCustomerAddress(pWorkOrderDTO.getCustomerAddress());
        mEntity.setCustomerLat(pWorkOrderDTO.getCustomerLat());
        mEntity.setCustomerLng(pWorkOrderDTO.getCustomerLng());
        mEntity.setCustomerPhoneNumber(pWorkOrderDTO.getCustomerPhoneNumber());

        mEntity.setProviderId(pWorkOrderDTO.getProviderId());
        mEntity.setProviderName(pWorkOrderDTO.getProviderName());
        mEntity.setProviderAddress(pWorkOrderDTO.getProviderAddress());
        mEntity.setProviderLat(pWorkOrderDTO.getProviderLat());
        mEntity.setProviderLng(pWorkOrderDTO.getProviderLng());
        mEntity.setProviderPhoneNumber(pWorkOrderDTO.getProviderPhoneNumber());

        mEntity.setSpecialization(pWorkOrderDTO.getSpecialization());
        mEntity.setDescription(pWorkOrderDTO.getDescription());
        mEntity.setDetail(pWorkOrderDTO.getDetail());
        mEntity.setTimeLimit(pWorkOrderDTO.getTimeLimit());
        mEntity.setCreationDate(pWorkOrderDTO.getCreationDate());
        mEntity.setState(pWorkOrderDTO.getState());
        mEntity.setStateChangeDate(pWorkOrderDTO.getStateChangeDate());

        mEntity.setInspectionDate(pWorkOrderDTO.getInspectionDate());
        mEntity.setInspectionCharges(pWorkOrderDTO.getInspectionCharges());
        mEntity.setInspectionFee(pWorkOrderDTO.getInspectionFee());
        mEntity.setInspectionPaymentOrder(pWorkOrderDTO.getInspectionPaymentOrder());

        mEntity.setWorkStartDate(pWorkOrderDTO.getWorkStartDate());
        mEntity.setWorkEndDate(pWorkOrderDTO.getWorkEndDate());

        mEntity.setWorkLaborCost(pWorkOrderDTO.getWorkLaborCost());
        mEntity.setWorkMaterialsCost(pWorkOrderDTO.getWorkMaterialsCost());
        mEntity.setWorkFee(pWorkOrderDTO.getWorkFee());

        mEntity.setWorkPaymentOrder(pWorkOrderDTO.getWorkPaymentOrder());
        mEntity.setWorkWarrantyEndDate(pWorkOrderDTO.getWorkWarrantyEndDate());

        mEntity.setImpressions(pWorkOrderDTO.getImpressions());
        mEntity.setAppereanceScore(pWorkOrderDTO.getAppereanceScore());
        mEntity.setCleanlinessScore(pWorkOrderDTO.getCleanlinessScore());
        mEntity.setSpeedScore(pWorkOrderDTO.getSpeedScore());
        mEntity.setQualityScore(pWorkOrderDTO.getQualityScore());
        return mEntity;
    }
}

