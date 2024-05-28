package com.jun.blogducinemaback.repositories

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql

@DataJpaTest
@AutoConfigureTestDatabase
@Sql("classpath:db/data.sql")
class LabelRepositoryTest(@Autowired val labelRepository: LabelRepository) {

    @Test
    fun getInfoLabels() {
        val excludingCategories = listOf("forum")
        val infoLabels = labelRepository.findLabelByCategoryNotIn(excludingCategories)
        assertFalse(infoLabels.isNullOrEmpty())

        val categories = infoLabels!!.map { it.category }.toSet()
        val categoriesReal = setOf("영화의 역사", "다양한 장르", "단어 사전", "영화 방법론")
        assertTrue(categories == categoriesReal)
    }

    @Test
    fun getPostsForLabel() {
        val label = labelRepository.findById(20);

        assertTrue(label.isPresent)

        val posts = label.get().posts

        assertFalse(posts.isNullOrEmpty())
        assertTrue(posts!!.size == 5)
    }
}