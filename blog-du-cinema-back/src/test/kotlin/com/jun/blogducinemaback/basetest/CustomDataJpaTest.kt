package com.jun.blogducinemaback.basetest

import com.jun.blogducinemaback.global.config.PersistenceConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(PersistenceConfig::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CustomDataJpaTest
