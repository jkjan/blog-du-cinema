package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.global.utils.logger
import com.jun.blogducinemaback.application.dto.UserSignUpDTO
import com.jun.blogducinemaback.domain.Authority
import com.jun.blogducinemaback.domain.UserData
import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val pbkdf2PasswordEncoder: Pbkdf2PasswordEncoder,
): UserDetailsService {
    private val logger = logger()

    override fun loadUserByUsername(username: String): UserData {
        val user = userRepository.findByUsername(username)

        if (user.isPresent)
            return user.get()
        else
            throw UsernameNotFoundException(username)
    }

    @Transactional
    fun signUp(user: UserSignUpDTO): Optional<UserData> {
        val duplicatedUser = userRepository.findByUsername(user.username)

        if (duplicatedUser.isEmpty) {
            logger.info("User ${user.username} is not registered.")
            val encoder = pbkdf2PasswordEncoder
            user.password = encoder.encode(user.password)
            val newUser = user.toUserData()
            val defaultAuthority = Authority()
            newUser.addAuthority(defaultAuthority)
            val registeredUser = userRepository.save(newUser)
            return Optional.of(registeredUser)
        }
        else {
            logger.info("User ${user.username} is already registered.")
            return Optional.empty()
        }
    }
}
