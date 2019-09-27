package com.example.springjpademo

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(val userRepository: UserRepository) {
    @Transactional
    fun user(name: String): User {
        val user = this.userRepository.findByName(name)
        user.answeredHistories.first().id
        return user
    }
}
