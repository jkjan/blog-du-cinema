package com.jun.blogducinemaback.entity

import jakarta.persistence.*
import org.hibernate.annotations.NaturalId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class User(
    @NaturalId
    val username: String,
    val password: String,
    ) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Int? = null

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = false)
    var posts: MutableList<Post>? = null
}
