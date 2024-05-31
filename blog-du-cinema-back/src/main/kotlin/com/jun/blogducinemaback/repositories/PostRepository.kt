package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Int> {
}