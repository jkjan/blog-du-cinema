package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.adapter.`in`.api.ForumController
import com.jun.blogducinemaback.application.dto.InfoPostDTO
import com.jun.blogducinemaback.application.model.ForumService
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.basetest.TestUser
import com.jun.blogducinemaback.domain.Post
import com.jun.blogducinemaback.global.types.Role
import jakarta.servlet.http.Cookie
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


@WebMvcTest(controllers = [ForumController::class])
class ForumControllerTest : DefaultControllerTest() {
    @MockBean
    lateinit var forumService: ForumService

    @Test
    fun `권한 없이 게시판 글 작성 실패`() {
        // given
        val newPost = InfoPostDTO(Post("title", "html"))
        val newPostJson = Gson().toJson(newPost)

        // when
        val mvcResult = mockMvc.perform(
            post("/forum").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(newPostJson)
        ).andReturn()

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    @WithUserDetails(TestUser.USER)
    fun `사용자 이상 권한으로 게시판 글 작성 성공`() {
        // given
        given(forumService.createNewPost(any())).willReturn(true)
        val newPost = InfoPostDTO(Post("title", "html"))
        val newPostJson = Gson().toJson(newPost)

        // when
        val mvcResult =
            mockMvc.perform(post("/forum").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(newPostJson))
                .andReturn()
        val body = Gson().fromJson(mvcResult.response.contentAsString, HashMap::class.java)

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.CREATED.value())
    }

    @Test
    @WithUserDetails(TestUser.ADMIN)
    fun `ADMIN 권한으로 게시판 글 작성 성공`() {
        // given
        given(forumService.createNewPost(any())).willReturn(true)
        val newPost = InfoPostDTO(Post("title", "html"))
        val newPostJson = Gson().toJson(newPost)

        // when
        val mvcResult =
            mockMvc.perform(post("/forum").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(newPostJson))
                .andReturn()
        val body = Gson().fromJson(mvcResult.response.contentAsString, HashMap::class.java)

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.CREATED.value())
    }

    @Test
    fun `쿠키로 SUPERUSER 권한 인가 성공`() {
        given(forumService.createNewPost(any())).willReturn(true)
        val jwtToken = jwtUtil.createJwt("test", listOf(Role.SUPERUSER.value))
        val cookie = jwtUtil.createCookie(jwtToken)
        val post = InfoPostDTO(Post("title", "html"))
        val postJson = Gson().toJson(post)

        val mvcResult = mockMvc.perform(
            post("/forum").with(csrf()).cookie(Cookie(cookie.name, cookie.value)).contentType(MediaType.APPLICATION_JSON).content(postJson)
        )
            .andReturn()

        val response = getResponse(mvcResult, HashMap::class.java)

        assertThat(response.status).isEqualTo(HttpStatus.CREATED.value())
    }
}