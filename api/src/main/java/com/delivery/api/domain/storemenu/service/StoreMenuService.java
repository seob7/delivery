package com.delivery.api.domain.storemenu.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.storemenu.StoreMenuRepository;
import com.delivery.db.storemenu.enums.StoreMenuStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id) {
        return storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id,
            StoreMenuStatus.REGISTERED).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

    }

    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId) {
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId,
            StoreMenuStatus.REGISTERED);
    }

    public StoreMenuEntity register(StoreMenuEntity storeMenuEntity) {
        return Optional.ofNullable(storeMenuEntity).map(entity -> {
            entity.setStatus(StoreMenuStatus.REGISTERED);
            return storeMenuRepository.save(entity);
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
