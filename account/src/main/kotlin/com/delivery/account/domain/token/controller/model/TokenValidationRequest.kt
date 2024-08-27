package com.delivery.account.domain.token.controller.model

import com.delivery.account.domain.token.model.TokenDto

data class TokenValidationRequest(
    var tokenDto: TokenDto? = null
)