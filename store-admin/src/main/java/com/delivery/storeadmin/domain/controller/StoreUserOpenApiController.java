package com.delivery.storeadmin.domain.controller;

import com.delivery.storeadmin.domain.business.StoreUserBusiness;
import com.delivery.storeadmin.domain.controller.model.StoreUserRegisterRequest;
import com.delivery.storeadmin.domain.controller.model.StoreUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

    private final StoreUserBusiness storeUserBusiness;

    @PostMapping("")
    public StoreUserResponse register(@Valid @RequestBody StoreUserRegisterRequest request) {
        StoreUserResponse response = storeUserBusiness.register(request);
        return response;
    }

}
