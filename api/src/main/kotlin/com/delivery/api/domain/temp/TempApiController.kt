package com.delivery.api.domain.temp

import com.delivery.db.user.UserRepository
import com.delivery.db.user.enums.UserStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/temp")
class TempApiController(
    val userRepository: UserRepository
) {

    @GetMapping("")
    fun temp(): String {

        val user = userRepository.findFirstByIdAndStatusOrderByIdDesc(10, UserStatus.REGISTERED)

        return "hello kotlin spring boot"
    }
}