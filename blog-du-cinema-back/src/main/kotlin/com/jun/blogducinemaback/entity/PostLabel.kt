package com.jun.blogducinemaback.entity

import jakarta.persistence.*

@Table(name = "post_label")
@Entity
class PostLabel(
    @Column(name = "label_id")
    val labelId: Int,

    @Column(name = "post_id")
    val postId: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postLabelId: Int? = null
}