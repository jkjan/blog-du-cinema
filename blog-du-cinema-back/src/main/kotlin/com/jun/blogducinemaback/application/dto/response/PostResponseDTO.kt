package com.jun.blogducinemaback.application.dto.response

import com.jun.blogducinemaback.global.types.Default
import jakarta.validation.constraints.NotEmpty

data class PostResponseDTO(
    @NotEmpty
    var title: String = Default.TITLE,

    @NotEmpty
    var nickname: String = Default.NICKNAME,

    @NotEmpty
    var contentHtml: String = Default.CONTENT_HTML,

    @NotEmpty
    var createdAt: String = Default.DATETIME,

    var modifiedAt: String = Default.DATETIME
)