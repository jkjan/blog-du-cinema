package com.jun.blogducinemaback.adapter.`in`.api

import com.jun.blogducinemaback.global.utils.JwtUtil
import com.jun.blogducinemaback.global.utils.logger
import com.jun.blogducinemaback.application.dto.UserSignInDTO
import com.jun.blogducinemaback.application.dto.UserSignUpDTO
import com.jun.blogducinemaback.application.model.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
) {
    private val logger = logger()

    @PostMapping("/sign-up")
    fun signUp(
        @Validated
        @RequestBody
        user: UserSignUpDTO,
    ): ResponseEntity<HashMap<String, String>> {
        logger.info("Controller: ${user.username} ${user.password}")

        val signUpResult = userService.signUp(user)
        lateinit var status: HttpStatus
        val body: HashMap<String, String> = hashMapOf()
        val headers = HttpHeaders()

        if (signUpResult.isPresent) {
            val registeredUserData = signUpResult.get()
            val token = jwtUtil.createJwt(registeredUserData.username, registeredUserData.authorities.map { it.authorityName })
            headers.set(HttpHeaders.SET_COOKIE, jwtUtil.createCookie(token).toString())

            status = HttpStatus.CREATED
            body["message"] = "회원 가입이 완료되었습니다."
        } else {
            status = HttpStatus.CONFLICT
            body["message"] = "이미 있는 아이디입니다."
        }

        val response = ResponseEntity
            .status(status)
            .headers(headers)
            .body(body)

        return response
    }

    @PostMapping("/sign-in")
    fun signIn(
        @Validated
        @RequestBody
        user: UserSignInDTO
    ): ResponseEntity<HashMap<String, String>> {
        var status: HttpStatus = HttpStatus.BAD_REQUEST
        val headers = HttpHeaders()
        val body: HashMap<String, String> = hashMapOf()
        lateinit var response: ResponseEntity<HashMap<String, String>>

        try {
            val token = UsernamePasswordAuthenticationToken(user.username, user.password)
            val authentication = authenticationManager.authenticate(token)

            logger.info("로그인 성공")

            val jwtToken = jwtUtil.createJwt(user.username, authentication.authorities.map { it.authority })
            val cookie = jwtUtil.createCookie(jwtToken)

            status = HttpStatus.OK
            headers.set(HttpHeaders.SET_COOKIE, cookie.toString())
            body["message"] = "로그인에 성공하였습니다."
        }
        catch (e: BadCredentialsException) {
            logger.info("로그인 실패")
            status = HttpStatus.UNAUTHORIZED
            body["message"] = "아이디가 존재하지 않거나 비밀번호와 일치하지 않습니다."
        }
        finally {
            response = ResponseEntity
                .status(status)
                .headers(headers)
                .body(body)
        }

        return response
    }
}