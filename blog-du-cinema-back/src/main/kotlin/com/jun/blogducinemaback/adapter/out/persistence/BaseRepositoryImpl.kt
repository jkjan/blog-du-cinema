package com.jun.blogducinemaback.adapter.out.persistence

import jakarta.persistence.EntityManager
import org.hibernate.Session
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import java.io.Serializable
import java.util.*

class BaseRepositoryImpl<T, ID :Serializable, NID : Serializable>(
    entityInformation: JpaEntityInformation<T, *>,
    private val entityManager: EntityManager,
) : SimpleJpaRepository<T, ID>(entityInformation, entityManager), BaseRepository<T, ID, NID> {
    override fun findByNaturalId(nid: NID): Optional<T & Any> {
        val entity = entityManager.unwrap(Session::class.java).bySimpleNaturalId(this.domainClass).load(nid)
        return if (entity != null)
            Optional.of(entity)
        else {
            Optional.empty()
        }
    }
}
