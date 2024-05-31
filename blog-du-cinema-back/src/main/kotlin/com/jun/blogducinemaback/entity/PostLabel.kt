package com.jun.blogducinemaback.entity

import jakarta.persistence.*

@Entity
class PostLabel(
    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "post_id")
    var post: Post,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "label_id")
    var label: Label,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postLabelId: Int? = null
}