package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.dto.UserSignUpDTO
import com.jun.blogducinemaback.services.UserService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class UserControllerTest: DefaultControllerTest() {
    @MockBean
    lateinit var userService: UserService

    @Test
    fun signUp() {
        given(userService.signUp(any<UserSignUpDTO>())).willReturn(true)

        val newUser = UserSignUpDTO(
            username = "test",
            password = "testPW"
        )

        val newUserJson = Gson().toJson(newUser)
        logger.info("test user: $newUserJson")

        mockMvc.perform(
            post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )
            .andExpect(status().isCreated)
            .andExpect(content().string("회원 가입이 완료되었습니다."))
            .andDo(document("user/sign-up"))
    }

    @Test
    fun signUpInvalidUserNameOrPasswordTest() {
        val newUser = UserSignUpDTO(
            username = "",
            password = ""
        )

        val newUserJson = Gson().toJson(newUser)
        logger.info("test user: $newUserJson")

        mockMvc.perform(
            post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )
            .andExpect(status().isBadRequest)
            .andDo(document("user/invalid-user-sign-up"))
    }
}