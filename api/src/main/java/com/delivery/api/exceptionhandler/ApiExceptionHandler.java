package com.delivery.api.exceptionhandler;

import com.delivery.common.api.Api;
import com.delivery.common.exception.ApiException;
import com.delivery.common.error.ErrorCodeIfs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE) // 우선순위 상위
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiException) {
        log.info("", apiException);

        ErrorCodeIfs errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
            .body(Api.ERROR(errorCode, apiException.getErrorDescription()));
    }

}
