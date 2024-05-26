package com.jun.blogducinemaback.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)

        registry
            .addMapping("/**")
            .allowedOrigins("http://127.0.0.1:5173")
            .allowedOrigins("http://localhost:5173")
            .allowedOrigins("http://211.197.212.209:5173")
            .allowedMethods("*")
            .allowCredentials(true)
            .maxAge(3000)
    }
}