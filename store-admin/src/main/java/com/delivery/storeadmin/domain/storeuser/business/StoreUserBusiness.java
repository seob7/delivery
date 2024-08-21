package com.delivery.storeadmin.domain.storeuser.business;

import com.delivery.db.store.StoreEntity;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storeadmin.common.annotation.Business;
import com.delivery.storeadmin.domain.storeuser.controller.model.StoreUserRegisterRequest;
import com.delivery.storeadmin.domain.storeuser.controller.model.StoreUserResponse;
import com.delivery.storeadmin.domain.storeuser.converter.StoreUserConverter;
import com.delivery.storeadmin.domain.storeuser.service.StoreUserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;

    private final StoreRepository storeRepository; // TODO SERVICE 로 변경

    public StoreUserResponse register(StoreUserRegisterRequest request) {

        Optional<StoreEntity> storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(
            request.getStoreName(), StoreStatus.REGISTERED);

        StoreUserEntity entity = storeUserConverter.toEntity(request, storeEntity.get());

        StoreUserEntity savedEntity = storeUserService.register(entity);

        return storeUserConverter.toResponse(savedEntity, storeEntity.get());
    }

}
