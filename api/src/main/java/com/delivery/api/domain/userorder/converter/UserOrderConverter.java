package com.delivery.api.domain.userorder.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.userorder.UserOrderEntity;
import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {

    public UserOrderEntity toEntity(User user, Long storeId,
        List<StoreMenuEntity> storeMenuEntityList) {

        BigDecimal totalAmount = storeMenuEntityList.stream().map(it -> it.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return UserOrderEntity.builder()
            .userId(user.getId())
            .storeId(storeId)
            .amount(totalAmount)
            .build();
    }

    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity) {
        return UserOrderResponse.builder()
            .id(userOrderEntity.getId())
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
