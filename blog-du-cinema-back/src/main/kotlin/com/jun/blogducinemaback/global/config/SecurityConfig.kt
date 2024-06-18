package com.jun.blogducinemaback.global.config

import com.jun.blogducinemaback.application.model.UserService
import com.jun.blogducinemaback.adapter.`in`.filter.JwtFilter
import com.jun.blogducinemaback.global.utils.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
class SecurityConfig(private val jwtUtil: JwtUtil) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors {
                disable()
            }
            csrf {
                disable()
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtFilter(jwtUtil))
        }

        return http.build()
    }

    @Bean
    fun pbkdf2PasswordEncoder(): Pbkdf2PasswordEncoder {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: UserService,
        passwordEncoder: Pbkdf2PasswordEncoder
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        val providerManager = ProviderManager(authenticationProvider)
        return providerManager
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val hierarchy = RoleHierarchyImpl()
        hierarchy.setHierarchy("ROLE_SUPERUSER > ROLE_ADMIN > ROLE_USER")
        return hierarchy
    }

    @Bean
    fun methodSecurityExpressionHandler(roleHierarchy: RoleHierarchy?): MethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        expressionHandler.setRoleHierarchy(roleHierarchy)
        return expressionHandler
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry
                    .addMapping("/**")
                    .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173")
                    .allowedMethods("*")
                    .allowCredentials(true)
                    .exposedHeaders("Authorization", "Set-Cookie")
                    .maxAge(300)
            }
        }
    }
}
