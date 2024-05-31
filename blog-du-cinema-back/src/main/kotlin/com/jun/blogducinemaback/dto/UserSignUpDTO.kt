package com.jun.blogducinemaback.dto
import com.jun.blogducinemaback.entity.UserData
import jakarta.validation.constraints.NotBlank

class UserSignUpDTO(
    @field:NotBlank
    var username: String = "",

    @field:NotBlank
    var password: String = ""
) {
    fun toUserData(): UserData {
        return UserData(username, password)
    }
}