package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.Label
import com.jun.blogducinemaback.entity.Post
import com.jun.blogducinemaback.entity.UserData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    fun labelPostsTest() {
        val user1 = UserData("user1")
        val user2 = UserData("user2")
        val user3 = UserData("user3")

        val label1 = Label(1, "A", "test")
        val label2 = Label(2, "B", "test")
        val label3 = Label(3, "C", "test")

        val post1 = Post("test1 title", "test1 html")
        val post2 = Post("test2 title", "test2 html")
        val post3 = Post("test3 title", "test3 html")

        post1.addLabels(listOf(label1, label2, label3))
        post2.addLabels(listOf(label1, label2, label3))
        post3.addLabels(listOf(label1, label2, label3))

        user1.writePost(post1)
        user2.writePost(post2)
        user3.writePost(post3)

        postRepository.saveAll(listOf(post1, post2, post3))

        val posts: List<Post> = postRepository.findAll()
        assertThat(posts).hasSize(3)
        posts.forEach {
            post ->
            assertThat(post.postLabel).hasSize(3)
            assertThat(post.postLabel[0].label.labelName).isEqualTo(label1.labelName)
            assertThat(post.postLabel[1].label.labelName).isEqualTo(label2.labelName)
            assertThat(post.postLabel[2].label.labelName).isEqualTo(label3.labelName)
        }
    }
}