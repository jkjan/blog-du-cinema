package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.application.dto.response.InfoLabelDTO
import com.jun.blogducinemaback.adapter.out.persistence.InfoRepository
import com.jun.blogducinemaback.application.dto.response.PostListDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
class InfoService(val infoRepository: InfoRepository) {
    fun getInfoLabels() : List<InfoLabelDTO> {
        val excludingCategories = listOf("forum")
        val infoLabels = infoRepository.findLabelByCategoryNotIn(excludingCategories)

        return if (infoLabels.isEmpty) {
            emptyList()
        } else {
            infoLabels.get().map { InfoLabelDTO(it) }
        }
    }
}