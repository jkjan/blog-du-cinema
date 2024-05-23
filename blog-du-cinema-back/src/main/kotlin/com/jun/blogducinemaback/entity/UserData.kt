package com.jun.blogducinemaback.entity

import jakarta.persistence.*
import org.hibernate.annotations.NaturalId

@Entity
class UserData(
    @NaturalId
    val username: String,
    val password: String,
    ) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userDataId: Int? = null

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "userData", orphanRemoval = false)
    var posts: MutableList<Post>? = null
}
