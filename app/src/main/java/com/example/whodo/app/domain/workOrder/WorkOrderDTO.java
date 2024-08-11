package com.example.whodo.app.domain.workOrder;

import com.google.android.gms.maps.model.LatLng;

public class WorkOrderDTO {

    private String OrderId;

    private String CustomerId;
    private String CustomerName;
    private String CustomerAddress;
    private double CustomerLat;
    private double CustomerLng;
    private String CustomerPhoneNumber;

    private String ProviderId;
    private String ProviderName;
    private String ProviderAddress;
    private double ProviderLat;
    private double ProviderLng;
    private String ProviderPhoneNumber;

    private String Specialization; //Categoria del trabajo demandado
    private String Description;// Descripcion del trabajo a realizar por el cliente
    private String Detail; // Bitacora de tareas y comentarios del proveedor
    private Long CreationDate; //Fecha y hora de creacion de la orden en formato YYYYMMDD24HHMMSS
    private Long TimeLimit; // dias desde la fecha de creacion en los cuales la orden estara disponible para ser tomada por un proveedor
    private String State; //esta variable ira marcando el ciclo de vida de la orden, desde q se crea hasta q es cerrada el cambio de estado requerira acciones de Customer y provider.
    private Long StateChangeDate; //Fecha y hora del ultimo cambio de estado en formato YYYYMMDD24HHMMSS
    //INSPECTION INFO
    private Long InspectionDate; //Fecha y hora de cita de inspeccion en formato YYYYMMDD24HHMMSS
    private Integer InspectionCharges; //Cargo por la visita para inspeccion, es opcional
    private String InspectionPaymentOrder; // ID de la orden de pago generada como registro del pago de la inspeccion
    //WORK INFO
    private Long WorkStartDate;//Fecha y hora de INICIO de trabajo en formato YYYYMMDD24HHMMSS
    private Long WorkEndDate;//Fecha y hora de FIN de trabajho en formato YYYYMMDD24HHMMSS
    private Integer WorkCost;
    private String WorkPaymentOrder;
    private Long WorkWarrantyEndDate;//Fecha y hora de FIN de GARANTIA en formato YYYYMMDD24HHMMSS
    //SCORES
    private String Impressions;
    private Integer AppereanceScore;
    private Integer CleanlinessScore;
    private Integer SpeedScore;
    private Integer QualityScore;

    public WorkOrderDTO() {}

    public String getOrderId() {
        return OrderId;
    }
    public void setOrderId(String pOrderId){
        OrderId=pOrderId;
    }

    //******** CUSTOMER INFO METHODS ********//
    public String getCustomerId() {
        return CustomerId;
    }
    public void setCustomerId(String pCustomerId){
        CustomerId=pCustomerId;
    }

    public String getCustomerName() { return CustomerName; }
    public void setCustomerName(String pCustomerName) { this.CustomerName = pCustomerName; }

    public String getCustomerAddress() { return CustomerAddress; }
    public void setCustomerAddress(String pCustomerAddress) { this.CustomerAddress = pCustomerAddress; }

    public double getCustomerLat() { return CustomerLat; }
    public void setCustomerLat(double pCustomerLat) { this.CustomerLat = pCustomerLat; }

    public double getCustomerLng() { return CustomerLng; }
    public void setCustomerLng(double pCustomerLng) { this.CustomerLng = pCustomerLng; }

    public String getCustomerPhoneNumber() {return CustomerPhoneNumber; }
    public void setCustomerPhoneNumber(String pCustomerPhoneNumber) { this.CustomerPhoneNumber = pCustomerPhoneNumber; }

    //******** PROVIDER INFO METHODS ********//
    public String getProviderId() {
        return ProviderId;
    }
    public void setProviderId(String pProviderId){
        ProviderId=pProviderId;
    }

    public String getProviderName() { return ProviderName; }
    public void setProviderName(String pProviderName) { this.ProviderName = pProviderName; }

    public String getProviderAddress() { return ProviderAddress; }
    public void setProviderAddress(String pProviderAddress) { this.ProviderAddress = pProviderAddress; }

