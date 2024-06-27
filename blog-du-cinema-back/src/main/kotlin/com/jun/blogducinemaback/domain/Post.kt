package com.jun.blogducinemaback.domain

import jakarta.persistence.*

@Entity
class Post (
    var title: String = "unknown",
    var contentHtml: String = "unknown"
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postId: Int = 0

    @ManyToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var userData: UserData = UserData()

    var contentText: String = ""

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "post", fetch = FetchType.LAZY)
    var postLabel: MutableList<PostLabel> = mutableListOf()

    fun addLabel(newLabel: Label) {
        postLabel.add(PostLabel(this, newLabel))
    }

    fun addLabels(newLabels: List<Label>) {
        newLabels.forEach {
            label ->
            val newPostLabel = PostLabel(this, label)
            label.postLabel.add(newPostLabel)
            postLabel.add(newPostLabel)
        }
    }
}