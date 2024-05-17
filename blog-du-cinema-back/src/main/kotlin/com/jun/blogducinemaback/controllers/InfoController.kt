package com.jun.blogducinemaback.controllers

import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.entity.Label
import com.jun.blogducinemaback.entity.Post
import com.jun.blogducinemaback.services.InfoService
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class InfoController(val infoService: InfoService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/info/category")
    fun getInfoLabels(): ResponseEntity<List<InfoLabelDTO>> {
        val infoLabels = infoService.getInfoLabels()

        logger.atLevel(Level.DEBUG)
        logger.debug("Printing info labels")
        logger.debug(infoLabels.toString())

        lateinit var response: ResponseEntity<List<InfoLabelDTO>>

        if (infoLabels.isNullOrEmpty()) {
           response = ResponseEntity(null, HttpStatus.BAD_REQUEST)
        } else {
            response = ResponseEntity(infoLabels, HttpStatus.OK)
        }

        return response
    }

    @GetMapping("info/post/{labelId}")
    fun getPostsForInfoLabel(@PathVariable labelId: Int): ResponseEntity<List<InfoPostDTO>> {
        val posts = infoService.getPostsForInfoLabel(labelId)
        lateinit var response: ResponseEntity<List<InfoPostDTO>>

        if (posts.isNullOrEmpty()) {
            response = ResponseEntity(null, HttpStatus.BAD_REQUEST)
        } else {
            response = ResponseEntity(posts, HttpStatus.OK)
        }

        return response
    }
}