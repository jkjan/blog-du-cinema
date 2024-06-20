package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.adapter.out.persistence.PostRepository
import com.jun.blogducinemaback.application.dto.ForumPostListDTO
import com.jun.blogducinemaback.domain.Post
import com.jun.blogducinemaback.global.types.Default
import org.springframework.data.domain.*
import org.springframework.stereotype.Service

@Service
class ForumService(private val postRepository: PostRepository) {
    fun getForumPostList(lastPostId: Int): ForumPostListDTO {
        lateinit var postPage: Page<Post>
        val page = PageRequest.of(0, Default.PAGE_SIZE)

        if (lastPostId == 0)
            postPage = postRepository.findAll(page)
        else {
            postPage = postRepository.findPostsByPostIdLessThanOrderByPostIdDesc(lastPostId, page)
        }

        return ForumPostListDTO(postPage)
    }
}