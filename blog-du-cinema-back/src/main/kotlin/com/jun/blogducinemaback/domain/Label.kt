package com.jun.blogducinemaback.domain

import com.jun.blogducinemaback.global.types.Default
import jakarta.persistence.*

@Entity
class Label(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var labelId: Int? = 0,
    var labelNum: Int = 0,
    var labelName: String = Default.LABEL_NAME,
) : BaseTimeEntity() {

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