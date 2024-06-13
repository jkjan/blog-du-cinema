package com.jun.blogducinemaback

import com.jun.blogducinemaback.config.JwtConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class BlogDuCinemaBackApplication

fun main(args: Array<String>) {
    runApplication<BlogDuCinemaBackApplication>(*args)
}
