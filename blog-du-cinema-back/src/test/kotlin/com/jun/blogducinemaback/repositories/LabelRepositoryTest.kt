package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.Label
import com.jun.blogducinemaback.entity.Post
import com.jun.blogducinemaback.entity.UserData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql

@DataJpaTest
class LabelRepositoryTest(@Autowired val labelRepository: LabelRepository) {
    @BeforeEach
    fun insertLabelsAndPosts() {
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

        labelRepository.saveAll(listOf(label1, label2, label3))
    }

    @Test
    fun getInfoLabels() {
        // given
        val label4 = Label(4, "D", "NO_CATEGORY")
        val label5 = Label(5, "E", "NO_CATEGORY")
        labelRepository.saveAll(listOf(label4, label5))

        // when
        val excludingCategories = listOf("NO_CATEGORY")
        val infoLabels = labelRepository.findLabelByCategoryNotIn(excludingCategories)
        infoLabels.get().sortBy { it.labelNum }

        // then
        assertThat(infoLabels.get()).hasSize(3)
        assertTrue {
            infoLabels.get()[0].labelName == "A" &&
            infoLabels.get()[1].labelName == "B" &&
            infoLabels.get()[2].labelName == "C"
        }
    }

    @Test
    fun getPostsForLabel() {
        // when
        val labels = labelRepository.findAll()

        // then
        assertThat(labels).hasSize(3)

        assertThat(labels[0].labelName).isEqualTo("A")
        assertThat(labels[1].labelName).isEqualTo("B")
        assertThat(labels[2].labelName).isEqualTo("C")

        labels.forEach {
            label ->
            assertThat(label.postLabel).hasSize(3)
            assertThat(label.postLabel[0].post.title).isEqualTo("test1 title")
            assertThat(label.postLabel[1].post.title).isEqualTo("test2 title")
            assertThat(label.postLabel[2].post.title).isEqualTo("test3 title")
        }
    }
}