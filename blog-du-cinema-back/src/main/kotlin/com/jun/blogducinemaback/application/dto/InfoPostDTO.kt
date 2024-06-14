package com.jun.blogducinemaback.application.dto

import com.jun.blogducinemaback.domain.Post
import jakarta.validation.constraints.NotEmpty

class InfoPostDTO(
    @field:NotEmpty
    var title: String,

    @field:NotEmpty
    var contentHtml: String
) {
    constructor(post: Post) : this(post.title, post.contentHtml)
}