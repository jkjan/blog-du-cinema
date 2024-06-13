package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.config.JwtFilter
import com.jun.blogducinemaback.config.JwtUtil
import com.jun.blogducinemaback.config.SecurityConfig
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.entity.Post
import com.jun.blogducinemaback.entity.UserData
import com.jun.blogducinemaback.repositories.UserRepository
import com.jun.blogducinemaback.services.UserService
import jakarta.servlet.Filter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.test.context.support.TestExecutionEvent
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

class PostControllerTest : DefaultControllerTest() {
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
}