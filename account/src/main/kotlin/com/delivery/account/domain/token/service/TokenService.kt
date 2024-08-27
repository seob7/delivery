package com.delivery.account.domain.token.service

import com.delivery.account.domain.token.ifs.TokenHelperIfs
import com.delivery.account.domain.token.model.TokenDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class TokenService(
    private val tokenHelperIfs: TokenHelperIfs
) {

    fun issueAccessToken(userId: Long?): TokenDto? {
        return userId?.let {
            var data = mapOf("userId" to it)
            tokenHelperIfs.issueRefreshToken(data)
        }
    }

    fun issueRefreshToken(userId: Long?): TokenDto? {
        requireNotNull(userId)
        var data = mapOf("userId" to userId)
        return tokenHelperIfs.issueRefreshToken(data)
    }

    fun validationToken(token: String?): Long? {
        return token?.let { token ->
            tokenHelperIfs.validationTokenWithThrow(token)
        }?.let { map ->
            map["userId"]
        }?.let { userId ->
            userId.toString().toLong()
        }
    }


}
