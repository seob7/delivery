package com.delivery.db.userordermenu

import com.delivery.db.userordermenu.enums.UserOrderMenuStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserOrderMenuRepository : JpaRepository<UserOrderMenuEntity, Long> {
    fun findAllByUserOrderIdAndStatus(
        userOrderId: Long?,
        status: UserOrderMenuStatus?
    ): List<UserOrderMenuEntity>
}
