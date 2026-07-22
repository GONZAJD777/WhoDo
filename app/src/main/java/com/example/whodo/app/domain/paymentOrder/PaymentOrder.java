package com.example.whodo.app.domain.paymentOrder;

import com.example.whodo.app.domain.DateUtcOffset;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    private String id;                  // PK interna (se mapea a _id en Mongo)
    private String workOrderId;         // Relación con tu WorkOrder
    private String payerId;             // Usuario que paga
    private String payeeId;             // Usuario que recibe
    private String reason;              // Motivo del pago
    private BigDecimal amount;          // Monto esperado
    private String state; // Enum con estados
    private String provider;            // MERCADO_PAGO, STRIPE, etc.
    private DateUtcOffset creationDate;
    private DateUtcOffset lastUpdated;
    private DateUtcOffset paymentDate;
    private Preference preference;      // Subdocumento con info de la preferencia
    private List<MerchantOrder> merchantOrders; // Lista de órdenes asociadas

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Preference {
        private String id;
        private Map<String, Object> snapshot;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MerchantOrder {
        private String id;
        private String status;
        private Map<String, Object> snapshot;
        private List<PaymentAttempt> payments;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentAttempt {
        private String id;
        private String status;
        private BigDecimal transactionAmount;
        private String dateCreated;
    }
}
