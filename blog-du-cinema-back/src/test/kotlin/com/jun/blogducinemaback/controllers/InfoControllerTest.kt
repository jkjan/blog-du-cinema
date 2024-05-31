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

class InfoControllerTest : DefaultControllerTest() {
    @MockBean
    lateinit var labelService: LabelService

    @Test
    fun getInfoLabelsSucceedTest() {
        // given
        given(labelService.getInfoLabels()).willReturn(
            listOf(
                InfoLabelDTO(Label(1, "test1", "test")),
                InfoLabelDTO(Label(2, "test2", "test")),
                InfoLabelDTO(Label(3, "test3", "test")),
            )
        )

        // when
        val mvcResult =
            mockMvc.perform(get("/info/label"))
                .andDo(document("info/label"))
                .andReturn()

        val response = getResponse(mvcResult, Array<InfoLabelDTO>::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.body)
        assertThat(response.body[0].labelName).isEqualTo("test1")
        assertThat(response.body[1].labelName).isEqualTo("test2")
        assertThat(response.body[2].labelName).isEqualTo("test3")
    }

    @Test
    fun getInfoLabelsFailTest() {
        // given
        given(labelService.getInfoLabels()).willReturn(listOf())

        // when
        val mvcResult =
            mockMvc.perform(get("/info/label"))
                .andReturn()
        val response = getResponse(mvcResult, Array<InfoLabelDTO>::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.NO_CONTENT.value())
        assertThat(response.body).isEmpty()
    }

    @Test
    fun getInfoPostsSucceedTest() {
        // given
        val labelId = 1
        given(labelService.getPostsForLabel(labelId)).willReturn(
            listOf(
                InfoPostDTO(Post("title 1")),
                InfoPostDTO(Post("title 2")),
                InfoPostDTO(Post("title 3")),
            )
        )

        // when
        val mvcResult =
            mockMvc.perform(get("/info/post/${labelId}"))
                .andDo(document("info/post"))
                .andReturn()
        val response = getResponse(mvcResult, Array<InfoPostDTO>::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.body[0].title).isEqualTo("title 1")
        assertThat(response.body[1].title).isEqualTo("title 2")
        assertThat(response.body[2].title).isEqualTo("title 3")
    }
}