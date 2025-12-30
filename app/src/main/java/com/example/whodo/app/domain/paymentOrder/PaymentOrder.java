package com.example.whodo.app.domain.paymentOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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


}
