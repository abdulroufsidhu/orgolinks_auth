package io.github.abdulroufsidhu.orgolink_auth.services

import io.github.abdulroufsidhu.orgolink_auth.dto.requestdto.CreateUserRequestDTO
import io.github.abdulroufsidhu.orgolink_auth.exceptions.UsernameAlreadyExists
import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUser
import io.github.abdulroufsidhu.orgolink_auth.repo.UserRepo
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRep: UserRepo,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun createUser(user: CreateUserRequestDTO): OrgoUser {
        if (userRep.existsByUsername(user.username)) throw UsernameAlreadyExists("Username already exists")
        return userRep.saveAndFlush(user.asOrgoOrgoUser().apply {
            password = passwordEncoder.encode(password)
        })
    }

}