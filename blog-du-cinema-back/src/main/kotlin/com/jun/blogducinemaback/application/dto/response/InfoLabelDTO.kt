package com.jun.blogducinemaback.application.dto.response

import com.google.gson.Gson
import com.jun.blogducinemaback.domain.Label

class InfoLabelDTO(label: Label) {
    var labelId: Int? = null
    var labelNum: Int? = null
    var labelName: String? = null
    var category: String? = null

    init {
        this.labelId = label.labelId
        this.labelNum = label.labelNum
        this.labelName = label.labelName
        this.category = label.category
    }
}