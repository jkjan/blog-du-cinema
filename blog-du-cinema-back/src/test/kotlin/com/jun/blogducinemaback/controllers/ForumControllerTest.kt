package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.application.dto.InfoPostDTO
import com.jun.blogducinemaback.domain.Post
import com.jun.blogducinemaback.global.types.Role
import com.jun.blogducinemaback.global.utils.JwtUtil
import jakarta.servlet.http.Cookie
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class ForumControllerTest : DefaultControllerTest() {
    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Test
    @WithAnonymousUser
    fun notEnoughAuthorityPostFailTest() {
        // given
        val newPost = InfoPostDTO(Post("title", "html"))
        val newPostJson = Gson().toJson(newPost)

        // when
        val mvcResult = mockMvc.perform(
            post("/post").with(csrf()).header(HttpHeaders.AUTHORIZATION, "Bearer invalid-token")
                .contentType(MediaType.APPLICATION_JSON).content(newPostJson)
        ).andReturn()

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    @WithMockUser(username = "test", roles = ["USER"])
    fun postSuccessTest() {
        // given
        val newPost = InfoPostDTO(Post("title", "html"))
        val newPostJson = Gson().toJson(newPost)

        // when
        val mvcResult =
            mockMvc.perform(post("/post").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(newPostJson))
                .andReturn()
        val body = Gson().fromJson(mvcResult.response.contentAsString, HashMap::class.java)

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.CREATED.value())
        assertThat(body["title"]).isEqualTo("title")
    }

    @Test
    @WithMockUser(username = "test", roles = ["ADMIN"])
    fun adminPostSuccessTest() {
        // given
        val newPost = InfoPostDTO(Post("title", "html"))
        val newPostJson = Gson().toJson(newPost)

        // when
        val mvcResult =
            mockMvc.perform(post("/post").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(newPostJson))
                .andReturn()
        val body = Gson().fromJson(mvcResult.response.contentAsString, HashMap::class.java)

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.CREATED.value())
        assertThat(body["title"]).isEqualTo("title")
    }

    @Test
    fun cookieReadingTest() {
        val jwtToken = jwtUtil.createJwt("test", listOf(Role.USER.value))
        val cookie = jwtUtil.createCookie(jwtToken)
        val post = InfoPostDTO(Post("title", "html"))
        val postJson = Gson().toJson(post)

        val mvcResult = mockMvc.perform(
            post("/post").with(csrf()).cookie(Cookie(cookie.name, cookie.value)).contentType(MediaType.APPLICATION_JSON).content(postJson)
        )
            .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        assertThat(response.status).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.body["title"]).isEqualTo("title")
        assertThat(response.body["contentHtml"]).isEqualTo("html")
    }

}