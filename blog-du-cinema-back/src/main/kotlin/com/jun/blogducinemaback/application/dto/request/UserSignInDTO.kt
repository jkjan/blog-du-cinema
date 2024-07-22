package com.jun.blogducinemaback.application.dto.request
import com.jun.blogducinemaback.domain.UserData
import com.jun.blogducinemaback.global.types.Default
import jakarta.validation.constraints.NotBlank

class UserSignInDTO(
    @field:NotBlank
    var username: String = Default.USERNAME,

    @field:NotBlank
    var password: String = Default.PASSWORD
) {
    fun toUserData(): UserData {
        return UserData(username, password)
    }
}