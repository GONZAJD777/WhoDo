package com.example.whodo.domain.workOrder;

public class WorkOrder {

    private String OrderId;
    private String CustomerId;
    private String ProviderId;
    private String Specialization; //Categoria del trabajo demandado
    private String Description;// Descripcion del trabajo a realizar por el cliente
    private String Detail; // Bitacora de tareas y comentarios del proveedor
    private Long CreationDate; //Fecha y hora de creacion de la orden en formato YYYYMMDD24HHMMSS
    private Long TimeLimit; // Fecha y hora que hasta la cual la orden estara disponible para ser tomada por un proveedor
    private String State; //esta variable ira marcando el ciclo de vida de la orden, desde q se crea hasta q es cerrada el cambio de estado requerira acciones de Customer y provider.
    private Long StateChangeDate; //Fecha y hora del ultimo cambio de estado en formato YYYYMMDD24HHMMSS
    //INSPECTION INFO
    private Long InspectionDate; //Fecha y hora de cita de inspeccion en formato YYYYMMDD24HHMMSS
    private String InspectionCharges; //Cargo por la visita para inspeccion, es opcional
    private String InspectionPaymentOrder; // ID de la orden de pago generada como registro del pago de la inspeccion
    //WORK INFO
    private Long WorkStartDate;//Fecha y hora de INICIO de trabajo en formato YYYYMMDD24HHMMSS
    private Long WorkEndDate;//Fecha y hora de FIN de trabajho en formato YYYYMMDD24HHMMSS
    private String WorkCost;
    private String WorkPaymentOrder;
    private Long WorkWarrantyEndDate;//Fecha y hora de FIN de GARANTIA en formato YYYYMMDD24HHMMSS
    //SCORES
    private String Impressions;
    private String AppereanceScore;
    private String CleanlinessScore;
    private String SpeedScore;
    private String QualityScore;

    public WorkOrder() {}

    // assigned OPEN WORK ORDER Constructor setted ONEVALUATION State
    public WorkOrder(String customerId, String providerId, String specialization,
                     String description, Long creationDate, Long timeLimit, Long stateChangeDate) {
        //this.OrderId = "orderId";
        this.CustomerId = customerId;
        this.ProviderId = providerId;
        this.Specialization = specialization;
        this.Description = description;
        //this.Detail = detail;
        this.CreationDate = creationDate;
        this.TimeLimit = timeLimit;
        this.State = "ONEVALUATION";
        this.StateChangeDate = stateChangeDate;
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
    public WorkOrder(String customerId, String specialization,
                     String description, Long creationDate, Long timeLimit, Long stateChangeDate) {
        //this.OrderId = "orderId";
        this.CustomerId = customerId;
        //this.ProviderId = providerId;
        this.Specialization = specialization;
        this.Description = description;
        //this.Detail = detail;
        this.CreationDate = creationDate;
        this.TimeLimit = timeLimit;
        this.State = "OPEN";
        this.StateChangeDate = stateChangeDate;
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



    public String getOrderId() {
        return OrderId;
    }
    public void setOrderId(String pOrderId){
        OrderId=pOrderId;
    }

    public String getCustomerId() {
        return CustomerId;
    }
    public void setCustomerId(String pCustomerId){
        CustomerId=pCustomerId;
    }

    public String getProviderId() {
        return ProviderId;
    }
    public void setProviderId(String pProviderId){
        ProviderId=pProviderId;
    }

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

    public String getInspectionCharges() {
        return InspectionCharges;
    }
    public void setInspectionCharges(String pInspectionCharges){ InspectionCharges=pInspectionCharges; }

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

    public String getWorkCost() {
        return WorkCost;
    }
    public void setWorkCost(String pWorkCost){ WorkCost=pWorkCost; }

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

    public String getAppereanceScore() {
        return AppereanceScore;
    }
    public void setAppereanceScore(String pAppereanceScore){ AppereanceScore=pAppereanceScore; }

    public String getCleanlinessScore() {
        return CleanlinessScore;
    }
    public void setCleanlinessScore(String pCleanlinessScore){ CleanlinessScore=pCleanlinessScore; }

    public String getSpeedScore() {
        return SpeedScore;
    }
    public void setSpeedScore(String pSpeedScore){ SpeedScore=pSpeedScore; }

    public String getQualityScore() {
        return QualityScore;
    }
    public void setQualityScore(String pQualityScore){ QualityScore=pQualityScore; }


}
