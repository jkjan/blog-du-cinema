package com.jun.blogducinemaback.adapter.`in`.api

import com.jun.blogducinemaback.application.dto.request.PostRequestDTO
import com.jun.blogducinemaback.global.utils.logger
import com.jun.blogducinemaback.application.dto.response.InfoLabelDTO
import com.jun.blogducinemaback.application.dto.response.PostListDTO
import com.jun.blogducinemaback.application.model.PostService
import com.jun.blogducinemaback.application.model.InfoService
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/info")
@RestController
class InfoController(
    val infoService: InfoService,
    val postService: PostService
) {
    @GetMapping("/label")
    fun getInfoLabels(): ResponseEntity<List<InfoLabelDTO>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(infoService.getInfoLabels())

    @PostMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")
    fun createNewInfoPost(
        @RequestBody
        post: PostRequestDTO
    ): ResponseEntity<Void> {
        val createdNewPost = postService.createNewPost(post)

        return if (createdNewPost)
            ResponseEntity
                .status(HttpStatus.CREATED)
                .build()
        else
            ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .build()
    }

    @GetMapping("/post/{labelId}")
    fun getPostsForLabel(@PathVariable labelId: Int, @RequestBody lastPostId: Int): ResponseEntity<PostListDTO> {
        val posts = postService.getPostListForLabel(listOf(labelId), lastPostId)

        return if (posts.isPresent)
            ResponseEntity
                .status(HttpStatus.OK)
                .body(posts.get())
        else
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(PostListDTO(Page.empty()))
    }
}