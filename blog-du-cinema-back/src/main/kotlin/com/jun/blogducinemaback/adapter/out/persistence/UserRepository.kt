package com.jun.blogducinemaback.adapter.out.persistence

import com.jun.blogducinemaback.domain.UserData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: BaseRepository<UserData, Int, String> {
    fun findByUsername(username: String): Optional<UserData>
}