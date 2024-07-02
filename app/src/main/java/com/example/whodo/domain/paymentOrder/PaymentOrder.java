package com.example.whodo.domain.paymentOrder;

public class PaymentOrder {

    private String OrderId;
    private String PayerId;
    private String PayeeId;
    private String Detail;
    private String CreationDate;
    private String PaymentDate;
    private String State; //
    private String Amount;
    private String InvoiceHash;
    private String InvoiceCreationDate;
    private String InvoiceValidPeriod;

    PaymentOrder(){ }

    PaymentOrder(String pPayer, String pPayee, String pAmount){
        OrderId="";
        PayerId=pPayer;
        PayeeId=pPayee;
        Detail="";
        CreationDate="";
        PaymentDate="";
        State=""; //
        Amount=pAmount;
        InvoiceHash="";
        InvoiceCreationDate="";
        InvoiceValidPeriod="";
    }

    public String getOrderId() {
        return OrderId;
    }
    public void setOrderId(String pOrderId){
        OrderId=pOrderId;
    }

    public String getPayerId() {
        return PayerId;
    }
    public void setPayerId(String pPayerId){
        PayerId=pPayerId;
    }

    public String getPayeeId() {
        return PayeeId;
    }
    public void setPayeeId(String pPayeeId){
        PayeeId=pPayeeId;
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

    public String getPaymentDate() {
        return PaymentDate;
    }
    public void setPaymentDate(String pPaymentDate){
        PaymentDate=pPaymentDate;
    }

    public String getState() {
        return State;
    }
    public void setState(String pState){
        State=pState;
    }

    public String getAmount() {
        return Amount;
    }
    public void setAmount(String pAmount){
        Amount=pAmount;
    }

    public String getInvoiceHash() {
        return InvoiceHash;
    }
    public void setInvoiceHash(String pInvoiceHash){
        InvoiceHash=pInvoiceHash;
    }

    public String getInvoiceCreationDate() {
        return InvoiceCreationDate;
    }
    public void setInvoiceCreationDate(String pInvoiceCreationDate){
        InvoiceCreationDate=pInvoiceCreationDate;
    }

    public String getInvoiceValidPeriod() {
        return InvoiceValidPeriod;
    }
    public void setInvoiceValidPeriod(String pInvoiceValidPeriod){
        InvoiceValidPeriod=pInvoiceValidPeriod;
    }


}
