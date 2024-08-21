package com.delivery.storeadmin.domain.userorder.controller.model;

import com.delivery.db.userorder.enums.UserOrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderResponse {

    private Long id;

    private Long storeId;

    private Long userId;

    private UserOrderStatus status;

    private BigDecimal amount;

    private LocalDateTime orderedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime cookingStoredAt;

    private LocalDateTime deliveryStartedAt;

    private LocalDateTime receivedAt;

}
