package com.delivery.api.domain.storemenu.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import com.delivery.db.storemenu.StoreMenuEntity;
import java.util.List;
import java.util.Optional;

@Converter
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request) {
        return Optional.ofNullable(request).map(it -> {
            return StoreMenuEntity.builder()
                .storeId(request.getStoreId())
                .name(request.getName())
                .amount(request.getAmount())
                .thumbnailUrl(request.getThumbnailUrl())
                .build();
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity) {
        return Optional.ofNullable(storeMenuEntity).map(it -> {
            return StoreMenuResponse.builder()
                .id(storeMenuEntity.getId())
                .name(storeMenuEntity.getName())
                .storeId(storeMenuEntity.getStoreId())
                .amount(storeMenuEntity.getAmount())
                .status(storeMenuEntity.getStatus())
                .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                .likeCount(storeMenuEntity.getLikeCount())
                .sequence(storeMenuEntity.getSequence())
                .build();
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list) {
        return list.stream().map(storeMenuEntity -> toResponse(storeMenuEntity)).toList();
    }

}
