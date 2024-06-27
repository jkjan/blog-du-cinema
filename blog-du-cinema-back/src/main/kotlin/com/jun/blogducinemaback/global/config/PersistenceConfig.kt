package com.jun.blogducinemaback.global.config

import com.jun.blogducinemaback.adapter.out.persistence.BaseRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.jun.blogducinemaback.adapter.out.persistence"],
    repositoryBaseClass = BaseRepositoryImpl::class
)
class PersistenceConfig {
}