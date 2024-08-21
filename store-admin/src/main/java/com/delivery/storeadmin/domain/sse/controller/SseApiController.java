package com.delivery.storeadmin.domain.sse.controller;

import com.delivery.storeadmin.domain.authorization.model.UserSession;
import com.delivery.storeadmin.domain.sse.connection.model.SseConnectionPool;
import com.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/sse")
public class SseApiController {

    private final SseConnectionPool sseConnectionPool;
    private final ObjectMapper objectMapper;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
        @Parameter(hidden = true)
        @AuthenticationPrincipal UserSession userSession) {
        log.info("login user {}", userSession);

        UserSseConnection userSseConnection = UserSseConnection.connect(
            userSession.getStoreId().toString(), sseConnectionPool, objectMapper);

        // session 에 추가
        sseConnectionPool.addSession(userSseConnection.getUniqueKey(), userSseConnection);

        return userSseConnection.getSseEmitter();

    }

    @GetMapping("/push-event")
    public void pushEvent(
        @Parameter(hidden = true)
        @AuthenticationPrincipal UserSession userSession
    ) {
        // 기존에 연결된 유저 찾기
        UserSseConnection userSseConnection = sseConnectionPool.getSession(
            userSession.getStoreId().toString());

        Optional.of(userSseConnection).ifPresent(it -> {
            it.sendMessage("hello world");
        });

    }
}
