package com.example.whodo.app.domain.workOrder;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class WorkOrder {

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
    private String CreationDate; //Fecha y hora de creacion de la orden en formato YYYYMMDD24HHMMSS
    private String TimeLimit; // Fecha y hora que hasta la cual la orden estara disponible para ser tomada por un proveedor
    private String StateChangeDate; //Fecha y hora del ultimo cambio de estado en formato YYYYMMDD24HHMMSS
    private String State; //esta variable ira marcando el ciclo de vida de la orden, desde q se crea hasta q es cerrada el cambio de estado requerira acciones de Customer y provider.

    //INSPECTION INFO
    private String InspectionDate; //Fecha y hora de cita de inspeccion en formato YYYYMMDD24HHMMSS
    private Integer InspectionCharges; //Cargo por la visita para inspeccion, es opcional
    private String InspectionPaymentOrder; // ID de la orden de pago generada como registro del pago de la inspeccion
    private Integer InspectionFee;
    //WORK INFO
    private String WorkStartDate;//Fecha y hora de INICIO de trabajo en formato YYYYMMDD24HHMMSS
    private String WorkEndDate;//Fecha y hora de FIN de trabajho en formato YYYYMMDD24HHMMSS
    private Integer WorkLaborCost;
    private Integer WorkMaterialsCost;
    private Integer WorkFee;
    private String Detail; // Bitacora de tareas y comentarios del proveedor
    private String WorkPaymentOrder;
    private String WorkWarrantyEndDate;//Fecha y hora de FIN de GARANTIA en formato YYYYMMDD24HHMMSS
    //SCORES
    private String Impressions; //Review
    private Integer AppereanceScore;
    private Integer CleanlinessScore;
    private Integer SpeedScore;
    private Integer QualityScore;

    public WorkOrder() {}

    // assigned OPEN WORK ORDER Constructor setted ONEVALUATION State
    public WorkOrder(String pCustomerId,String pCustomerName,String pCustomerAddress,double pCustomerLat,double pCustomerLng,String pCustomerPhoneNumber,
                     String pProviderId,String pProviderName,String pProviderAddress,double pProviderLat,double pProviderLng,String pProviderPhoneNumber,
                     String pSpecialization,String pDescription,String pCreationDate,String pTimeLimit,String pStateChangeDate) {

        //this.OrderId = "orderId";
        this.CustomerId = pCustomerId;
        this.CustomerName=pCustomerName;
        this.CustomerAddress=pCustomerAddress;
        this.CustomerLat=pCustomerLat;
        this.CustomerLng=pCustomerLng;
        this.CustomerPhoneNumber=pCustomerPhoneNumber;

        this.ProviderId = pProviderId;
        this.ProviderName=pProviderName;
        this.ProviderAddress=pProviderAddress;
        this.ProviderLat=pProviderLat;
        this.ProviderLng=pProviderLng;
        this.ProviderPhoneNumber=pProviderPhoneNumber;

        this.Specialization = pSpecialization;
        this.Description = pDescription;
        //this.Detail = detail;
        this.CreationDate = pCreationDate;
        this.TimeLimit = pTimeLimit;
        this.State = "ONEVALUATION";
        this.StateChangeDate = pStateChangeDate;
        //this.InspectionDate = inspectionDate;
        //this.InspectionCharges = inspectionCharges;
        //this.InspectionPaymentOrder = inspectionPaymentOrder;
        //this.WorkStartDate = workStartDate;
        //this.WorkEndDate = workEndDate;
        //this.WorkCost = workCost;
        //this.WorkPaymentOrder = workPaymentOrder;
        //this.WorkWarrantyEndDate = workWarrantyEndDate;
        //this.Impressions = impressions;
        //this.AppereanceScore = appereanceScore;
        //this.CleanlinessScore = cleanlinessScore;
        //this.SpeedScore = speedScore;
        //this.QualityScore = qualityScore;
    }
    // not assigned OPEN WORK ORDER Constructor
    public WorkOrder(String pCustomerId,String pCustomerName,String pCustomerAddress,double pCustomerLat,double pCustomerLng,String pCustomerPhoneNumber,
                     String pSpecialization,String pDescription,String pCreationDate,String pTimeLimit,String pStateChangeDate) {
        this.CustomerId = pCustomerId;
        this.CustomerName=pCustomerName;
        this.CustomerAddress=pCustomerAddress;
        this.CustomerLat=pCustomerLat;
        this.CustomerLng=pCustomerLng;
        this.CustomerPhoneNumber=pCustomerPhoneNumber;
        this.Specialization = pSpecialization;
        this.Description = pDescription;
        this.CreationDate = pCreationDate;
        this.TimeLimit = pTimeLimit;
        this.State = "OPEN";
        this.StateChangeDate = pStateChangeDate;
    }

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

    public String getCreationDate() {
        return CreationDate;
    }
    public void setCreationDate(String pCreationDate){
        CreationDate=pCreationDate;
    }

    public String getTimeLimit() {
        return TimeLimit;
    }
    public void setTimeLimit(String pTimeLimit){
        TimeLimit=pTimeLimit;
    }

    public String getState() {
        return State;
    }
    public void setState(String pState){
        State=pState;
    }

    public String getStateChangeDate() {
        return StateChangeDate;
    }
    public void setStateChangeDate(String pStateChangeDate){
        StateChangeDate=pStateChangeDate;
    }

    //INSPECTION INFO
    public String getInspectionDate() {
        return InspectionDate;
    }
    public void setInspectionDate(String pInspectionDate){
        InspectionDate=pInspectionDate;
    }

    public Integer getInspectionCharges() {
        return InspectionCharges;
    }
    public void setInspectionCharges(Integer pInspectionCharges){ InspectionCharges=pInspectionCharges; }

    public Integer getInspectionFee() {
        return InspectionFee;
    }
    public void setInspectionFee(Integer pInspectionFee){ InspectionFee=pInspectionFee; }

    public String getInspectionPaymentOrder() {
        return InspectionPaymentOrder;
    }
    public void setInspectionPaymentOrder(String pInspectionPaymentOrder){ InspectionPaymentOrder=pInspectionPaymentOrder; }

    //WORK INFO
    public String getWorkStartDate() {
        return WorkStartDate;
    }
    public void setWorkStartDate(String pWorkStartDate){ WorkStartDate=pWorkStartDate; }

    public String getWorkEndDate() {
        return WorkEndDate;
    }
    public void setWorkEndDate(String pWorkEndDate){ WorkEndDate=pWorkEndDate; }

    public Integer getWorkLaborCost() { return WorkLaborCost; }
    public void setWorkLaborCost(Integer pWorkCost){ WorkLaborCost=pWorkCost; }

    public Integer getWorkMaterialsCost() {
        return WorkMaterialsCost;
    }
    public void setWorkMaterialsCost(Integer pWorkMaterialsCost){ WorkMaterialsCost=pWorkMaterialsCost; }

    public Integer getWorkFee() {
        return WorkFee;
    }
    public void setWorkFee(Integer pWorkFee){ WorkFee=pWorkFee; }

    public String getWorkPaymentOrder() {
        return WorkPaymentOrder;
    }
    public void setWorkPaymentOrder(String pWorkPaymentOrder){ WorkPaymentOrder=pWorkPaymentOrder; }

    public String getWorkWarrantyEndDate() {
        return WorkWarrantyEndDate;
    }
    public void setWorkWarrantyEndDate(String pWorkWarrantyEndDate){ WorkWarrantyEndDate=pWorkWarrantyEndDate; }

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
