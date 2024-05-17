package com.jun.blogducinemaback.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.time.LocalDateTime

@Entity
class Label(
    var labelNum: Int,
    var labelName: String,
    var category: String
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var labelId: Int? = null

    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_label",
        joinColumns = [JoinColumn(name = "label_id")],
        inverseJoinColumns = [JoinColumn(name = "post_id")]
    )
    var posts: MutableList<Post>? = null
}