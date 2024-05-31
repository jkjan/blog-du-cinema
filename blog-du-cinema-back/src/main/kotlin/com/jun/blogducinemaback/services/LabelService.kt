package com.jun.blogducinemaback.services

import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.repositories.LabelRepository
import org.springframework.stereotype.Service

@Service
class LabelService(val labelRepository: LabelRepository) {
    fun getInfoLabels() : List<InfoLabelDTO> {
        val excludingCategories = listOf("forum")
        val infoLabels = labelRepository.findLabelByCategoryNotIn(excludingCategories)

        if (infoLabels.isEmpty) {
            return emptyList()
        }
        else {
            return infoLabels.get().map { InfoLabelDTO(it) }
        }
    }

    fun getPostsForLabel(labelId: Int): List<InfoPostDTO> {
        val label = labelRepository.findById(labelId)

        if (label.isEmpty) {
            return emptyList()
        }
        else {
            val posts = label.get().postLabel
            return posts.map { InfoPostDTO(it.post) }
        }
    }
}