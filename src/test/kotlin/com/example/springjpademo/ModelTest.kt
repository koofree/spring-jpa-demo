package com.example.springjpademo

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ModelTest {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userService: UserService

    @Test
    fun test() {
        userRepository.save(User(name = "Koo", description = "Hello World", profile = Profile("123", "dfgas")))
        userRepository.save(User(name = "Lee", description = "No", profile = Profile("010", "asdf")))

        val user: User = userRepository.findByName("Koo")
        val user2: User = userRepository.findByName("Lee")

        assertEquals("Koo", user.name)
        assertEquals(2, user2.id)
    }


    @Test
    fun test2() {
        var user = User(name = "Koo", description = "ddd", profile = Profile("234", "234"))
        user = userRepository.save(user)

        user.socialInfos.add(SocialInfo(name = "facebook"))
        user.answeredHistories.add(AnsweredHistory(123))
        userRepository.save(user)

        val savedUser = userRepository.findByName("Koo")
        assertEquals("facebook", savedUser.socialInfos.first().name)

    }

    @Test
    fun test3() {
        var user = User(name = "Koo", description = "ddd", profile = Profile("234", "234"))
        user = userRepository.save(user)

        user.socialInfos.add(SocialInfo(name = "facebook"))
        user.answeredHistories.add(AnsweredHistory(123))
        userRepository.save(user)

        val savedUser = userService.user("Koo")
        assertEquals(123, savedUser.answeredHistories.first().id)

        val pageable: Pageable = PageRequest.of(1, 10, Sort.Direction.DESC, "name")
        val users = userRepository.findAll(pageable)
        assertEquals(1, users.totalElements)
    }
}
