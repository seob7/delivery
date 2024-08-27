package com.delivery.account.domain.token.controller

import com.delivery.account.common.Log
import com.delivery.account.domain.token.business.TokenBusiness
import com.delivery.account.domain.token.controller.model.TokenValidationRequest
import com.delivery.account.domain.token.controller.model.TokenValidationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal-api/token")
class TokenInternalApiController(
    private val tokenBusiness: TokenBusiness
) {

    companion object: Log

    @PostMapping("/validation")
    fun tokenValidation(
        @RequestBody
        tokenValidationRequest: TokenValidationRequest?
    ): TokenValidationResponse {
        log.info("token validation init : {}", tokenValidationRequest)
        return tokenBusiness.tokenValidation(tokenValidationRequest)
    }

}