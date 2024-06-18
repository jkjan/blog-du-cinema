package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.global.utils.JwtUtil
import com.jun.blogducinemaback.application.dto.UserSignInDTO
import com.jun.blogducinemaback.application.dto.UserSignUpDTO
import com.jun.blogducinemaback.application.model.UserService
import com.jun.blogducinemaback.domain.Authority
import com.jun.blogducinemaback.global.types.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class UserControllerTest : DefaultControllerTest() {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Test
    fun signUpSuccessTest() {
        val newUser = UserSignUpDTO(
            username = "test",
            password = "testPW"
        )

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
    fun signUpInvalidUserNameOrPasswordTest() {
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
            post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )
            .andDo(document("user/sign-in"))
            .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        // then
        val authorization = mvcResult.response.getHeaders("authorization")[0]
        logger.info(authorization)
        assertThat(authorization).isNotEmpty
        assertThat(authorization).startsWith("Bearer ")

        val token = authorization.split(" ")[1]
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(jwtUtil.isExpired(token)).isEqualTo(false)
        assertThat(jwtUtil.getUsername(token)).isEqualTo("test")
        assertThat(jwtUtil.getAuthorities(token)).contains(Authority(Role.USER.name))
        assertThat(response.body["message"]).isEqualTo("로그인에 성공하였습니다.")
    }

    @Test
    fun signInFailedTest() {
        // given
        val newUser = UserSignUpDTO("test", "testPW")
        val newUserJson = Gson().toJson(newUser)

        mockMvc.perform(
            post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson)
        )

        val invalidUser = UserSignInDTO("test", "InvalidTestPW")
        val invalidUserJson = Gson().toJson(invalidUser)

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