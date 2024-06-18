package com.jun.blogducinemaback.adapter.out.persistence

import com.jun.blogducinemaback.domain.Label
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface InfoRepository: JpaRepository<Label, Int> {
    fun findLabelByCategoryNotIn(excludingCategories: List<String>): Optional<ArrayList<Label>>
}