package com.jun.blogducinemaback.application.dto.response

import com.jun.blogducinemaback.domain.Post
import org.springframework.data.domain.Page
import java.time.LocalDateTime

class PostListDTO(page: Page<Post>) {
    data class PostEntry(
        var postId: Int,
        var title: String,
        var nickname: String,
        var createdAt: String
    )

    var postEntries: List<PostEntry> = listOf()
    var isLast: Boolean = true

    init {
        postEntries = page.content.map { post ->
            PostEntry(post.postId, post.title, post.userData.nickname, post.createdAt)
        }
        isLast = page.isLast
    }
}