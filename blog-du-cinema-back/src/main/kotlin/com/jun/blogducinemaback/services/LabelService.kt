package com.jun.blogducinemaback.services

import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.repositories.LabelRepository
import org.springframework.stereotype.Service

@Service
class LabelService(val labelRepository: LabelRepository) {
    fun getInfoLabels() : List<InfoLabelDTO>? {
        val excludingCategories = listOf("forum")
        val infoLabels = labelRepository.findLabelByCategoryNotIn(excludingCategories)
        return infoLabels?.map { InfoLabelDTO(it) }
    }

    fun getPostsForLabel(labelId: Int): List<InfoPostDTO>? {
        val label = labelRepository.findById(labelId)

        if (label.isPresent) {
            val posts = label.get().posts
            if (posts == null) {
                return null
            }
            else {
                return posts.map { InfoPostDTO(it) }
            }
        }
        else {
            return null
        }
    }
}