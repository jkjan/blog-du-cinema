package com.jun.blogducinemaback

import com.jun.blogducinemaback.application.dto.UserSignInDTO
import com.jun.blogducinemaback.global.properties.JwtProperties
import com.jun.blogducinemaback.global.types.Role
import com.jun.blogducinemaback.global.types.TimeUnit
import com.jun.blogducinemaback.global.utils.JwtUtil
import com.jun.blogducinemaback.global.utils.logger
import io.jsonwebtoken.Jwts
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


@WebMvcTest
@Import(JwtProperties::class, JwtUtil::class)
class JwtTest {
    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var jwtProperties: JwtProperties

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun expirationTest() {
        val token = jwtUtil.createJwt("test", listOf(Role.USER.value))
        val payload = Jwts.parser().verifyWith(jwtUtil.secretKey).build().parseSignedClaims(token).payload
        val issuedAt = payload.issuedAt
        val expiration = payload.expiration
        val lifetime = expiration.time - issuedAt.time

        val lifetimeType = jwtProperties.lifetime.type
        val lifetimeDuration = jwtProperties.lifetime.duration

        when (lifetimeType) {
            TimeUnit.DAY.name -> assertThat(lifetime).isEqualTo(lifetimeDuration * 24 * 60 * 60 * 1000)
            TimeUnit.HOUR.name -> assertThat(lifetime).isEqualTo(lifetimeDuration * 60 * 60 * 1000)
            TimeUnit.MINUTE.name -> assertThat(lifetime).isEqualTo(lifetimeDuration * 60 * 1000)
        }
    }

    @Test
    fun cookieTest() {
        val token = jwtUtil.createJwt("test", listOf(Role.USER.value))
        val cookie = jwtUtil.createCookie(token).toString()

        logger().info(cookie)
        assertTrue(true)
    }
}