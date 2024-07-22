package com.jun.blogducinemaback.application.dto.request
import com.jun.blogducinemaback.domain.UserData
import com.jun.blogducinemaback.global.types.Default
import jakarta.validation.constraints.NotBlank

class UserSignUpDTO(
    @field:NotBlank
    var username: String = Default.USERNAME,

    @field:NotBlank
    var password: String = Default.PASSWORD,

    @field:NotBlank
    var nickname: String = Default.NICKNAME
) {
    fun toUserData(): UserData {
        val user = UserData(username, password)
        user.nickname = this.nickname
        return user
    }
}