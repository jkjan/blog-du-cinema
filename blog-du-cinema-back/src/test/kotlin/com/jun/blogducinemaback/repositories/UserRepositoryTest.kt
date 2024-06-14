package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.adapter.out.persistence.UserRepository
import com.jun.blogducinemaback.domain.UserData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest(@Autowired val userRepository: UserRepository) {
    @BeforeEach
    fun addTestUser() {
        val testUser = UserData("test", "testpw")
        userRepository.save(testUser)
    }

    @Test
    fun defaultUserRoleTest() {
        // when
        val user = userRepository.findByUsername("test")

        // then
        assertThat(user).isNotNull
        assertEquals(user.get().userId, 1)
    }
}