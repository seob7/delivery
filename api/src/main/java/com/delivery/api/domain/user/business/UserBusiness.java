package com.delivery.api.domain.user.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.token.business.TokenBusiness;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.user.controller.model.UserLoginRequest;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.converter.UserConverter;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.user.service.UserService;
import com.delivery.db.user.UserEntity;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    /**
     * 가입 처리 로직
     */
    public UserResponse register(UserRegisterRequest request) {

        UserEntity entity = userConverter.toEntity(request);
        UserEntity newEntity = userService.register(entity);
        UserResponse response = userConverter.toResponse(newEntity);
        return response;

/*        return Optional.ofNullable(request)
            .map(req -> userConverter.toEntity(req))
            .map(entity -> userService.register(entity))
            .map(entity -> userConverter.toResponse(entity))
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request null"));*/
    }

    public TokenResponse login(UserLoginRequest request) {

        UserEntity userEntity = userService.login(request.getEmail(), request.getPassword());

        return tokenBusiness.issueToken(userEntity);
    }

    public UserResponse me(User user) {
        UserEntity userEntity = userService.getUserWithThrow(user.getId());
        return userConverter.toResponse(userEntity);
    }
}
