package com.jun.blogducinemaback.application.dto

import com.jun.blogducinemaback.domain.Post
import com.jun.blogducinemaback.domain.UserData
import com.jun.blogducinemaback.global.types.Default
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDateTime

class ForumPostDTO() {
    @NotEmpty
    var title: String = "title"

    var username: String = ""

    @NotEmpty
    var nickname: String = Default.NICKNAME

    @NotEmpty
    var contentHtml: String = "content"

    var createdAt: LocalDateTime? = Default.DATETIME

    constructor(post: Post): this() {
        this.title = post.title
        this.nickname = post.userData.nickname
        this.contentHtml = post.contentHtml
        this.createdAt = post.createdAt
    }

    fun toPost(): Post {
        val post = Post(this.title, this.contentHtml)
        return post
    }
}