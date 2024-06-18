package com.jun.blogducinemaback.basetest

import com.google.gson.Gson
import com.jun.blogducinemaback.controllers.InfoControllerTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureWebMvc
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
abstract class DefaultControllerTest {
    val logger = LoggerFactory.getLogger(InfoControllerTest::class.java)!!
    lateinit var mockMvc: MockMvc

    class Response<T: Any>(val status: Int, val body: T) {}

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder> { it.response.setDefaultCharacterEncoding(StandardCharsets.UTF_8.toString()) }
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(restDocumentation).snippets().withEncoding("UTF-8")
            )
            .build()
    }

    fun <T: Any>getResponse(mvcResult: MvcResult, classOfBody: Class<T>): Response<T> {
        val status = mvcResult.response.status
        val bodyString = mvcResult.response.contentAsString
        val body = Gson().fromJson(bodyString, classOfBody)
        return Response(status, body)
    }
}