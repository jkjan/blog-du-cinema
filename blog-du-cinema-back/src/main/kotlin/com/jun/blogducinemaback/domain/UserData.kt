package com.jun.blogducinemaback.domain

import com.jun.blogducinemaback.global.types.Default
import com.jun.blogducinemaback.global.types.Role
import jakarta.persistence.*
import org.hibernate.annotations.NaturalId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class UserData(
    @NaturalId
    private var username: String = "unknown",
    private var password: String = "unknown",
) : BaseTimeEntity(), UserDetails {
    constructor(userId: Int, username: String) : this() {
        this.userId = userId
        this.username = username
    }

    constructor(username: String, password: String, authority: String) : this() {
        this.username = username
        this.password = password
        this.authorities = mutableListOf(Authority(authority))
    }

    constructor(username: String, password: String, authority: Role) : this() {
        this.username = username
        this.password = password
        this.authorities = mutableListOf(Authority(authority.value))
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Int = 0

    var nickname: String = Default.NICKNAME

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
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
