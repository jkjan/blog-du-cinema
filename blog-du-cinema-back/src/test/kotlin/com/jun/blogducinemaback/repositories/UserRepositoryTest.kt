package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.basetest.CustomDataJpaTest
import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import com.jun.blogducinemaback.domain.UserData
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.Session
import org.hibernate.stat.Statistics
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@CustomDataJpaTest
class UserRepositoryTest(@Autowired val userRepository: UserRepository) {
    @Autowired
    private lateinit var entityManager: EntityManager
    private lateinit var statistics: Statistics

    @BeforeEach
    fun setUp() {
        val session: Session = entityManager.unwrap(Session::class.java)
        statistics = session.sessionFactory.statistics
        statistics.clear()
    }

    @Test
    fun `natural id 테스트`() {
        val user = UserData("user1")
        userRepository.save(user)
        statistics.clear()

        repeat(100) {
            userRepository.findByNaturalId("user1")
        }
        val naturalIdCount = statistics.prepareStatementCount

        assertThat(naturalIdCount).isEqualTo(0)
    }

    @Test
    fun `id 테스트`() {
        val user = UserData("user1")
        userRepository.save(user)
        statistics.clear()

        repeat(100) {
            userRepository.findById(1)
        }
        val idCount = statistics.prepareStatementCount

        assertThat(idCount).isEqualTo(0)
    }

    @Test
    fun `natural id 미적용 테스트`() {
        val user = UserData("user1")
        userRepository.save(user)
        statistics.clear()

        repeat(100) {
            userRepository.findByUsername("user1")
        }

        val naturalIdCount = statistics.prepareStatementCount

        assertThat(naturalIdCount).isEqualTo(100)
    }
}