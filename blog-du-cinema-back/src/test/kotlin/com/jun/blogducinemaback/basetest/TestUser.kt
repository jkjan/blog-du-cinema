package com.jun.blogducinemaback.basetest

import com.jun.blogducinemaback.domain.UserData
import com.jun.blogducinemaback.global.types.Role
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class TestUser: UserDetailsService {
    companion object {
        const val USER = "user"
        const val ADMIN = "admin"
        const val SUPERUSER = "superuser"
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return when (username) {
            USER -> UserData("user", "user", Role.USER)
            ADMIN -> UserData("admin", "admin", Role.ADMIN)
            SUPERUSER -> UserData("superuser", "superuser", Role.SUPERUSER)
            else -> UserData()
        }
    }
}