package com.jun.blogducinemaback.application.dto.request

import com.jun.blogducinemaback.global.types.Default
import jakarta.validation.constraints.NotEmpty

data class PostRequestDTO (
    @NotEmpty
    var username: String = Default.USERNAME,

    @NotEmpty
    var title: String = Default.TITLE,

    @NotEmpty
    var contentHtml: String = Default.CONTENT_HTML,

    @NotEmpty
    var category: String = Default.CATEGORY_FORUM,

    var labelIds: List<Int> = listOf()
)