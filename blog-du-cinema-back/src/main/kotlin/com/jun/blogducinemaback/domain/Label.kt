package com.jun.blogducinemaback.domain

import jakarta.persistence.*

@Entity
class Label(
    var labelNum: Int = 0,
    var labelName: String = "unknown",
    var category: String = "unknown"
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var labelId: Int? = null

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "label", fetch = FetchType.LAZY)
    var postLabel: MutableList<PostLabel> = mutableListOf()

    fun registerPosts(newPosts: List<Post>) {
        newPosts.forEach {
            post ->
            val newPostLabel = PostLabel(post, this)
            post.postLabel.add(newPostLabel)
            postLabel.add(newPostLabel)
        }
    }
}