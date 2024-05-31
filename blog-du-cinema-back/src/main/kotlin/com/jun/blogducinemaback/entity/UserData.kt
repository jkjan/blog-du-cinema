package com.jun.blogducinemaback.entity

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import jakarta.persistence.*
import org.hibernate.annotations.NaturalId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class UserData(
    @NaturalId
    private val username: String = "unknown",
    private val password: String = "unknown",
    ) : BaseTimeEntity(), UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Int = 1

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "userData", orphanRemoval = true)
    var posts: MutableList<Post> = mutableListOf()

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "userData", orphanRemoval = true)
    var authorities: MutableList<Authority> = mutableListOf()

    fun addAuthority(authority: Authority) {
        this.authorities.add(authority)
        authority.userData = this
    }

    fun writePost(post: Post) {
        posts.add(post)
        post.userData = this
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // TODO("Not yet implemented")
        return authorities
    }

    override fun getPassword(): String {
        // TODO("Not yet implemented")
        return password
    }

    override fun getUsername(): String {
        // TODO("Not yet implemented")
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        // TODO("Not yet implemented")
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        // TODO("Not yet implemented")
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        // TODO("Not yet implemented")
        return true
    }

    override fun isEnabled(): Boolean {
        // TODO("Not yet implemented")
        return true
    }
}
