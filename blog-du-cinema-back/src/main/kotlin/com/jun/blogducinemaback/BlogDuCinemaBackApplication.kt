package com.jun.blogducinemaback

import com.jun.blogducinemaback.adapter.out.persistence.BaseRepository
import com.jun.blogducinemaback.adapter.out.persistence.BaseRepositoryImpl
import com.jun.blogducinemaback.global.properties.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ConfigurationPropertiesScan
class BlogDuCinemaBackApplication

fun main(args: Array<String>) {
    runApplication<BlogDuCinemaBackApplication>(*args)
}