package com.jun.blogducinemaback.services

import com.jun.blogducinemaback.config.logger
import com.jun.blogducinemaback.dto.UserSignInDTO
import com.jun.blogducinemaback.dto.UserSignUpDTO
import com.jun.blogducinemaback.entity.Authority
import com.jun.blogducinemaback.entity.UserData
import com.jun.blogducinemaback.repositories.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val pbkdf2PasswordEncoder: Pbkdf2PasswordEncoder,
): UserDetailsService {
    private val logger = logger()

    override fun loadUserByUsername(username: String?): UserData {
        if (username.isNullOrBlank()) {
            return UserData()
        }
        else {
            val user = userRepository.findByUsername(username)
            if (user.isPresent)
                return user.get()
            else
                return UserData()
        }
    }

    @Transactional
    fun signUp(user: UserSignUpDTO): Boolean {
        val duplicatedUser = userRepository.findByUsername(user.username)

        if (duplicatedUser.isEmpty) {
            logger.info("User ${user.username} is not registered.")
            val encoder = pbkdf2PasswordEncoder
            user.password = encoder.encode(user.password)
            val newUser = user.toUserData()
            val defaultAuthority = Authority()
            newUser.addAuthority(defaultAuthority)
            userRepository.save(newUser)
            return true
        }
        else {
            logger.info("User ${user.username} is already registered.")
            return false
        }
    }
}
