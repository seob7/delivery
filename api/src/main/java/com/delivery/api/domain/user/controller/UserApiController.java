package com.delivery.api.domain.user.controller;

import com.delivery.common.annotation.UserSession;
import com.delivery.common.api.Api;
import com.delivery.api.domain.user.business.UserBusiness;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping
    public Api<UserResponse> me(@UserSession User user) {
        UserResponse response = userBusiness.me(user);
        return Api.OK(response);
    }

}
