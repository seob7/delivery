package com.delivery.api.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        // Filter 에서 Request 내용을 읽으면 그 후 Request 의 내용을 읽을 수 없기 때문에 미리 캐싱을 해줌
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(
            (HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(
            (HttpServletResponse) response);

        log.info("INIT URI : {}", req.getRequestURI());

        chain.doFilter(req, res);

        // request 정보
        Enumeration<String> headerNames = req.getHeaderNames();
        StringBuilder headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            String headerValue = req.getHeader(headerKey);
            headerValues.append("[").append(headerKey).append(" : ").append(headerValue)
                .append("] ");
        });

        String requestBody = new String(req.getContentAsByteArray());
        String uri = req.getRequestURI();
        String method = req.getMethod();

        log.info(">>>>> uri : {}, method : {}, header : {}, body : {}", uri, method,
            headerValues, requestBody);

        // response 정보
        StringBuilder responseHeaderValues = new StringBuilder();
        res.getHeaderNames().forEach(headerKey -> {
            String headerValue = res.getHeader(headerKey);
            responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue)
                .append("] ");
        });

        String responseBody = new String(res.getContentAsByteArray());
        log.info("<<<<< uri : {}, method : {}, header : {}, body : {}", uri, method,
            headerValues, responseBody);

        res.copyBodyToResponse(); // responseBody 를 한 번 읽었기 때문에 다시 한 번 초기화 시켜야 함 : 반드시 있어야 함

    }
}
