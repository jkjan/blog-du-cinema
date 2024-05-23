package com.jun.blogducinemaback.entity

import jakarta.persistence.*

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
    @JoinColumn(name = "user_data_id")
    var userData: UserData? = null

    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_label",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "label_id")]
    )
    var labels: MutableList<Label>? = null
}