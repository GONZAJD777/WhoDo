package com.example.whodo.BusinessClasses;

public class WorkOrder {

    private String OrderId;
    private String CustomerId;
    private String ProviderId;
    private String Detail;
    private String CreationDate;
    private String State; //esta variable ira marcando el ciclo de vida de la orden, desde q se crea hasta q es cerrada el cambio de estado requerira acciones de Customer y provider.
    private String InspectionDate;
    private String InspectionCharges;
    private String InspectionPaymentOrder;
    private String WorkStartDate;
    private String WorkEndDate;
    private String WorkCost;
    private String WorkPaymentOrder;
    private String WorkWarrantyEndDate;
    private String AppereanceScore;
    private String CleanlinessScore;
    private String SpeedScore;
    private String QualityScore;

    public WorkOrder() {}

    public WorkOrder(String pCustomerId,String pDetail){
        OrderId="";
        CustomerId=pCustomerId;
        ProviderId="";
        Detail=pDetail;
        CreationDate="";
        State="";
        InspectionDate="";
        InspectionCharges="";
        InspectionPaymentOrder="";
        WorkStartDate="";
        WorkEndDate="";
        WorkCost="";
        WorkPaymentOrder="";
        WorkWarrantyEndDate="";
        AppereanceScore="";
        CleanlinessScore="";
        SpeedScore="";
        QualityScore="";

    }

    public WorkOrder(String pCustomerId,String pProviderId,String pDetail){
        OrderId="";
        CustomerId=pCustomerId;
        ProviderId=pProviderId;
        Detail=pDetail;
        CreationDate="";
        State="";
        InspectionDate="";
        InspectionCharges="";
        InspectionPaymentOrder="";
        WorkStartDate="";
        WorkEndDate="";
        WorkCost="";
        WorkPaymentOrder="";
        WorkWarrantyEndDate="";
        AppereanceScore="";
        CleanlinessScore="";
        SpeedScore="";
        QualityScore="";

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

    public String getState() {
        return State;
    }
    public void setState(String pState){
        State=pState;
    }

    public String getInspectionDate() {
        return InspectionDate;
    }
    public void setInspectionDate(String pInspectionDate){
        State=pInspectionDate;
    }

    public String getInspectionCharges() {
        return InspectionCharges;
    }
    public void setInspectionCharges(String pInspectionCharges){ InspectionCharges=pInspectionCharges; }

    public String getInspectionPaymentOrder() {
        return InspectionPaymentOrder;
    }
    public void setInspectionPaymentOrder(String pInspectionPaymentOrder){ InspectionPaymentOrder=pInspectionPaymentOrder; }

    public String getWorkStartDate() {
        return WorkStartDate;
    }
    public void setWorkStartDate(String pWorkStartDate){ WorkStartDate=pWorkStartDate; }

    public String getWorkEndDate() {
        return WorkEndDate;
    }
    public void setWorkEndDate(String pWorkEndDate){ WorkEndDate=pWorkEndDate; }

    public String getWorkCost() {
        return WorkCost;
    }
    public void setWorkCost(String pWorkCost){ WorkCost=pWorkCost; }

    public String getWorkPaymentOrder() {
        return WorkPaymentOrder;
    }
    public void setWorkPaymentOrder(String pWorkPaymentOrder){ WorkPaymentOrder=pWorkPaymentOrder; }

    public String getWorkWarrantyEndDate() {
        return WorkWarrantyEndDate;
    }
    public void setWorkWarrantyEndDate(String pWorkWarrantyEndDate){ WorkWarrantyEndDate=pWorkWarrantyEndDate; }

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
