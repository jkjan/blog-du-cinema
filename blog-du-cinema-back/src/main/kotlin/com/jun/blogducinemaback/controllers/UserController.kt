package com.jun.blogducinemaback.controllers

import com.jun.blogducinemaback.config.logger
import com.jun.blogducinemaback.dto.UserSignUpDTO
import com.jun.blogducinemaback.services.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {
    private val logger = logger()

    @PostMapping("/user/sign-up")
    fun signUp(
        @Validated
        @RequestBody
        user: UserSignUpDTO,
    ): ResponseEntity<String> {
        logger.info("Controller: ${user.username} ${user.password}")

        val signUpSucceeded = userService.signUp(user)
        lateinit var status: HttpStatus
        lateinit var body: String

        if (signUpSucceeded) {
            status = HttpStatus.CREATED
            body = "회원 가입이 완료되었습니다."
        }
        else {
            status = HttpStatus.CONFLICT
            body = "이미 있는 아이디입니다."
        }

       val response = ResponseEntity
           .status(status)
           .body(body)

        // TODO: JWT 토큰 반환
        return response
    }
}