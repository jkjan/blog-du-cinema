package com.jun.blogducinemaback.global.utils

import com.jun.blogducinemaback.domain.Authority
import com.jun.blogducinemaback.global.properties.JwtProperties
import com.jun.blogducinemaback.global.types.TimeUnit
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseCookie.ResponseCookieBuilder
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtil(jwtProperties: JwtProperties) {
    final val secretString: String = jwtProperties.secret
    final val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString))
    final var lifetime = 0L

    init {
        val lifetimeType = TimeUnit.valueOf(jwtProperties.lifetime.type)
        lifetime = lifetimeType.getLifetimeInMilli(jwtProperties.lifetime.duration)
    }

    fun getUsername(token: String?): String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
            "username",
            String::class.java
        )
    }

    fun getAuthorities(token: String?): List<Authority> {
        try {
            val authoritiesString = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
                "role",
                String::class.java
            )

            return authoritiesString.split(",").map { authorityName -> Authority(authorityName) }
        } catch (e: JwtException) {
            return emptyList()
        }
    }

    fun isExpired(token: String?): Boolean {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).payload.expiration.before(Date())
        } catch (e: JwtException) {
            return true
        }
    }

    fun createJwt(username: String, authorities: List<String>): String {
        val now = Date()
        val expiration = Date(now.time + lifetime)
        val authoritiesString = authorities.joinToString(",")

        return Jwts.builder()
            .claim("username", username)
            .claim("role", authoritiesString)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(secretKey)
            .compact()
    }

    fun createCookie(token: String): ResponseCookie {
        return ResponseCookie
            .from("jwt-token", token)
            .httpOnly(true)
            .path("/")
            .sameSite("None")
            .maxAge(lifetime / 1000)
            .secure(false)
            .build()
    }
}