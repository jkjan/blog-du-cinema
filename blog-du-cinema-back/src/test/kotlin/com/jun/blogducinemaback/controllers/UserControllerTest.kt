package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.adapter.`in`.api.UserController
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.global.utils.JwtUtil
import com.jun.blogducinemaback.application.dto.UserSignInDTO
import com.jun.blogducinemaback.application.dto.UserSignUpDTO
import com.jun.blogducinemaback.application.model.UserService
import com.jun.blogducinemaback.domain.Authority
import com.jun.blogducinemaback.domain.UserData
import com.jun.blogducinemaback.global.types.Role
import org.aspectj.lang.annotation.Before
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.util.*
import kotlin.collections.HashMap

@WebMvcTest(controllers = [UserController::class])
class UserControllerTest : DefaultControllerTest() {
    @MockBean
    lateinit var userService: UserService

    @Test
    fun `회원가입 성공`() {
        val newUser = UserSignUpDTO(
            username = "test",
            password = "testPW"
        )

        given(userService.signUp(any())).willReturn(Optional.of(UserData("test", "testPW")))

        val newUserJson = Gson().toJson(newUser)

        // when
        val mvcResult =
            mockMvc.perform(
                post("/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newUserJson)
            )
                .andDo(document("user/sign-up"))
                .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.body["message"]).isEqualTo("회원 가입이 완료되었습니다.")
    }

    @Test
    fun `부적절한 아이디, 패스워드로 회원가입 실패`() {
        // when
        val newUser = UserSignUpDTO(
            username = "",
            password = ""
        )

        val newUserJson = Gson().toJson(newUser)

        val mvcResult = mockMvc.perform(
            post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )
            .andDo(document("user/invalid-user-sign-up"))
            .andReturn()

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `로그인 성공`() {
        // given
        val invalidUser = UserSignInDTO("test", "InvalidTestPW")
        val invalidUserJson = Gson().toJson(invalidUser)
        val token = TestingAuthenticationToken("test", "test", listOf(Authority()))
        given(userService.signIn(any())).willReturn(Optional.of(token))

        // when
        val mvcResult = mockMvc.perform(
            post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUserJson)
        )
            .andDo(document("user/user-sign-in"))
            .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    fun `로그인 실패`() {
        // given
        val invalidUser = UserSignInDTO("test", "InvalidTestPW")
        val invalidUserJson = Gson().toJson(invalidUser)
        given(userService.signIn(any())).willReturn(Optional.empty())

        // when
        val mvcResult = mockMvc.perform(
            post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUserJson)
        )
            .andDo(document("user/invalid-user-sign-in"))
            .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }
}