    public double getProviderLat() { return ProviderLat; }
    public void setProviderLat(double pProviderLat) { this.ProviderLat = pProviderLat; }

    public double getProviderLng() { return ProviderLng; }
    public void setProviderLng(double pProviderLng) { this.ProviderLng = pProviderLng; }

    public String getProviderPhoneNumber() { return ProviderPhoneNumber; }
    public void setProviderPhoneNumber(String pProviderPhoneNumber) { this.ProviderPhoneNumber = pProviderPhoneNumber; }
    //******** ******** ********//

    public String getSpecialization() {
        return Specialization;
    }
    public void setSpecialization(String pSpecialization){
        Specialization=pSpecialization;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String pDescription){
        Description=pDescription;
    }

    public String getDetail() {
        return Detail;
    }
    public void setDetail(String pDetail){
        Detail=pDetail;
    }

    public Long getCreationDate() {
        return CreationDate;
    }
    public void setCreationDate(Long pCreationDate){
        CreationDate=pCreationDate;
    }

    public Long getTimeLimit() {
        return TimeLimit;
    }
    public void setTimeLimit(Long pTimeLimit){
        TimeLimit=pTimeLimit;
    }

    public String getState() {
        return State;
    }
    public void setState(String pState){
        State=pState;
    }

    public Long getStateChangeDate() {
        return StateChangeDate;
    }
    public void setStateChangeDate(Long pStateChangeDate){
        StateChangeDate=pStateChangeDate;
    }

    //INSPECTION INFO
    public Long getInspectionDate() {
        return InspectionDate;
    }
    public void setInspectionDate(Long pInspectionDate){
        InspectionDate=pInspectionDate;
    }

    public Integer getInspectionCharges() {
        return InspectionCharges;
    }
    public void setInspectionCharges(Integer pInspectionCharges){ InspectionCharges=pInspectionCharges; }

    public String getInspectionPaymentOrder() {
        return InspectionPaymentOrder;
    }
    public void setInspectionPaymentOrder(String pInspectionPaymentOrder){ InspectionPaymentOrder=pInspectionPaymentOrder; }

    //WORK INFO
    public Long getWorkStartDate() {
        return WorkStartDate;
    }
    public void setWorkStartDate(Long pWorkStartDate){ WorkStartDate=pWorkStartDate; }

    public Long getWorkEndDate() {
        return WorkEndDate;
    }
    public void setWorkEndDate(Long pWorkEndDate){ WorkEndDate=pWorkEndDate; }

    public Integer getWorkCost() {
        return WorkCost;
    }
    public void setWorkCost(Integer pWorkCost){ WorkCost=pWorkCost; }

    public String getWorkPaymentOrder() {
        return WorkPaymentOrder;
    }
    public void setWorkPaymentOrder(String pWorkPaymentOrder){ WorkPaymentOrder=pWorkPaymentOrder; }

    public Long getWorkWarrantyEndDate() {
        return WorkWarrantyEndDate;
    }
    public void setWorkWarrantyEndDate(Long pWorkWarrantyEndDate){ WorkWarrantyEndDate=pWorkWarrantyEndDate; }

    //SCORES
    public String getImpressions() {
        return Impressions;
    }
    public void setImpressions(String pImpressions){ Impressions=pImpressions; }

    public Integer getAppereanceScore() {
        return AppereanceScore;
    }
    public void setAppereanceScore(Integer pAppereanceScore){ AppereanceScore=pAppereanceScore; }

    public Integer getCleanlinessScore() {
        return CleanlinessScore;
    }
    public void setCleanlinessScore(Integer pCleanlinessScore){ CleanlinessScore=pCleanlinessScore; }

    public Integer getSpeedScore() {
        return SpeedScore;
    }
    public void setSpeedScore(Integer pSpeedScore){ SpeedScore=pSpeedScore; }

    public Integer getQualityScore() {
        return QualityScore;
    }
    public void setQualityScore(Integer pQualityScore){ QualityScore=pQualityScore; }


}
