package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.dto.Message
import com.jun.blogducinemaback.dto.UserSignInDTO
import com.jun.blogducinemaback.dto.UserSignUpDTO
import com.jun.blogducinemaback.services.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class UserControllerTest : DefaultControllerTest() {
    @Autowired
    lateinit var userService: UserService

    @Test
    fun signUpSuccessTest() {
        // given
        given(userService.signUp(any<UserSignUpDTO>())).willReturn(true)

        val newUser = UserSignUpDTO(
            username = "test",
            password = "testPW"
        )

        val newUserJson = Gson().toJson(newUser)

        // when
        val mvcResult =
            mockMvc.perform(
                post("/user/sign-up")
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
    fun signUpInvalidUserNameOrPasswordTest() {
        // when
        val newUser = UserSignUpDTO(
            username = "",
            password = ""
        )

        val newUserJson = Gson().toJson(newUser)

        val mvcResult = mockMvc.perform(
            post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun signInSucceededTest() {
        // given
        val newUser = UserSignUpDTO("test", "testPW")
        val newUserJson = Gson().toJson(newUser)

        mockMvc.perform(
            post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )

        // when
        val mvcResult = mockMvc.perform(
            post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )
            .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    fun signInFailedTest() {
        // given
        val newUser = UserSignUpDTO("test", "testPW")
        val newUserJson = Gson().toJson(newUser)

        mockMvc.perform(
            post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )

        val invalidUser = UserSignUpDTO("test", "IvalidtestPW")
        val invalidUserJson = Gson().toJson(invalidUser)

        // when
        val mvcResult = mockMvc.perform(
            post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUserJson)
        )
            .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }
}