package com.example.whodo.app.domain.paymentOrder;

import lombok.*;
import java.util.List;
import java.util.Map;
import com.google.gson.annotations.SerializedName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private List<ItemRequest> items;

    @SerializedName("back_urls")
    private BackUrlsRequest backUrls;

    @SerializedName("notification_url")
    private String notificationUrl;

    @SerializedName("external_reference")
    private String externalReference;

    private Map<String, Object> metadata;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemRequest {
        private String id;
        private String title;
        private String description;

        @SerializedName("picture_url")
        private String pictureUrl;

        @SerializedName("category_id")
        private String categoryId;

        private Integer quantity;

        @SerializedName("currency_id")
        private String currencyId;

        @SerializedName("unit_price")
        private java.math.BigDecimal unitPrice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BackUrlsRequest {
        private String success;
        private String pending;
        private String failure;
    }
}

