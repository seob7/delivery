package com.delivery.api.domain.userorder.controller.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserOrderRequest {

    @NotNull
    private Long storeId;

    @NotNull
    private List<Long> storeMenuIdList;

    public @NotNull Long getStoreId() {
        return storeId;
    }

    public void setStoreId(@NotNull Long storeId) {
        this.storeId = storeId;
    }

    public @NotNull List<Long> getStoreMenuIdList() {
        return storeMenuIdList;
    }

    public void setStoreMenuIdList(
        @NotNull List<Long> storeMenuIdList) {
        this.storeMenuIdList = storeMenuIdList;
    }
}
