package com.example.whodo.app.domain.workOrder;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.whodo.app.domain.user.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WorkOrderApiRestRequestDTO {
    private String orderId;
    private Customer customer;
    private Provider provider;
    private String specialization;
    private String description;
    private WorkOrderDate creationDate;
    private WorkOrderDate timeLimit;
    private WorkOrderDate stateChangeDate;
    private String state;
    private Inspection inspection;
    private Work work;
    private Feedback feedback;


    public WorkOrderApiRestRequestDTO() {
        this.customer = new Customer();
        this.provider = new Provider();
        this.inspection= new Inspection();
        this.work = new Work();
        this.feedback = new Feedback();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String pSpecialization) {
        specialization = pSpecialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public WorkOrderDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(WorkOrderDate pCreationDate) {
        creationDate = pCreationDate;
    }

    public WorkOrderDate getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(WorkOrderDate pTimeLimit) {
        timeLimit = pTimeLimit;
    }

    public WorkOrderDate getStateChangeDate() {
        return stateChangeDate;
    }

    public void setStateChangeDate(WorkOrderDate pStateChangeDate) {
        stateChangeDate = pStateChangeDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String pState) {
        state = pState;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer pCustomer) {
        this.customer = pCustomer;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider pProvider) {
        this.provider = pProvider;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection pInspection) {
        this.inspection = pInspection;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work pWork) {
        this.work = pWork;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback pFeedback) {
        this.feedback = pFeedback;
    }

    public static class Customer {
        private String customerId;
        private String customerName;
        private String customerAddress;
        private User.Location customerLocation;
        private User.Phone customerPhone;

        // Getters y setters
        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String pustomerId) {
            customerId = pustomerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String pustomerName) {
            customerName = pustomerName;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String pustomerAddress) {
            customerAddress = pustomerAddress;
        }

        public User.Location getCustomerLocation() {
            return customerLocation;
        }

        public void setCustomerLocation(User.Location customerLocation) {
            this.customerLocation = customerLocation;
        }

        public void setCustomerPhone(User.Phone customerPhone) {
            this.customerPhone = customerPhone;
        }

        public User.Phone getCustomerPhone() {
            return customerPhone;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "customerId='" + customerId + '\'' +
                    ", customerName='" + customerName + '\'' +
                    ", customerAddress='" + customerAddress + '\'' +
                    ", customerLocation=" + customerLocation +
                    ", customerPhoneNumber=" + customerPhone +
                    '}';
        }
    }
    public static class Provider {
        private String providerId;
        private String providerName;
        private String providerAddress;
        private User.Location providerLocation;
        private User.Phone providerPhone;

        // Getters y setters

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getProviderAddress() {
            return providerAddress;
        }

        public void setProviderAddress(String providerAddress) {
            this.providerAddress = providerAddress;
        }

        public User.Location getProviderLocation() {
            return providerLocation;
        }

        public void setProviderLocation(User.Location providerLocation) {
            this.providerLocation = providerLocation;
        }

        public User.Phone getProviderPhone() {
            return providerPhone;
        }

        public void setProviderPhone(User.Phone providerPhone) {
            this.providerPhone = providerPhone;
        }

        @Override
        public String toString() {
            return "Provider{" +
                    "providerId='" + providerId + '\'' +
                    ", providerName='" + providerName + '\'' +
                    ", providerAddress='" + providerAddress + '\'' +
                    ", providerLocation=" + providerLocation +
                    ", providerPhoneNumber=" + providerPhone +
                    '}';
        }
    }
    public static class Inspection {
        private WorkOrderDate inspectionDate;
        private Integer inspectionCharges;
        private WorkOrderDate inspectionTimeLimit;
        private String inspectionPaymentOrder;
        private Integer inspectionFee;
        private String inspectionFullfilment;
        private String inspectionRescheduled;

        // Getters y setters

        public WorkOrderDate getInspectionDate() {
            return inspectionDate;
        }

        public void setInspectionDate(WorkOrderDate pInspectionDate) {
            inspectionDate = pInspectionDate;
        }

        public Integer getInspectionCharges() {
            return inspectionCharges;
        }

        public void setInspectionCharges(Integer pInspectionCharges) {
            inspectionCharges = pInspectionCharges;
        }

        public WorkOrderDate getInspectionTimeLimit() {
            return inspectionTimeLimit;
        }

        public void setInspectionTimeLimit(WorkOrderDate pInspectionTimeLimit) {
            inspectionTimeLimit = pInspectionTimeLimit;
        }

        public String getInspectionPaymentOrder() {
            return inspectionPaymentOrder;
        }

        public void setInspectionPaymentOrder(String pInspectionPaymentOrder) {
            inspectionPaymentOrder = pInspectionPaymentOrder;
        }

        public Integer getInspectionFee() {
            return inspectionFee;
        }

        public void setInspectionFee(Integer pInspectionFee) {
            inspectionFee = pInspectionFee;
        }

        public String getInspectionFullfilment() {
            return inspectionFullfilment;
        }

        public void setInspectionFullfilment(String pInspectionFullfilment) {
            inspectionFullfilment = pInspectionFullfilment;
        }

        public String getInspectionRescheduled() {
            return inspectionRescheduled;
        }

        public void setInspectionRescheduled(String pInspectionRescheduled) {
            inspectionRescheduled = pInspectionRescheduled;
        }

        @Override
        public String toString() {
            return "Inspection{" +
                    "inspectionDate=" + inspectionDate +
                    ", inspectionCharges=" + inspectionCharges +
                    ", inspectionTimeLimit=" + inspectionTimeLimit +
                    ", inspectionPaymentOrder='" + inspectionPaymentOrder + '\'' +
                    ", inspectionFee=" + inspectionFee +
                    ", inspectionFullfilment='" + inspectionFullfilment + '\'' +
                    ", inspectionRescheduled='" + inspectionRescheduled + '\'' +
                    '}';
        }
    }
    public static class Work {
        private WorkOrderDate proposalTimeLimitDate;
        private WorkOrderDate workStartDate;
        private WorkOrderDate workEndDate;
        private Integer workLaborCost;
        private Integer workMaterialsCost;
        private Integer workFee;
        private String detail;
        private String workPaymentOrder;
        private WorkOrderDate workWarrantyEndDate;
        private Integer workLimitTimeExtension;

        // Getters y setters

        public WorkOrderDate getProposalTimeLimitDate() {
            return proposalTimeLimitDate;
        }

        public void setProposalTimeLimitDate(WorkOrderDate pProposalTimeLimitDate) {
            proposalTimeLimitDate = pProposalTimeLimitDate;
        }

        public WorkOrderDate getWorkStartDate() {
            return workStartDate;
        }

        public void setWorkStartDate(WorkOrderDate pWorkStartDate) {
            workStartDate = pWorkStartDate;
        }

        public WorkOrderDate getWorkEndDate() {
            return workEndDate;
        }

        public void setWorkEndDate(WorkOrderDate pWorkEndDate) {
            workEndDate = pWorkEndDate;
        }

        public Integer getWorkLaborCost() {
            return workLaborCost;
        }

        public void setWorkLaborCost(Integer pWorkLaborCost) {
            workLaborCost = pWorkLaborCost;
        }

        public Integer getWorkMaterialsCost() {
            return workMaterialsCost;
        }

        public void setWorkMaterialsCost(Integer pWorkMaterialsCost) {
            workMaterialsCost = pWorkMaterialsCost;
        }

        public Integer getWorkFee() {
            return workFee;
        }

        public void setWorkFee(Integer pWorkFee) {
            workFee = pWorkFee;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String pDetail) {
            detail = pDetail;
        }

        public String getWorkPaymentOrder() {
            return workPaymentOrder;
        }

        public void setWorkPaymentOrder(String pWorkPaymentOrder) {
            workPaymentOrder = pWorkPaymentOrder;
        }

        public WorkOrderDate getWorkWarrantyEndDate() {
            return workWarrantyEndDate;
        }

        public void setWorkWarrantyEndDate(WorkOrderDate pWorkWarrantyEndDate) {
            workWarrantyEndDate = pWorkWarrantyEndDate;
        }

        public Integer getWorkLimitTimeExtension() {
            return workLimitTimeExtension;
        }

        public void setWorkLimitTimeExtension(Integer pWorkLimitTimeExtension) {
            workLimitTimeExtension = pWorkLimitTimeExtension;
        }

        @Override
        public String toString() {
            return "Work{" +
                    "proposalTimeLimitDate=" + proposalTimeLimitDate +
                    ", workStartDate=" + workStartDate +
                    ", workEndDate=" + workEndDate +
                    ", workLaborCost=" + workLaborCost +
                    ", workMaterialsCost=" + workMaterialsCost +
                    ", workFee=" + workFee +
                    ", detail='" + detail + '\'' +
                    ", workPaymentOrder='" + workPaymentOrder + '\'' +
                    ", workWarrantyEndDate=" + workWarrantyEndDate +
                    ", workLimitTimeExtension=" + workLimitTimeExtension +
                    '}';
        }
    }
    public static class Feedback {
        private String impressions;
        private Integer appereanceScore;
        private Integer cleanlinessScore;
        private Integer speedScore;
        private Integer qualityScore;

        // Getters y setters

        public String getImpressions() {
            return impressions;
        }

        public void setImpressions(String pmpressions) {
            impressions = pmpressions;
        }

        public Integer getAppereanceScore() {
            return appereanceScore;
        }

        public void setAppereanceScore(Integer pppereanceScore) {
            appereanceScore = pppereanceScore;
        }

        public Integer getCleanlinessScore() {
            return cleanlinessScore;
        }

        public void setCleanlinessScore(Integer pleanlinessScore) {
            cleanlinessScore = pleanlinessScore;
        }

        public Integer getSpeedScore() {
            return speedScore;
        }

        public void setSpeedScore(Integer ppeedScore) {
            speedScore = ppeedScore;
        }

        public Integer getQualityScore() {
            return qualityScore;
        }

        public void setQualityScore(Integer pualityScore) {
            qualityScore = pualityScore;
        }

        @Override
        public String toString() {
            return "Feedback{" +
                    "impressions='" + impressions + '\'' +
                    ", appereanceScore=" + appereanceScore +
                    ", cleanlinessScore=" + cleanlinessScore +
                    ", speedScore=" + speedScore +
                    ", qualityScore=" + qualityScore +
                    '}';
        }
    }
    public static class WorkOrderDate {
        private String dateUtc; // Fecha almacenada en UTC
        private String timeZoneOffset; // Desplazamiento horario en formato ±hh:mm

        public WorkOrderDate() {}

        public WorkOrderDate(String isoDate) {
            try {
                System.out.println("Date To Parse send --> " + isoDate);

                // Parseamos usando java.time
                OffsetDateTime odt = OffsetDateTime.parse(isoDate);
                OffsetDateTime utcDate = odt.withOffsetSameInstant(ZoneOffset.UTC);

                // Formateamos la fecha en formato UTC con milisegundos y zona horaria
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                this.dateUtc = utcDate.format(formatter);

                // Offset original, por ejemplo "+00:00"
                this.timeZoneOffset = odt.getOffset().toString();

                System.out.println("Formatted Date UTC --> " + this.dateUtc);
                System.out.println("Offset --> " + this.timeZoneOffset);

            } catch (Exception e) {
                throw new RuntimeException("Error al convertir la fecha: " + e.getMessage());
            }
        }

        public WorkOrderDate(String dateUtc, String timeZoneOffset) {
            this.dateUtc = dateUtc;
            this.timeZoneOffset = timeZoneOffset;
        }

        public String getDateUtc() { return dateUtc; }
        public void setDateUtc(String dateUtc) { this.dateUtc = dateUtc; }

        public String getTimeZoneOffset() { return timeZoneOffset; }
        public void setTimeZoneOffset(String timeZoneOffset) { this.timeZoneOffset = timeZoneOffset; }

        @Override
        public String toString() {
            return "WorkOrderDate{" +
                    "dateUtc=" + dateUtc +
                    ", timeZoneOffset='" + timeZoneOffset + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "WorkOrder{" +
                "orderId='" + orderId + '\'' +
                ", customer=" + customer.toString() +
                ", provider=" + provider.toString() +
                ", specialization='" + specialization + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", timeLimit=" + timeLimit +
                ", stateChangeDate=" + stateChangeDate +
                ", state='" + state + '\'' +
                ", inspection=" + inspection.toString() +
                ", work=" + work.toString() +
                ", feedback=" + feedback.toString() +
                '}';
    }
}