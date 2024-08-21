package com.delivery.storeadmin.domain.sse.connection.ifs;

import com.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;

public interface ConnectionPoolIfs<T, R> {

    void addSession(T uniqueKey, R session);

    UserSseConnection getSession(T uniqueKey);

    void onCompletionCallback(R session);

}
