package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import com.jun.blogducinemaback.domain.UserData
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDataDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserData {
        val user = userRepository.nnnnn(username)

        if (user.isPresent)
            return user.get()
        else
            throw UsernameNotFoundException(username)
    }
}