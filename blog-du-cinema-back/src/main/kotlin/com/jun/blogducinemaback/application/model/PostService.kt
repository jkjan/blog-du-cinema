package com.jun.blogducinemaback.application.model

import com.jun.blogducinemaback.adapter.out.persistence.PostRepository
import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import com.jun.blogducinemaback.application.dto.request.PostRequestDTO
import com.jun.blogducinemaback.application.dto.response.PostListDTO
import com.jun.blogducinemaback.application.dto.response.PostResponseDTO
import com.jun.blogducinemaback.domain.Authority
import com.jun.blogducinemaback.domain.Label
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
class PostService(
    private val postRepository: PostRepository,
    private val roleHierarchy: RoleHierarchy,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createNewPost(postRequestDTO: PostRequestDTO): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val isAuthenticatedUser = authentication.principal == postRequestDTO.username
        val userData = userRepository.findByNaturalId(postRequestDTO.username)

        if (isAuthenticatedUser and userData.isPresent) {
            lateinit var labels: List<Label>

            val post = with(postRequestDTO) {
                labels = labelIds.map { id ->
                    Label(labelId = id)
                }

                Post(
                    title = title,
                    contentHtml = contentHtml,
                )
            }

            post.addLabels(labels)
            userData.get().writePost(post)

            return true
        }

        return false
    }

    fun getAllForumPostList(lastPostId: Int): Optional<PostListDTO> {
        val page = PageRequest.of(0, Default.PAGE_SIZE)

        return if (lastPostId > 0)
            Optional.empty()
        else {
            val pageResult: Page<Post> =
                if (lastPostId == 0)
                    postRepository.findAll(page)
                else
                    postRepository.findPostsByPostIdLessThanOrderByPostIdDesc(lastPostId, page)
            Optional.of(PostListDTO(pageResult))
        }
    }

    fun getPostListForLabel(labelIds: List<Int>, lastPostId: Int): Optional<PostListDTO> {
        val page = PageRequest.of(0, Default.PAGE_SIZE)

        return if (lastPostId > 0)
            Optional.empty()
        else {
            val pageResult: Page<Post> =
                if (lastPostId == 0)
                    postRepository.findAll(page)
                else
                    postRepository.findPostsByPostIdLessThanOrderByPostIdDesc(lastPostId, page)
            Optional.of(PostListDTO(pageResult))
        }
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
        } else {
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

    fun getPost(postId: Int): Optional<PostResponseDTO> {
        val post = postRepository.findById(postId)

        return if (post.isPresent) {
            with(post.get()) {
                Optional.of(
                    PostResponseDTO(
                        title = title,
                        contentHtml = contentHtml,
                        createdAt = createdAt,
                        modifiedAt = modifiedAt,
                    )
                )
            }
        } else {
            Optional.empty()
        }
    }
}