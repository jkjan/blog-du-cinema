package com.jun.blogducinemaback.controllers

import com.jun.blogducinemaback.dto.InfoLabelDTO
import com.jun.blogducinemaback.dto.InfoPostDTO
import com.jun.blogducinemaback.services.LabelService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class LabelController(val labelService: LabelService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/label/info")
    fun getInfoLabels(@RequestHeader("Origin") origin: String?): ResponseEntity<List<InfoLabelDTO>> {
        val labelService = labelService.getInfoLabels()

        logger.info("request origin: $origin")
        logger.info("All labels requested for info.")

        lateinit var status: HttpStatus
        val headers = HttpHeaders()
        val body: List<InfoLabelDTO>?

        if (labelService.isNullOrEmpty()) {
            logger.info("No labels in Label table or failed to retrieve from db.")
            status = HttpStatus.NO_CONTENT
            body = null
        } else {
            logger.info("Found ${labelService.size} labels in Label table.")
            status = HttpStatus.OK
            body = labelService
        }

        val response = ResponseEntity
                .status(status)
                .headers(headers)
                .body(body)

        return response
    }

    @GetMapping("/label/{labelId}/post")
    fun getPostsForLabel(@PathVariable labelId: Int): ResponseEntity<List<InfoPostDTO>> {
        val posts = labelService.getPostsForLabel(labelId)

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