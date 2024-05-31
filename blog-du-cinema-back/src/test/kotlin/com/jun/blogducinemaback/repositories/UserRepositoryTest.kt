package com.jun.blogducinemaback.repositories

import com.jun.blogducinemaback.entity.Authority
import com.jun.blogducinemaback.entity.UserData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase
class UserRepositoryTest(@Autowired val userRepository: UserRepository) {
    @Test
    fun defaultUserRoleTest() {
        val testUser = UserData("test", "testpw")
        val testAuthority = Authority("ROLE_USER")

        testUser.addAuthority(testAuthority)

        val user: UserData = userRepository.save(testUser)

        assertEquals(user.authorities[0].authorityName, "ROLE_USER")
        assertEquals(user.authorities[0].authorityId, 1)
        assertEquals(user.userId, 1)
    }
}