package com.jun.blogducinemaback.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.time.LocalDateTime

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