package com.jun.blogducinemaback.dto

import com.jun.blogducinemaback.entity.Post
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated

class InfoPostDTO(
    @field:NotEmpty
    var title: String,

    @field:NotEmpty
    var contentHtml: String
) {
    constructor(post: Post) : this(post.title, post.contentHtml)
}