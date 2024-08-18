package com.delivery.api.domain.token.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.ifs.TokenHelperIfs;
import com.delivery.api.domain.token.model.TokenDto;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }
    
    public Long validationToken(String token) {
        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);
        Object userId = map.get("userId");
        Objects.requireNonNull(userId, ()->{throw new ApiException(ErrorCode.NULL_POINT);});
        return Long.parseLong(userId.toString());
    }

}
