package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.global.utils.logger
import com.jun.blogducinemaback.application.dto.UserSignUpDTO
import com.jun.blogducinemaback.domain.Authority
import com.jun.blogducinemaback.domain.UserData
import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import com.jun.blogducinemaback.application.dto.UserSignInDTO
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val pbkdf2PasswordEncoder: Pbkdf2PasswordEncoder,
    private val userDataDetailsService: UserDataDetailsService,
    private val authenticationManager: AuthenticationManager
) {
    private val logger = logger()

    @Transactional
    fun signUp(user: UserSignUpDTO): Optional<UserData> {
        val duplicatedUser = userRepository.nnnnn(user.username)

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

    fun signIn(user: UserSignInDTO): Optional<Authentication> {
        try {
            val token = UsernamePasswordAuthenticationToken(user.username, user.password)
            val authentication = authenticationManager.authenticate(token)

            return Optional.of(authentication)
        } catch (e: BadCredentialsException) {
            return Optional.empty()
        }
    }

    fun getNickname(username: String): String? {
        val user = this.userDataDetailsService.loadUserByUsername(username)
        return user.nickname
    }
}
