package com.jun.blogducinemaback.config
import com.jun.blogducinemaback.entity.Authority
import com.jun.blogducinemaback.entity.UserData
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtil {
    private val secretKey: SecretKey = Jwts.SIG.HS256.key().build()

    fun getUsername(token: String?): String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
            "username",
            String::class.java
        )
    }

    fun getAuthorities(token: String?): List<String> {
        try {
            val authoritiesString = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
                "role",
                String::class.java
            )

            return authoritiesString.split(",")
        }
        catch (e: MalformedJwtException) {
            return emptyList()
        }
    }

    fun isExpired(token: String?): Boolean {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.expiration.before(Date())
        }
        catch (e: MalformedJwtException) {
            return true
        }
    }

    fun createJwt(username: String, authorities: List<String>): String {
        val now = Date()
        val expiration = Date(now.time + 1000 * 60 * 60 * 24)
        val authoritiesString = authorities.joinToString(",")

        return Jwts.builder()
            .claim("username", username)
            .claim("role", authoritiesString)
            .issuedAt(Date())
            .expiration(expiration)
            .signWith(secretKey)
            .compact()
    }
}