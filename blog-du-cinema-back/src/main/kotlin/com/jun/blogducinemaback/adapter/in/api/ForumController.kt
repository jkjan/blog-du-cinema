package com.jun.blogducinemaback.adapter.`in`.api

import com.jun.blogducinemaback.application.dto.request.PostRequestDTO
import com.jun.blogducinemaback.application.dto.response.PostListDTO
import com.jun.blogducinemaback.application.dto.response.PostResponseDTO
import com.jun.blogducinemaback.application.model.PostService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RequestMapping("/forum")
@RestController
class ForumController(
    private val postService: PostService,
) {
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun createNewForumPost(
        @Validated
        @RequestBody post: PostRequestDTO
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

    @GetMapping("/all")
    fun listForumPostList(@RequestBody lastPostId: Int): ResponseEntity<PostListDTO> {
        val forumPostList = postService.getAllForumPostList(lastPostId)

        return if (forumPostList.isPresent)
            ResponseEntity
                .status(HttpStatus.OK)
                .body(forumPostList.get())
        else
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(PostListDTO(Page.empty()))
    }

    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Int): ResponseEntity<PostResponseDTO> {
        val post = postService.getPost(postId)

        return if (post.isPresent)
            ResponseEntity
                .status(HttpStatus.OK)
                .body(post.get())
        else
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(PostResponseDTO())
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    fun deletePost(@PathVariable postId: Int): ResponseEntity<Void> {
        val deletedPost = postService.deletePost(postId)

        return if (deletedPost)
            ResponseEntity
                .status(HttpStatus.OK)
                .build()
        else
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build()
    }
}