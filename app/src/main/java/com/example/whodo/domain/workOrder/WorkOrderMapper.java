package com.example.whodo.domain.workOrder;


public class WorkOrderMapper {

    public static WorkOrderDTO toDto(WorkOrder entity) {
        WorkOrderDTO dto = new WorkOrderDTO();
        dto.setOrderId(entity.getOrderId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setTimeLimit(entity.getTimeLimit());
        dto.setProviderId(entity.getProviderId());
        dto.setSpecialization(entity.getSpecialization());
        dto.setDescription(entity.getDescription());
        dto.setDetail(entity.getDetail());
        dto.setCreationDate(entity.getCreationDate());
        dto.setState(entity.getState());
        dto.setStateChangeDate(entity.getStateChangeDate());
        dto.setInspectionDate(entity.getInspectionDate());
        dto.setInspectionCharges(entity.getInspectionCharges());
        dto.setInspectionPaymentOrder(entity.getInspectionPaymentOrder());
        dto.setWorkStartDate(entity.getWorkStartDate());
        dto.setWorkEndDate(entity.getWorkEndDate());
        dto.setWorkCost(entity.getWorkCost());
        dto.setWorkPaymentOrder(entity.getWorkPaymentOrder());
        dto.setWorkWarrantyEndDate(entity.getWorkWarrantyEndDate());
        dto.setImpressions(entity.getImpressions());
        dto.setAppereanceScore(entity.getAppereanceScore());
        dto.setCleanlinessScore(entity.getCleanlinessScore());
        dto.setSpeedScore(entity.getSpeedScore());
        dto.setQualityScore(entity.getQualityScore());
        return dto;
    }

    public static WorkOrder toEntity(WorkOrderDTO dto) {
        WorkOrder entity = new WorkOrder();
        entity.setOrderId(dto.getOrderId());
        entity.setCustomerId(dto.getCustomerId());
        entity.setTimeLimit(dto.getTimeLimit());
        entity.setProviderId(dto.getProviderId());
        entity.setSpecialization(dto.getSpecialization());
        entity.setDescription(dto.getDescription());
        entity.setDetail(dto.getDetail());
        entity.setCreationDate(dto.getCreationDate());
        entity.setState(dto.getState());
        entity.setStateChangeDate(dto.getStateChangeDate());
        entity.setInspectionDate(dto.getInspectionDate());
        entity.setInspectionCharges(dto.getInspectionCharges());
        entity.setInspectionPaymentOrder(dto.getInspectionPaymentOrder());
        entity.setWorkStartDate(dto.getWorkStartDate());
        entity.setWorkEndDate(dto.getWorkEndDate());
        entity.setWorkCost(dto.getWorkCost());
        entity.setWorkPaymentOrder(dto.getWorkPaymentOrder());
        entity.setWorkWarrantyEndDate(dto.getWorkWarrantyEndDate());
        entity.setImpressions(dto.getImpressions());
        entity.setAppereanceScore(dto.getAppereanceScore());
        entity.setCleanlinessScore(dto.getCleanlinessScore());
        entity.setSpeedScore(dto.getSpeedScore());
        entity.setQualityScore(dto.getQualityScore());
        return entity;
    }
}

