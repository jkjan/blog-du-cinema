package com.jun.blogducinemaback.adapter.`in`.api

import com.jun.blogducinemaback.global.utils.logger
import com.jun.blogducinemaback.application.dto.InfoLabelDTO
import com.jun.blogducinemaback.application.dto.InfoPostDTO
import com.jun.blogducinemaback.application.model.InfoService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class InfoController(val infoService: InfoService) {
    private val logger = logger()

    @GetMapping("/info/label")
    fun getInfoLabels(@RequestHeader("Origin") origin: String?): ResponseEntity<List<InfoLabelDTO>> {
        val labelService = infoService.getInfoLabels()

        logger.info("request origin: $origin")
        logger.info("All labels requested for info.")

        lateinit var status: HttpStatus
        val headers = HttpHeaders()
        val body: List<InfoLabelDTO>?

        if (labelService.isEmpty()) {
            logger.info("No labels in Label table or failed to retrieve from db.")
            status = HttpStatus.NO_CONTENT
            body = emptyList()
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

    @PostMapping("/info/post")
    @PreAuthorize("hasRole('ADMIN')")
    fun createNewInfoPost(
        @RequestBody
        post: InfoPostDTO
    ): ResponseEntity<HashMap<String, String>> {
        val body: HashMap<String, String> = hashMapOf()
        body["message"] = "성공"

        return ResponseEntity.status(HttpStatus.CREATED).body(body)
    }

    @GetMapping("/info/post/{labelId}")
    fun getPostsForLabel(@PathVariable labelId: Int): ResponseEntity<List<InfoPostDTO>> {
        val posts = infoService.getPostsForLabel(labelId)
        logger.info("Posts: $posts")

        lateinit var status: HttpStatus
        val headers = HttpHeaders()
        lateinit var body: List<InfoPostDTO>

        if (posts.isEmpty()) {
            status = HttpStatus.NO_CONTENT
            body = emptyList()
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