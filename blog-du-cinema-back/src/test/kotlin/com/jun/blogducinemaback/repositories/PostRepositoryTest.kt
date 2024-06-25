package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.adapter.out.persistence.PostRepository
import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import com.jun.blogducinemaback.application.dto.ForumPostListDTO
import com.jun.blogducinemaback.domain.Label
import com.jun.blogducinemaback.domain.Post
import com.jun.blogducinemaback.domain.UserData
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    lateinit var postRepository: PostRepository

    @BeforeEach
    fun clearPK() {
        this.entityManager.createNativeQuery("ALTER TABLE post ALTER COLUMN `post_id` RESTART WITH 1").executeUpdate()
        this.entityManager.createNativeQuery("ALTER TABLE user_data ALTER COLUMN `user_id` RESTART WITH 1").executeUpdate()
    }

    @Test
    fun `post, user, label 의존 관계`() {
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


    @Test
    fun `offset 방식 post 페이징 테스트`() {
        postRepository.saveAll(listOf(
            Post("title 1", "html 1"),
            Post("title 2", "html 2"),
            Post("title 3", "html 3"),
            Post("title 4", "html 4"),
            Post("title 5", "html 5"),
            Post("title 6", "html 6"),
            Post("title 7", "html 7"),
            )
        )

        val pageable = PageRequest.of(0, 5)
        val forumPostPage = postRepository.findAll(pageable)

        assertThat(forumPostPage.content).hasSize(5)
    }

    @Test
    fun `offset 방식 post 2번째 페이지 테스트`() {
        postRepository.saveAll(
            listOf(
                Post("title 1", "html 1"),
                Post("title 2", "html 2"),
                Post("title 3", "html 3"),
                Post("title 4", "html 4"),
                Post("title 5", "html 5"),
                Post("title 6", "html 6"),
                Post("title 7", "html 7"),
            )
        )

        val pageable = PageRequest.of(1, 5)
        val forumPostPage = postRepository.findAll(pageable)

        assertThat(forumPostPage.content).hasSize(2)
        assertThat(forumPostPage.totalPages).isEqualTo(2)
    }

    @Test
    fun `커서 방식 페이징 테스트`() {
        postRepository.saveAll(
            listOf(
                Post("title 1", "html 1"),
                Post("title 2", "html 2"),
                Post("title 3", "html 3"),
                Post("title 4", "html 4"),
                Post("title 5", "html 5"),
                Post("title 6", "html 6"),
                Post("title 7", "html 7"),
            )
        )

        val page = PageRequest.of(0, 5)

        val posts = postRepository.findPostsByPostIdLessThanOrderByPostIdDesc(4, page)
        val list = ForumPostListDTO(posts)

        assertThat(posts).hasSize(3)
        assertThat(list.postEntries.size).isEqualTo(3)
        assertThat(list.isLast).isTrue()
    }

    @Test
    fun `새로운 글 썼을 때 사용자 ID 추가됨`() {
        // given
        val user1 = UserData("user1", "password1")
        userRepository.save(user1)

        val post = Post("title", "html")
        user1.writePost(post)
        postRepository.save(post)

        // when
        val npost = postRepository.findById(1).get()

        // then
        assertThat(npost.userData.username).isEqualTo("user1")
    }

    @Test
    fun `사용자 글 수정`() {
        // given
        val user1 = UserData("user1", "password1")
        val post1 = Post("title1", "html1")
        user1.writePost(post1)
        userRepository.save(user1)

        // when
        val editingPost = postRepository.findById(1)
        editingPost.get().contentHtml = "editted html1"
        postRepository.save(editingPost.get())

        // then
        val post2 = postRepository.findById(1).get()
        val post3 = userRepository.nnnnn("user1").get().posts
        assertThat(post3).hasSize(1)
        assertThat(post2.title).isEqualTo(post3[0].title)
    }

}