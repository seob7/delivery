package com.delivery.api.interceptor;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.error.TokenErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.business.TokenBusiness;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB, 특히 Chrome 의 경우 GET, POST OPTIONS = pass
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // js, html, png resource 를 요청하는 경우 pass
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // header 검증
        String accessToken = request.getHeader("authorization-token");
        if(accessToken == null) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        Long userId = tokenBusiness.validationAccessToken(accessToken);

        if(userId != null) {
            RequestAttributes requestContext = Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
            return true;
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
    }
}
