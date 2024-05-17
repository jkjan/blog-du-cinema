package com.jun.blogducinemaback.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Post (
    var title: String,
    var contentText: String,
    var contentHtml: String
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postId: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null

    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_label",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "label_id")]
    )
    var labels: MutableList<Label>? = null
}