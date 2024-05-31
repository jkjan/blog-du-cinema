package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.UserData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<UserData, Int> {
    fun findByUsername(username: String): Optional<UserData>
}