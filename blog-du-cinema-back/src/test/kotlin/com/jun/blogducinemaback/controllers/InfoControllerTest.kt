package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.services.InfoService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
class InfoControllerTest {
    private val logger = LoggerFactory.getLogger(InfoControllerTest::class.java)!!

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var infoService: InfoService

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo <DefaultMockMvcBuilder> { it.response.setDefaultCharacterEncoding(StandardCharsets.UTF_8.toString()) }
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation).snippets().withEncoding("UTF-8"))
            .build()
    }

    @Test
    fun getInfoLabels() {
        val response = mockMvc.perform(
            get("/info/category")
        )
            .andExpect(status().isOk)
            .andDo(document("info/category"))
            .andReturn()

        logger.debug("response ${response.response.contentAsString}")
        val body = Gson().fromJson(response.response.contentAsString, Array<InfoLabelDTO>::class.java)
        logger.debug("body ${body}")

        assertTrue(body.size == 35)

        val categories = body!!.map { it.category }.toSet()
        val categoriesReal = setOf("영화의 역사", "다양한 장르", "단어 사전", "영화 방법론")

        logger.info("시ㅁㄴ")
        categoriesReal.forEach { logger.info(it) }
        categories.forEach { logger.info(it) }

        assertTrue(categories == categoriesReal)
    }

    @Test
    fun getPostsForInfoLabel() {
        val response = mockMvc.perform(
            get("/info/post/20")
        )
            .andExpect(status().isOk)
            .andDo(document("info/post"))
            .andReturn()

        val body = Gson().fromJson(response.response.contentAsString, Array<InfoPostDTO>::class.java)

        assertTrue(body.size == 5)
        assertTrue(body[0].title == "title 1")
        assertTrue(body[1].title == "title 2")
        assertTrue(body[2].title == "title 3")
        assertTrue(body[3].title == "title 4")
        assertTrue(body[4].title == "title 5")
    }
}