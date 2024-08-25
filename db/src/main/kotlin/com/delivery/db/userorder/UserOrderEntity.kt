package com.delivery.db.userorder

import com.delivery.db.store.StoreEntity
import com.delivery.db.userorder.enums.UserOrderStatus
import com.delivery.db.userordermenu.UserOrderMenuEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "user_order")
class UserOrderEntity(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null,

    @field:Column(nullable = false)
    var userId: Long?=null,

    @field:JoinColumn(nullable = false, name = "store_id")
    @field:ManyToOne
    var store: StoreEntity?=null,

    @field:Enumerated(EnumType.STRING)
    @field:Column(length = 50, nullable = false)
    var status: UserOrderStatus?=null,

    @field:Column(precision = 11, scale = 4, nullable = false)
    var amount: BigDecimal?=null,

    var orderedAt: LocalDateTime?=null,

    var acceptedAt: LocalDateTime?=null,

    var cookingStartedAt: LocalDateTime?=null,

    var deliveryStartedAt: LocalDateTime?=null,

    var receivedAt: LocalDateTime?=null,

    @field:OneToMany(mappedBy = "userOrder")
    @field:JsonIgnore
    var userOrderMenuList: MutableList<UserOrderMenuEntity>?=null
){

    override fun toString(): String {
        return "UserOrderEntity(id=$id, userId=$userId, store=$store, status=$status, amount=$amount, orderedAt=$orderedAt, acceptedAt=$acceptedAt, cookingStartedAt=$cookingStartedAt, deliveryStartedAt=$deliveryStartedAt, receivedAt=$receivedAt)"
    }
}