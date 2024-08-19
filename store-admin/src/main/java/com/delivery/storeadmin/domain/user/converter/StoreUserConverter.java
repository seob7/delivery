package com.delivery.storeadmin.domain.user.converter;

import com.delivery.db.store.StoreEntity;
import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storeadmin.common.annotation.Converter;
import com.delivery.storeadmin.domain.authorization.model.UserSession;
import com.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import com.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
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

    public StoreUserResponse toResponse(UserSession userSession) {
        return StoreUserResponse.builder()
            .user(
                StoreUserResponse.UserResponse.builder()
                    .id(userSession.getUserId())
                    .email(userSession.getEmail())
                    .status(userSession.getStatus())
                    .role(userSession.getRole())
                    .registeredAt(userSession.getRegisteredAt())
                    .unregisteredAt(userSession.getUnregisteredAt())
                    .lastLoginAt(userSession.getLastLoginAt())
                    .build()
            )
            .store(
                StoreUserResponse.StoreResponse.builder()
                    .id(userSession.getStoreId())
                    .name(userSession.getStoreName())
                    .build()
            )
            .build();
    }

}
