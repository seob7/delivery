package com.delivery.storeadmin.domain.userorder.converter;

import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.storeadmin.common.annotation.Converter;
import com.delivery.storeadmin.domain.userorder.controller.model.UserOrderResponse;

@Converter
public class UserOrderConverter {

    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity) {
        return UserOrderResponse.builder()
            .id(userOrderEntity.getId())
            .userId(userOrderEntity.getUserId())
            .storeId(userOrderEntity.getStore().getId())
            .status(userOrderEntity.getStatus())
            .amount(userOrderEntity.getAmount())
            .orderedAt(userOrderEntity.getOrderedAt())
            .acceptedAt(userOrderEntity.getAcceptedAt())
            .cookingStoredAt(userOrderEntity.getCookingStoredAt())
            .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
            .receivedAt(userOrderEntity.getReceivedAt())
            .build();
    }

}
