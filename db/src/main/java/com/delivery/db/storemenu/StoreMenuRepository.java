package com.delivery.db.storemenu;

import com.delivery.db.storemenu.enums.StoreMenuStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface StoreMenuRepository extends JpaRepositoryImplementation<StoreMenuEntity, Long> {

    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus status);

    List<StoreMenuEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId,
        StoreMenuStatus status);

}
