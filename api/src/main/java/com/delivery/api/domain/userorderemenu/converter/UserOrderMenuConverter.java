package com.delivery.api.domain.userorderemenu.converter;

import com.delivery.common.annotation.Converter;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.db.userordermenu.UserOrderMenuEntity;

@Converter
public class UserOrderMenuConverter {

    public UserOrderMenuEntity toEntity(UserOrderEntity userOrderEntity, StoreMenuEntity storeMenuEntity) {
        return UserOrderMenuEntity.builder()
            .userOrderId(userOrderEntity.getId())
            .storeMenuId(storeMenuEntity.getId())
            .build();
    }
}
