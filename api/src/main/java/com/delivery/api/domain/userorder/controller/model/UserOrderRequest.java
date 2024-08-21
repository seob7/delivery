package com.delivery.api.domain.userorder.controller.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {

    @NotNull
    private Long storeId;

    @NotNull
    private List<Long> storeMenuIdList;

}
