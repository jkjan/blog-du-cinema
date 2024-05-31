package com.jun.blogducinemaback.config

import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors {
                disable()
            }
            csrf {
                disable()
            }
        }

       return http.build()
    }

    @Bean
    fun pbkdf2PasswordEncoder(): Pbkdf2PasswordEncoder {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    }

}