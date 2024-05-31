package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.entity.Label
import com.jun.blogducinemaback.entity.Post
import com.jun.blogducinemaback.services.LabelService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LabelControllerTest: DefaultControllerTest() {
    @MockBean
    lateinit var labelService: LabelService

    @Test
    fun getInfoLabels() {
        given(labelService.getInfoLabels()).willReturn(listOf(
            InfoLabelDTO(Label(1, "test1", "test")),
            InfoLabelDTO(Label(2, "test2", "test")),
            InfoLabelDTO(Label(3, "test3", "test")),
        ))

        val mvcResult = mockMvc.perform(
            get("/label/info")
        )
//            .andExpect(status().isOk)
            .andDo(document("label/info"))
            .andReturn()

        val status = mvcResult.response.status
        assertThat(status).isEqualTo(HttpStatus.OK)

        
        logger.debug("response ${mvcResult.response.contentAsString}")
        val body = Gson().fromJson(mvcResult.response.contentAsString, Array<InfoLabelDTO>::class.java)

        assertThat(body).hasSize(3)
        assertThat(body[0].labelName).isEqualTo("test1")
        assertThat(body[1].labelName).isEqualTo("test2")
        assertThat(body[2].labelName).isEqualTo("test3")
    }

    @Test
    fun getPostsForLabel() {
        given(labelService.getPostsForLabel(1)).willReturn(listOf(
            InfoPostDTO(Post("title 1")),
            InfoPostDTO(Post("title 2")),
            InfoPostDTO(Post("title 3"))
        ))

        val response = mockMvc.perform(
            get("/label/1/post")
        )
            .andExpect(status().isOk)
            .andDo(document("label/post"))
            .andReturn()

        val body = Gson().fromJson(response.response.contentAsString, Array<InfoPostDTO>::class.java)

        assertTrue(body.size == 3)
        assertTrue(body[0].title == "title 1")
        assertTrue(body[1].title == "title 2")
        assertTrue(body[2].title == "title 3")
    }
}