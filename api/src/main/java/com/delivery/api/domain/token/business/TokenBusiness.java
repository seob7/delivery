package com.delivery.api.domain.token.business;

import com.delivery.common.annotation.Business;
import com.delivery.common.error.ErrorCode;
import com.delivery.common.exception.ApiException;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.token.converter.TokenConverter;
import com.delivery.api.domain.token.model.TokenDto;
import com.delivery.api.domain.token.service.TokenService;
import com.delivery.db.user.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    public TokenResponse issueToken(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
            .map(entity -> entity.getId())
            .map(userId -> {
                TokenDto accessToken = tokenService.issueAccessToken(userId);
                TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                return tokenConverter.tokenResponse(accessToken, refreshToken);
            })
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public Long validationAccessToken(String accessToken) {
        Long userId = tokenService.validationToken(accessToken);
        return userId;
    }
    
}
