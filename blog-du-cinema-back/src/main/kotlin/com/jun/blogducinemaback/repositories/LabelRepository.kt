package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.Label
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface LabelRepository: JpaRepository<Label, Int> {
    fun findLabelByCategoryNotIn(excludingCategories: List<String>): Optional<ArrayList<Label>>
}