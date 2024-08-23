package com.delivery.api.domain.storemenu.business;

import com.delivery.api.domain.store.service.StoreService;
import com.delivery.common.annotation.Business;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import com.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import com.delivery.db.storemenu.StoreMenuEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreService storeService;

    private final StoreMenuConverter storeMenuConverter;

    public StoreMenuResponse register(StoreMenuRegisterRequest request) {
        var storeEntity = storeService.getStoreWithThrow(request.getStoreId());
        StoreMenuEntity entity = storeMenuConverter.toEntity(request, storeEntity);
        StoreMenuEntity newEntity = storeMenuService.register(entity);
        return storeMenuConverter.toResponse(newEntity);
    }

    public List<StoreMenuResponse> search(Long storeId) {
        List<StoreMenuEntity> list = storeMenuService.getStoreMenuByStoreId(storeId);
        return list.stream().map(entity -> storeMenuConverter.toResponse(entity)).toList();
    }

}
