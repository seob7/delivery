package com.delivery.account.domain.token.ifs

import com.delivery.account.domain.token.model.TokenDto

interface TokenHelperIfs {

    fun issueAccessToken(data: Map<String, Any>): TokenDto

    fun issueRefreshToken(data: Map<String, Any>): TokenDto

    fun validationTokenWithThrow(token: String?): Map<String, Any>?

}
