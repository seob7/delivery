package com.delivery.api.domain.store.controller;

import com.delivery.common.api.Api;
import com.delivery.api.domain.store.business.StoreBusiness;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.db.store.enums.StoreCategory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/search")
    public Api<List<StoreResponse>> search(@RequestParam(required = false)StoreCategory category) {
        List<StoreResponse> response = storeBusiness.searchCategory(category);
        return Api.OK(response);
    }


}
