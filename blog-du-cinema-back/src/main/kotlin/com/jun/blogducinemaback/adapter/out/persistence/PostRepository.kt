package com.jun.blogducinemaback.adapter.out.persistence

import com.jun.blogducinemaback.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Int> {
    fun findPostsByPostIdLessThanOrderByPostIdDesc(lastPostId: Int, page: Pageable): Page<Post>
}