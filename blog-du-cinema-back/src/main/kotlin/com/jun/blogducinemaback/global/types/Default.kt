package com.jun.blogducinemaback.global.types

import java.time.LocalDateTime
import java.time.Month

class Default {
    companion object {
        val DATETIME: LocalDateTime = LocalDateTime.of(1997, Month.OCTOBER, 15, 11, 0, 0)
        const val USERNAME: String = "default username"
        const val NICKNAME: String = "default nickname"
        const val PAGE_SIZE: Int = 10
    }
}
