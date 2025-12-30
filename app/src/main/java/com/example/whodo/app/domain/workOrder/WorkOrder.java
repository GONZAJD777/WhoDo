package com.example.whodo.app.domain.workOrder;

import com.example.whodo.app.domain.Location;
import com.example.whodo.app.domain.Phone;

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
public class WorkOrder {

    private String orderId;
    @Builder.Default
    private Customer customer = new Customer();
    @Builder.Default
    private Provider provider = new Provider();
    private String specialization;
    private String description;
    private String creationDate;
    private String timeLimit;
    private String stateChangeDate;
    private String state;
    @Builder.Default
    private Inspection inspection = new Inspection();
    @Builder.Default
    private Work work = new Work();
    @Builder.Default
    private Feedback feedback = new Feedback();

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Customer {
        private String customerId;
        private String customerName;
        private String customerAddress;
        private Location customerLocation;
        private Phone customerPhone;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Provider {
        private String providerId;
        private String providerName;
        private String providerAddress;
        private Location providerLocation;
        private Phone providerPhone;

    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Inspection {
        private String inspectionDate;
        private Integer inspectionCharges;
        private String inspectionTimeLimit;
        private String inspectionPaymentOrder;
        private Integer inspectionFee;
        private String inspectionFullfilment;
        private String inspectionRescheduled;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Work {
        private String proposalTimeLimitDate;
        private String workStartDate;
        private String workEndDate;
        private Integer workLaborCost;
        private Integer workMaterialsCost;
        private Integer workFee;
        private String detail;
        private String workPaymentOrder;
        private String workWarrantyEndDate;
        private Integer workLimitTimeExtension;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Feedback {
        private String impressions;
        private Integer appereanceScore;
        private Integer cleanlinessScore;
        private Integer speedScore;
        private Integer qualityScore;

    }

}
