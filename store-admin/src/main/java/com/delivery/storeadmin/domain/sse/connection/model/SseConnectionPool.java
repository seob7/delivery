package com.delivery.storeadmin.domain.sse.connection.model;

import com.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SseConnectionPool implements ConnectionPoolIfs<String, UserSseConnection> {

    private final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    @Override
    public UserSseConnection getSession(String uniqueKey) {
        return connectionPool.get(uniqueKey);
    }

    @Override
    public void addSession(String uniqueKey, UserSseConnection session) {
        connectionPool.put(uniqueKey, session);
    }

    @Override
    public void onCompletionCallback(UserSseConnection session) {
        log.info("call back connection pool completion : {}", session);
        connectionPool.remove(session.getUniqueKey());
    }
}
