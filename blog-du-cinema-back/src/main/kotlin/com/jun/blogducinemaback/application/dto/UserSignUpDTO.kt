package com.jun.blogducinemaback.application.dto
import com.jun.blogducinemaback.domain.UserData
import jakarta.validation.constraints.NotBlank

class UserSignUpDTO(
    @field:NotBlank
    var username: String = "unknown",

    @field:NotBlank
    var password: String = "unknown"
) {
    fun toUserData(): UserData {
        return UserData(username, password)
    }
}