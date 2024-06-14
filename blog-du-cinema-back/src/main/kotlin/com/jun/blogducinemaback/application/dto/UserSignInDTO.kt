package com.jun.blogducinemaback.application.dto
import com.jun.blogducinemaback.domain.UserData
import jakarta.validation.constraints.NotBlank

class UserSignInDTO(
    @field:NotBlank
    var username: String = "",

    @field:NotBlank
    var password: String = ""
) {
    fun toUserData(): UserData {
        return UserData(username, password)
    }
}