package com.jun.blogducinemaback.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDateTime

@EnableJpaAuditing
@MappedSuperclass
abstract class BaseTimeEntity {
    @CreatedDate
    @Column(insertable = false, updatable = false)
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(insertable = false, updatable = false)
    var modifiedAt: LocalDateTime? = null
}