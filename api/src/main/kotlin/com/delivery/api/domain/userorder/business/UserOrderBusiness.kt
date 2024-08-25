package com.delivery.api.domain.userorder.business

import com.delivery.api.common.Log
import com.delivery.api.domain.store.converter.StoreConverter
import com.delivery.api.domain.store.service.StoreService
import com.delivery.api.domain.storemenu.converter.StoreMenuConverter
import com.delivery.api.domain.storemenu.service.StoreMenuService
import com.delivery.api.domain.user.model.User
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse
import com.delivery.api.domain.userorder.converter.UserOrderConverter
import com.delivery.api.domain.userorder.producer.UserOrderProducer
import com.delivery.api.domain.userorder.service.UserOrderService
import com.delivery.api.domain.userorderemenu.converter.UserOrderMenuConverter
import com.delivery.api.domain.userorderemenu.service.UserOrderMenuService
import com.delivery.common.annotation.Business
import com.delivery.db.userordermenu.enums.UserOrderMenuStatus

@Business
class UserOrderBusiness(
    private val userOrderService: UserOrderService,
    private val userOrderConverter: UserOrderConverter,

    private val storeMenuService: StoreMenuService,
    private val storeMenuConverter: StoreMenuConverter,

    private val userOrderMenuService: UserOrderMenuService,
    private val userOrderMenuConverter: UserOrderMenuConverter,

    private val storeService: StoreService,
    private val storeConverter: StoreConverter,

    private val userOrderProducer: UserOrderProducer
) {

    companion object : Log

    fun userOrder(
        user: User?,
        body: UserOrderRequest
    ): UserOrderResponse {
        // 가게 찾기
        val storeEntity = storeService.getStoreWithThrow(body.storeId)

        // 주문한 메뉴 찾기
        val storeMenuEntityList = body?.storeMenuIdList?.mapNotNull { it ->
            storeMenuService.getStoreMenuWithThrow(it)
        }?.toList()

        val userOrderEntity = userOrderConverter.toEntity(
            user = user,
            storeEntity = storeEntity,
            storeMenuEntityList = storeMenuEntityList
        ).run {
            userOrderService.order(this)
        }

        val userOrderMenuEntityList = storeMenuEntityList?.map { it ->
            userOrderMenuConverter.toEntity(userOrderEntity, it)
        }?.toList()

        userOrderMenuEntityList?.forEach { it -> userOrderMenuService.order(it) }

        userOrderProducer.sendOrder(userOrderEntity)

        return userOrderConverter.toResponse(userOrderEntity)

    }

    fun current(
        user: User?
    ): List<UserOrderDetailResponse> {
        return userOrderService.current(user?.id).map { userOrderEntity ->
            val storeMenuEntityList = userOrderEntity.userOrderMenuList?.stream()
                ?.filter { userOrderMenuEntity -> userOrderMenuEntity.status == UserOrderMenuStatus.REGISTERED }
                ?.map { userOrderMenuEntity -> userOrderMenuEntity.storeMenu }
                ?.toList()

            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                storeResponse = storeConverter.toResponse(userOrderEntity.store),
                storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }.toList()
    }

    fun history(
        user: User?
    ): List<UserOrderDetailResponse> {
        return userOrderService.history(user?.id).map { userOrderEntity ->
            val storeMenuEntityList = userOrderEntity.userOrderMenuList?.stream()
                ?.filter { userOrderMenuEntity -> userOrderMenuEntity.status == UserOrderMenuStatus.REGISTERED }
                ?.map { userOrderMenuEntity -> userOrderMenuEntity.storeMenu }
                ?.toList()

            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                storeResponse = storeConverter.toResponse(userOrderEntity.store),
                storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }.toList()
    }

    fun read(
        user: User?,
        orderId: Long?
    ): UserOrderDetailResponse {
        return userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user?.id)
            .let { userOrderEntity ->

                val storeMenuEntityList = userOrderEntity.userOrderMenuList?.stream()
                    ?.filter { it.status == UserOrderMenuStatus.REGISTERED }
                    ?.map { it.storeMenu }
                    ?.toList()
                    ?: listOf() //null 일때 빈값을 전달



                UserOrderDetailResponse(
                    userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                    storeResponse = storeConverter.toResponse(userOrderEntity.store),
                    storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
                )
            }
    }
}