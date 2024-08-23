package com.delivery.db.store

import com.delivery.db.store.enums.StoreCategory
import com.delivery.db.store.enums.StoreStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StoreRepository : JpaRepository<StoreEntity, Long> {
    // 특정 유효한 스토어
    // select * from store where id = ? and status = ? order by id desc limit 1
    fun findFirstByIdAndStatusOrderByIdDesc(
        id: Long?,
        status: StoreStatus?
    ): StoreEntity?

    // 유효한 스토어 리스트
    // select * from store where status = ? order by id desc
    fun findAllByStatusOrderByIdDesc(status: StoreStatus?): List<StoreEntity>

    // 유효한 특정 카테고리의 스토어 리스트
    fun findAllByStatusAndCategoryOrderByStarDesc(
        status: StoreStatus?,
        category: StoreCategory?
    ): List<StoreEntity>

    fun findFirstByNameAndStatusOrderByIdDesc(
        name: String?,
        status: StoreStatus?
    ): StoreEntity?
}
