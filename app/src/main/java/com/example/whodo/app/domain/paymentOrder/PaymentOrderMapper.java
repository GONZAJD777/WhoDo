package com.example.whodo.app.domain.paymentOrder;

public class PaymentOrderMapper {

    public static PaymentOrderDTO toDTO(PaymentOrder paymentOrder) {
        PaymentOrderDTO dto = new PaymentOrderDTO();
        dto.setOrderId(paymentOrder.getOrderId());
        dto.setPayerId(paymentOrder.getPayerId());
        dto.setPayeeId(paymentOrder.getPayeeId());
        dto.setDetail(paymentOrder.getDetail());
        dto.setCreationDate(paymentOrder.getCreationDate());
        dto.setPaymentDate(paymentOrder.getPaymentDate());
        dto.setState(paymentOrder.getState());
        dto.setAmount(paymentOrder.getAmount());
        dto.setInvoiceHash(paymentOrder.getInvoiceHash());
        dto.setInvoiceCreationDate(paymentOrder.getInvoiceCreationDate());
        dto.setInvoiceValidPeriod(paymentOrder.getInvoiceValidPeriod());
        return dto;
    }

    public static PaymentOrder toEntity(PaymentOrderDTO dto) {
        PaymentOrder entity = new PaymentOrder();
        entity.setOrderId(dto.getOrderId());
        entity.setPayerId(dto.getPayerId());
        entity.setPayeeId(dto.getPayeeId());
        entity.setDetail(dto.getDetail());
        entity.setCreationDate(dto.getCreationDate());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setState(dto.getState());
        entity.setAmount(dto.getAmount());
        entity.setInvoiceHash(dto.getInvoiceHash());
        entity.setInvoiceCreationDate(dto.getInvoiceCreationDate());
        entity.setInvoiceValidPeriod(dto.getInvoiceValidPeriod());
        return entity;
    }
}

