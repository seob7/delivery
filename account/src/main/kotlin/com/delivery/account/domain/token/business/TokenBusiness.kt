package com.delivery.account.domain.token.business

import com.delivery.account.domain.token.controller.model.TokenValidationRequest
import com.delivery.account.domain.token.controller.model.TokenValidationResponse
import com.delivery.account.domain.token.service.TokenService
import com.delivery.common.annotation.Business

@Business
class TokenBusiness(
    private val tokenService: TokenService
) {
    fun tokenValidation(tokenValidationRequest: TokenValidationRequest?): TokenValidationResponse {
        val result = tokenService.validationToken(tokenValidationRequest?.tokenDto?.token)
        return TokenValidationResponse(
            userId = result
        )
    }
}