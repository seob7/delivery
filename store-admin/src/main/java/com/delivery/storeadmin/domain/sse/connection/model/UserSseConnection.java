package com.delivery.storeadmin.domain.sse.connection.model;

import com.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ObjectMapper objectMapper;

    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private UserSseConnection(
        String uniqueKey,
        ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
        ObjectMapper objectMapper
    ) {
        this.uniqueKey = uniqueKey;
        this.sseEmitter = new SseEmitter(1000L * 60);
        this.connectionPoolIfs = connectionPoolIfs;
        this.objectMapper = objectMapper;

        // on completion
        this.sseEmitter.onCompletion(() -> {
            this.connectionPoolIfs.onCompletionCallback(this);
        });

        // on timeout
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        // onopen 메시지
        sendMessage("onopen", "connect");
    }

    public static UserSseConnection connect(
        String uniqueKey,
        ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
        ObjectMapper objectMapper
    ) {
        return new UserSseConnection(uniqueKey, connectionPoolIfs, objectMapper);
    }

    public void sendMessage(String eventName, Object data) {


        try {
            String json = this.objectMapper.writeValueAsString(data);
            SseEventBuilder event = SseEmitter.event()
                .name(eventName)
                .data(json);

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            String json = this.objectMapper.writeValueAsString(data);
            SseEventBuilder event = SseEmitter.event()
                .data(json);

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
