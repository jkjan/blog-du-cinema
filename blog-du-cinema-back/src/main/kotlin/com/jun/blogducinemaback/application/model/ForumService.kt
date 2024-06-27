package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.adapter.out.persistence.PostRepository
import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import com.jun.blogducinemaback.application.dto.ForumPostDTO
import com.jun.blogducinemaback.application.dto.ForumPostListDTO
import com.jun.blogducinemaback.domain.Authority
import com.jun.blogducinemaback.domain.Post
import com.jun.blogducinemaback.global.types.Default
import com.jun.blogducinemaback.global.types.Role
import jakarta.transaction.Transactional
import org.springframework.data.domain.*
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ForumService(
    private val postRepository: PostRepository,
    private val roleHierarchy: RoleHierarchy,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createNewPost(forumPostDTO: ForumPostDTO): Boolean {
        val userData = userRepository.findByNaturalId(forumPostDTO.username)

        if (userData.isPresent) {
            val post = forumPostDTO.toPost()
            userData.get().writePost(post)
            return true
        }

        return false
    }

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

    @Transactional
    fun deletePost(postId: Int): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val nowUserAuthorities = authentication.authorities
        val grantedAuthorities = roleHierarchy.getReachableGrantedAuthorities(nowUserAuthorities)
        val isAtLeastAdmin = grantedAuthorities.contains(Authority(Role.ADMIN))

        var delete = false
        if (isAtLeastAdmin) {
            delete = true
        }
        else {
            val deletingPost = postRepository.findById(postId)

            if (deletingPost.isPresent) {
                val usernameOfPost = deletingPost.get().userData.username
                val nowUsername = authentication.principal

                if (usernameOfPost == nowUsername) {
                    delete = true
                }
            }
        }

        if (delete) {
            postRepository.deleteById(postId)
        }

        return delete
    }

    fun getPost(postId: Int): Optional<ForumPostDTO> {
        val post = postRepository.findById(postId)
        if (post.isPresent) {
            return Optional.of(ForumPostDTO(post.get()))
        }
        else {
            return Optional.empty()
        }
    }
}