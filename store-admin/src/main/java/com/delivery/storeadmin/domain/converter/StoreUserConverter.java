package com.delivery.storeadmin.domain.converter;

import com.delivery.db.store.StoreEntity;
import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storeadmin.common.annotation.Converter;
import com.delivery.storeadmin.domain.controller.model.StoreUserRegisterRequest;
import com.delivery.storeadmin.domain.controller.model.StoreUserResponse;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class StoreUserConverter {

    public StoreUserEntity toEntity(StoreUserRegisterRequest request, StoreEntity storeEntity) {
        return StoreUserEntity.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .role(request.getRole())
            .storeId(storeEntity.getId())
            .build();
    }

    public StoreUserResponse toResponse(StoreUserEntity storeUserEntity, StoreEntity storeEntity) {
        return StoreUserResponse.builder()
            .user(
                StoreUserResponse.UserResponse.builder()
                    .id(storeUserEntity.getId())
                    .email(storeUserEntity.getEmail())
                    .status(storeUserEntity.getStatus())
                    .role(storeUserEntity.getRole())
                    .registeredAt(storeUserEntity.getRegisteredAt())
                    .unregisteredAt(storeUserEntity.getUnregisteredAt())
                    .lastLoginAt(storeUserEntity.getLastLoginAt())
                    .build()
            )
            .store(
                StoreUserResponse.StoreResponse.builder()
                    .id(storeEntity.getId())
                    .name(storeEntity.getName())
                    .build()
            )
            .build();
    }

}
