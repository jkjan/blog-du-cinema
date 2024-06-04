package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.Authority
import com.jun.blogducinemaback.entity.UserData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase
class UserRepositoryTest(@Autowired val userRepository: UserRepository) {
    @BeforeEach
    fun addTestUser() {
        val testUser = UserData("test", "testpw")
        val testAuthority = Authority("ROLE_USER")
        testUser.addAuthority(testAuthority)

        userRepository.save(testUser)
    }

    @Test
    fun defaultUserRoleTest() {
        // when
        val user = userRepository.findByUsername("test")

        // then
        assertThat(user).isNotNull
        assertEquals(user.get().authorities[0].authorityName, "ROLE_USER")
        assertEquals(user.get().authorities[0].authorityId, 1)
        assertEquals(user.get().userId, 1)
    }
}