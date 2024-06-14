package com.jun.blogducinemaback.controllers

import com.jun.blogducinemaback.domain.UserData
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class TestUser: UserDetailsService {
    companion object {
        const val USER = "user"
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return when (username) {
            USER -> UserData("user", "user", "ROLE_USER")
            else -> UserData()
        }
    }
}