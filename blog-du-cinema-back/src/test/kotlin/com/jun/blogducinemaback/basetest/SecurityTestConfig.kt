package com.jun.blogducinemaback.basetest

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetailsService

@TestConfiguration
class SecurityTestConfig {
    @Bean
    @Primary
    fun userDetailsService(): UserDetailsService {
        return TestUser()
    }
}