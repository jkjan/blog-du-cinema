package com.jun.blogducinemaback.controllers

import com.google.gson.Gson
import com.jun.blogducinemaback.adapter.`in`.api.InfoController
import com.jun.blogducinemaback.basetest.DefaultControllerTest
import com.jun.blogducinemaback.application.dto.InfoLabelDTO
import com.jun.blogducinemaback.application.dto.InfoPostDTO
import com.jun.blogducinemaback.domain.Label
import com.jun.blogducinemaback.domain.Post
import com.jun.blogducinemaback.application.model.InfoService
import com.jun.blogducinemaback.basetest.TestUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(controllers = [InfoController::class])
@WithMockUser
class InfoControllerTest : DefaultControllerTest() {
    @MockBean
    lateinit var infoService: InfoService

    @Test
    fun `Info에 등록된 Label 가져오기`() {
        // given
        given(infoService.getInfoLabels()).willReturn(
            listOf(
                InfoLabelDTO(Label(1, "test1", "test")),
                InfoLabelDTO(Label(2, "test2", "test")),
                InfoLabelDTO(Label(3, "test3", "test")),
            )
        )

        // when
        val mvcResult = mockMvc.perform(get("/info/label")).andDo(document("info/label")).andReturn()

        val response = getResponse(mvcResult, Array<InfoLabelDTO>::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.body)
        assertThat(response.body[0].labelName).isEqualTo("test1")
        assertThat(response.body[1].labelName).isEqualTo("test2")
        assertThat(response.body[2].labelName).isEqualTo("test3")
    }

    @Test
    fun `Info에 등록된 Label 가져오기 실패`() {
        // given
        given(infoService.getInfoLabels()).willReturn(listOf())

        // when
        val mvcResult = mockMvc.perform(get("/info/label")).andReturn()
        val response = getResponse(mvcResult, Array<InfoLabelDTO>::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.NO_CONTENT.value())
        assertThat(response.body).isEmpty()
    }

    @Test
    fun `Info에 등록된 Label 별 글 가져오기`() {
        // given
        val labelId = 1
        given(infoService.getPostsForLabel(labelId)).willReturn(
            listOf(
                InfoPostDTO(Post("title 1")),
                InfoPostDTO(Post("title 2")),
                InfoPostDTO(Post("title 3")),
            )
        )

        // when
        val mvcResult = mockMvc.perform(get("/info/post/${labelId}")).andDo(document("info/post")).andReturn()
        val response = getResponse(mvcResult, Array<InfoPostDTO>::class.java)

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.body[0].title).isEqualTo("title 1")
        assertThat(response.body[1].title).isEqualTo("title 2")
        assertThat(response.body[2].title).isEqualTo("title 3")
    }

    @Test
    @WithUserDetails(TestUser.USER)
    fun `일반 사용자는 Info에 글을 쓸 수 없음`() {
        // given
        val newPost = Gson().toJson(InfoPostDTO("title", "html"))

        // when
        val mvcResult = mockMvc.perform(
            post("/info/post").contentType(MediaType.APPLICATION_JSON).content(newPost)
        ).andDo(document("info/post-new/fail")).andReturn()

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    @WithUserDetails(TestUser.ADMIN)
    fun `ADMIN 사용자는 Info에 글을 쓸 수 있음`() {
        // given
        val newPost = Gson().toJson(InfoPostDTO("title", "html"))

        // when
        val mvcResult = mockMvc.perform(
            post("/info/post").contentType(MediaType.APPLICATION_JSON).content(newPost)
        ).andDo(document("info/post-new/success")).andReturn()

        // then
        assertThat(mvcResult.response.status).isEqualTo(HttpStatus.CREATED.value())
    }
}