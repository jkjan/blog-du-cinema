package com.jun.blogducinemaback.controllers

import com.jun.blogducinemaback.config.logger
import com.jun.blogducinemaback.dto.UserSignInDTO
import com.jun.blogducinemaback.dto.UserSignUpDTO
import com.jun.blogducinemaback.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {
    private val logger = logger()

    @PostMapping("/user/sign-up")
    fun signUp(
        @Validated
        @RequestBody
        user: UserSignUpDTO,
    ): ResponseEntity<HashMap<String, String>> {
        logger.info("Controller: ${user.username} ${user.password}")

        val signUpSucceeded = userService.signUp(user)
        lateinit var status: HttpStatus
        val body: HashMap<String, String> = hashMapOf()

        if (signUpSucceeded) {
            status = HttpStatus.CREATED
            body["message"] = "회원 가입이 완료되었습니다."
        }
        else {
            status = HttpStatus.CONFLICT
            body["message"] = "이미 있는 아이디입니다."
        }

       val response = ResponseEntity
           .status(status)
           .body(body)

        // TODO: JWT 토큰 반환
        return response
    }

    @PostMapping("/user/sign-in")
    fun signIn(
        @Validated
        @RequestBody
        user: UserSignInDTO
    ): ResponseEntity<HashMap<String, String>> {
        lateinit var status: HttpStatus
        val body: HashMap<String, String> = hashMapOf()
        lateinit var response: ResponseEntity<HashMap<String, String>>

        try {
            val authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.username, user.password)
            authenticationManager.authenticate(authenticationRequest)

            status = HttpStatus.OK
            body["message"] = "로그인에 성공하였습니다."
        }
        catch (e: BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED
            body["message"] = "아이디가 존재하지 않거나 비밀번호와 일치하지 않습니다."

        }
        finally {
            response = ResponseEntity
                .status(status)
                .body(body)
        }

        return response
    }
}