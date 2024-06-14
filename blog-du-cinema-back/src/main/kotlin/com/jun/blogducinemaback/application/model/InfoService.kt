package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.application.dto.InfoLabelDTO
import com.jun.blogducinemaback.application.dto.InfoPostDTO
import com.jun.blogducinemaback.adapter.out.persistence.InfoRepository
import org.springframework.stereotype.Service

@Service
class InfoService(val infoRepository: InfoRepository) {
    fun getInfoLabels() : List<InfoLabelDTO> {
        val excludingCategories = listOf("forum")
        val infoLabels = infoRepository.findLabelByCategoryNotIn(excludingCategories)

        if (infoLabels.isEmpty) {
            return emptyList()
        }
        else {
            return infoLabels.get().map { InfoLabelDTO(it) }
        }
    }

    fun getPostsForLabel(labelId: Int): List<InfoPostDTO> {
        val label = infoRepository.findById(labelId)

        if (label.isEmpty) {
            return emptyList()
        }
        else {
            val posts = label.get().postLabel
            return posts.map { InfoPostDTO(it.post) }
        }
    }
}