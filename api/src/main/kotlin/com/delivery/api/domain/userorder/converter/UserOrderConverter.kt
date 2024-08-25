package com.delivery.api.domain.userorder.converter

import com.delivery.api.domain.user.model.User
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse
import com.delivery.common.annotation.Converter
import com.delivery.db.store.StoreEntity
import com.delivery.db.storemenu.StoreMenuEntity
import com.delivery.db.userorder.UserOrderEntity

@Converter
class UserOrderConverter {
    fun toEntity(
        user: User?,
        storeEntity: StoreEntity?,
        storeMenuEntityList: List<StoreMenuEntity>?

    ): UserOrderEntity {
        val totalAmout = storeMenuEntityList?.mapNotNull {
            it -> it.amount
        }?.reduce{acc, bigDecimal -> acc.add(bigDecimal)}

        return UserOrderEntity(
            userId = user?.id,
            store = storeEntity,
            amount = totalAmout,
        )
    }

    fun toResponse(
        userOrderEntity: UserOrderEntity?
    ): UserOrderResponse {
        return UserOrderResponse(
            id = userOrderEntity?.id,
            status =userOrderEntity?.status,
            amount = userOrderEntity?.amount,
            orderedAt = userOrderEntity?.orderedAt,
            acceptedAt = userOrderEntity?.acceptedAt,
            cookingStoredAt = userOrderEntity?.cookingStartedAt,
            deliveryStartedAt = userOrderEntity?.deliveryStartedAt,
            receivedAt = userOrderEntity?.receivedAt
        )
    }
}