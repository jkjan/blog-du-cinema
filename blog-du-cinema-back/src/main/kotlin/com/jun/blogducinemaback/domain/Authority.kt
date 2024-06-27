package com.jun.blogducinemaback.domain

import com.jun.blogducinemaback.global.types.Role
import jakarta.persistence.*
import jakarta.persistence.metamodel.StaticMetamodel
import org.springframework.security.core.GrantedAuthority

@Entity
@StaticMetamodel(Authority::class)
class Authority(
    final var authorityName: String = "ROLE_USER"
): GrantedAuthority{
    constructor(role: Role) : this() {
        this.authorityName = role.value
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var authorityId: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userData: UserData? = null

    override fun getAuthority(): String {
        return authorityName
    }

    override fun toString(): String {
        return authorityName
    }
}