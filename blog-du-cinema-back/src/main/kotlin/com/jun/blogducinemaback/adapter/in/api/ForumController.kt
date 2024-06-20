package com.jun.blogducinemaback.adapter.`in`.api

import com.jun.blogducinemaback.application.dto.ForumPostListDTO
import com.jun.blogducinemaback.global.utils.logger
import com.jun.blogducinemaback.application.dto.InfoPostDTO
import com.jun.blogducinemaback.application.model.ForumService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ForumController(
    private val httpServletRequest: HttpServletRequest,
    private val forumService: ForumService
) {
    private val logger = logger()

     @PostMapping("/forum")
     @PreAuthorize("hasRole('USER')")
    fun createNewPost(
        @Validated
        @RequestBody post: InfoPostDTO
    ): ResponseEntity<HashMap<String, String?>> {
        logger.info(post.title, post.contentHtml)
        logger.info(SecurityContextHolder.getContext().authentication.authorities.joinToString(", ") { it.authority })
        val isAuthorized = httpServletRequest.isUserInRole("ROLE_USER")
        logger.info("역할: $isAuthorized")

        return ResponseEntity.status(HttpStatus.CREATED).body(hashMapOf("title" to post.title, "contentHtml" to post.contentHtml))
    }

    @GetMapping("/forum")
    fun listPosts(@RequestBody page: Int): ResponseEntity<ForumPostListDTO> {
        val forumPostListPage = forumService.getForumPostList(page)

        val response = ResponseEntity
            .status(HttpStatus.OK)
            .body(forumPostListPage)

        return response
    }
}