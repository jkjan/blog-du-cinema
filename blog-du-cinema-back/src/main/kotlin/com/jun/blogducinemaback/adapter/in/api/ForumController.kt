package com.jun.blogducinemaback.adapter.`in`.api

import com.jun.blogducinemaback.application.dto.ForumPostDTO
import com.jun.blogducinemaback.application.dto.ForumPostListDTO
import com.jun.blogducinemaback.global.utils.logger
import com.jun.blogducinemaback.application.dto.InfoPostDTO
import com.jun.blogducinemaback.application.model.ForumService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class ForumController(
    private val httpServletRequest: HttpServletRequest,
    private val forumService: ForumService,
) {
    private val logger = logger()

    @PostMapping("/forum")
    @PreAuthorize("hasRole('USER')")
    fun createNewPost(
        @Validated
        @RequestBody post: ForumPostDTO
    ): ResponseEntity<HashMap<String, String?>> {
        val success = forumService.createNewPost(post)

        lateinit var status: HttpStatus
        if (success) {
            status = HttpStatus.CREATED
        }
        else {
            status = HttpStatus.FORBIDDEN
        }

        return ResponseEntity.status(status).body(hashMapOf())
    }

    @GetMapping("/forum")
    fun listPosts(@RequestBody page: Int): ResponseEntity<ForumPostListDTO> {
        val forumPostListPage = forumService.getForumPostList(page)

        val response = ResponseEntity
            .status(HttpStatus.OK)
            .body(forumPostListPage)

        return response
    }

    @GetMapping("/forum/{postId}")
    fun getPost(@PathVariable postId: Int): ResponseEntity<ForumPostDTO> {
        val post = forumService.getPost(postId)

        if (post.isPresent)
            return ResponseEntity.status(HttpStatus.OK).body(post.get())
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ForumPostDTO())
        }
    }

    @DeleteMapping("/forum/{postId}")
    @PreAuthorize("hasRole('USER')")
    fun deletePost(@PathVariable postId: Int): ResponseEntity<HashMap<String, String>> {
        val deleted = forumService.deletePost(postId)
        lateinit var status: HttpStatus
        val body: HashMap<String, String> = hashMapOf()
        if (deleted) {
            status = HttpStatus.OK
            body["message"] = "삭제되었습니다."
        }
        else {
            status = HttpStatus.FORBIDDEN
            body["message"] = "권한이 없습니다."
        }

        return ResponseEntity.status(status).body(body)
    }
}