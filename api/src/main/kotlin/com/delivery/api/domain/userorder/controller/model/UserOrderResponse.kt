package com.delivery.api.domain.userorder.controller.model

import com.delivery.db.userorder.enums.UserOrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class UserOrderResponse(

    var id: Long? = null,

    var status: UserOrderStatus? = null,

    var amount: BigDecimal? = null,

    var orderedAt: LocalDateTime? = null,

    var acceptedAt: LocalDateTime? = null,

    var cookingStoredAt: LocalDateTime? = null,

    var deliveryStartedAt: LocalDateTime? = null,

    var receivedAt: LocalDateTime? = null
)