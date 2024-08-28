package com.delivery.api.domain.userorder.controller.model

import com.delivery.api.domain.store.controller.model.StoreResponse
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse

data class UserOrderDetailResponse (
    var userOrderResponse: UserOrderResponse? = null,
    var storeResponse: StoreResponse? = null,
    var storeMenuResponseList: List<StoreMenuResponse>? = null,
)