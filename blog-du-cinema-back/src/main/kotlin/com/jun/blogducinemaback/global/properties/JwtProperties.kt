package com.jun.blogducinemaback.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var secret: String = "",
    var lifetime: Lifetime = Lifetime()
) {
    data class Lifetime (
        var type: String = "",
        var duration: Long = 0
    )
}
