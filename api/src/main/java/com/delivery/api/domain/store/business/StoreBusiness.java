package com.delivery.api.domain.store.business;

import com.delivery.common.annotation.Business;
import com.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.api.domain.store.converter.StoreConverter;
import com.delivery.api.domain.store.service.StoreService;
import com.delivery.db.store.StoreEntity;
import com.delivery.db.store.enums.StoreCategory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(StoreRegisterRequest storeRegisterRequest) {
        StoreEntity entity = storeConverter.toEntity(storeRegisterRequest);
        StoreEntity newEntity = storeService.register(entity);
        return storeConverter.toResponse(newEntity);
    }

    public List<StoreResponse> searchCategory(StoreCategory category) {
        List<StoreEntity> storeList = storeService.searchByCategory(category);
        return storeList.stream().map(entity -> storeConverter.toResponse(entity)).toList();
    }

}
