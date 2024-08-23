package com.delivery.api.config.web

import com.delivery.api.interceptor.AuthorizationInterceptor
import com.delivery.api.resolver.UserSessionResolver
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebConfig(
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val userSessionResolver: UserSessionResolver
) : WebMvcConfigurer{

    private val OPEN_API = listOf(
        "open-api/**"
    )

    private val DEFAULT_EXCLUDE = listOf(
        "/",
        "favicon.ico",
        "/error"
    )

    private val SWAGGER = listOf(
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    )

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authorizationInterceptor)
            .excludePathPatterns(OPEN_API)
            .excludePathPatterns(DEFAULT_EXCLUDE)
            .excludePathPatterns(SWAGGER)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userSessionResolver)
    }
}