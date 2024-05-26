package com.jun.blogducinemaback.controllers

import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.services.InfoService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
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

        logger.debug("Printing info labels")
        logger.debug(infoLabels.toString())

        lateinit var status: HttpStatus
        val headers = HttpHeaders()
        val body: List<InfoLabelDTO>?

        if (infoLabels.isNullOrEmpty()) {
            status = HttpStatus.NO_CONTENT
            body = null
        } else {
            status = HttpStatus.OK
            body = infoLabels
        }

        val response = ResponseEntity
                .status(status)
                .headers(headers)
                .body(body)

        return response
    }

    @GetMapping("info/post/{labelId}")
    fun getPostsForInfoLabel(@PathVariable labelId: Int): ResponseEntity<List<InfoPostDTO>> {
        val posts = infoService.getPostsForInfoLabel(labelId)

        lateinit var status: HttpStatus
        val headers = HttpHeaders()
        val body: List<InfoPostDTO>?

        if (posts.isNullOrEmpty()) {
            status = HttpStatus.NO_CONTENT
            body = null
        } else {
            status = HttpStatus.OK
            body = posts
        }

        val response = ResponseEntity
            .status(status)
            .headers(headers)
            .body(body)

        return response
    }
}