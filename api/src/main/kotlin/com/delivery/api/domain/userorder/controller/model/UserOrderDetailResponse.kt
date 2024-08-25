package com.delivery.api.domain.userorder.controller.model

import com.delivery.api.domain.store.controller.model.StoreResponse
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse

data class UserOrderDetailResponse (
    val userOrderResponse: UserOrderResponse? = null,
    val storeResponse: StoreResponse? = null,
    val storeMenuResponseList: List<StoreMenuResponse>? = null
)
