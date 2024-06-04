package com.jun.blogducinemaback.services
import com.jun.blogducinemaback.dto.UserSignInDTO
import com.jun.blogducinemaback.dto.UserSignUpDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var pbkdf2PasswordEncoder: Pbkdf2PasswordEncoder

    @Test
    fun signUpTest() {
        // given
        val userSignUpDTO = UserSignUpDTO("test", "testPW")
        userService.signUp(userSignUpDTO)

        // when
        val user = userService.loadUserByUsername("test")
        val encryptedPW = pbkdf2PasswordEncoder.encode("testPW")

        // then
        assertThat(user.username).isEqualTo("test")
        assertThat(user.password).isEqualTo(encryptedPW)
    }
}