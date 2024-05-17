package com.jun.blogducinemaback.services

import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.entity.Label
import com.jun.blogducinemaback.entity.Post
import com.jun.blogducinemaback.repositories.InfoRepository
import org.springframework.stereotype.Service

@Service
class InfoService(val infoRepository: InfoRepository) {
    fun getInfoLabels() : List<InfoLabelDTO>? {
        val excludingCategories = listOf("forum")
        val infoLabels = infoRepository.findLabelByCategoryNotIn(excludingCategories)
        return infoLabels?.map { InfoLabelDTO(it) }
    }

    fun getPostsForInfoLabel(labelId: Int): List<InfoPostDTO>? {
        val label = infoRepository.findById(labelId)

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