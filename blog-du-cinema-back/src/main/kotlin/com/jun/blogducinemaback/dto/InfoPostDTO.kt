package com.jun.blogducinemaback.dto

import com.jun.blogducinemaback.entity.Post

class InfoPostDTO(post: Post) {
    var title: String? = null
    var contentHtml: String? = null

    init {
        this.title = post.title
        this.contentHtml = post.contentHtml
    }
}