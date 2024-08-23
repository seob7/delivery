package com.delivery.api.domain.user.converter;

import com.delivery.common.annotation.Converter;
import com.delivery.common.error.ErrorCode;
import com.delivery.common.exception.ApiException;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.db.user.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request) {
        return Optional.ofNullable(request).map(it -> {
            // to Entity
            return UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .build();
        }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity).map(it -> {
            return UserResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .status(userEntity.getStatus())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress())
                .registeredAt(userEntity.getRegisteredAt())
                .unregisteredAt(userEntity.getUnregisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }
}
