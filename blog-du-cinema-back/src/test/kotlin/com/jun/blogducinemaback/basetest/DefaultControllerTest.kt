package com.jun.blogducinemaback.basetest

import com.google.gson.Gson
import com.jun.blogducinemaback.adapter.`in`.filter.JwtFilter
import com.jun.blogducinemaback.application.model.UserDataDetailsService
import com.jun.blogducinemaback.application.model.UserService
import com.jun.blogducinemaback.controllers.InfoControllerTest
import com.jun.blogducinemaback.global.config.JwtConfig
import com.jun.blogducinemaback.global.config.SecurityConfig
import com.jun.blogducinemaback.global.utils.JwtUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.charset.StandardCharsets

@Import(
    JwtFilter::class,
    JwtUtil::class,
    SecurityTestConfig::class,
    SecurityConfig::class,
    JwtConfig::class
)
@WithAnonymousUser
@ExtendWith(RestDocumentationExtension::class)
abstract class DefaultControllerTest {
    @MockBean
    lateinit var userDataDetailsService: UserDataDetailsService

    @MockBean
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    private val logger = LoggerFactory.getLogger(InfoControllerTest::class.java)!!
    lateinit var mockMvc: MockMvc

    class Response<T : Any>(val status: Int, val body: T)

    @BeforeEach
    fun mockMvc(webApplicationContext: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder> { it.response.setDefaultCharacterEncoding(StandardCharsets.UTF_8.toString()) }
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(restDocumentation).snippets().withEncoding(StandardCharsets.UTF_8.toString())
            )
            .build()
    }

    fun <T : Any> getResponse(mvcResult: MvcResult, classOfBody: Class<T>): Response<T> {
        val status = mvcResult.response.status
        val bodyString = mvcResult.response.contentAsString
        val body = Gson().fromJson(bodyString, classOfBody)
        logger.info("Response status: $status")
        logger.info("Response body: $bodyString")
        return Response(status, body)
    }
}