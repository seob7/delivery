package com.delivery.api.domain.store.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.store.StoreEntity;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreCategory;
import com.delivery.db.store.enums.StoreStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 유효한 스토어 가져오기
    public StoreEntity getStoreWithThrow(Long id) {
        return storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED)
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 스토어 등록
    public StoreEntity register(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity).map(entity -> {
            entity.setStar(0);
            entity.setStatus(StoreStatus.REGISTERED);
            return storeRepository.save(entity);
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 카테고리로 스토어 검색
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory) {
        return storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
            StoreStatus.REGISTERED, storeCategory);
    }

    // 전체 스토어
    public List<StoreEntity> registerStore() {
        return storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
    }
}
