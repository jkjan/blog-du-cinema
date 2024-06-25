package com.jun.blogducinemaback.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface BaseRepository<T, ID, NID> : JpaRepository<T, ID> {
    fun nnnnn(nid: NID): Optional<T & Any>
}
