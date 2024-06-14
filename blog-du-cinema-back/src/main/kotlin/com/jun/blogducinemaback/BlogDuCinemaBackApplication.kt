package com.jun.blogducinemaback

import com.jun.blogducinemaback.global.properties.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties(JwtProperties::class)
class BlogDuCinemaBackApplication

fun main(args: Array<String>) {
    runApplication<BlogDuCinemaBackApplication>(*args)
}
