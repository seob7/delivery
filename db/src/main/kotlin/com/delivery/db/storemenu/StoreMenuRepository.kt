package com.delivery.db.storemenu

import com.delivery.db.storemenu.enums.StoreMenuStatus
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import java.util.*

interface StoreMenuRepository : JpaRepositoryImplementation<StoreMenuEntity, Long> {
    fun findFirstByIdAndStatusOrderByIdDesc(
        id: Long?,
        status: StoreMenuStatus?
    ): StoreMenuEntity?

    fun findAllByStoreIdAndStatusOrderBySequenceDesc(
        storeId: Long?,
        status: StoreMenuStatus?
    ): List<StoreMenuEntity>
}
