package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.Label
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InfoRepository: JpaRepository<Label, Int> {
    fun findLabelByCategoryNotIn(excludingCategories: List<String>): ArrayList<Label>?
}