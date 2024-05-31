package com.jun.blogducinemaback.entity

import jakarta.persistence.*
import jakarta.persistence.metamodel.StaticMetamodel
import org.springframework.security.core.GrantedAuthority

@Entity
@StaticMetamodel(Authority::class)
class Authority(
    var authorityName: String = "ROLE_USER"
): GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var authorityId: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userData: UserData? = null

    override fun getAuthority(): String {
        return authorityName
    }
}