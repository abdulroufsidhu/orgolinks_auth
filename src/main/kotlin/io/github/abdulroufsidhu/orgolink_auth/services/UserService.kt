package io.github.abdulroufsidhu.orgolink_auth.services

import io.github.abdulroufsidhu.orgolink_auth.model.User
import io.github.abdulroufsidhu.orgolink_auth.repo.UserRepo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRep: UserRepo, private val passwordEncoder: BCryptPasswordEncoder) {

    fun createUser(user: User) = try { userRep.saveAndFlush(user.apply { password = passwordEncoder.encode(password) }) } catch (e: DataIntegrityViolationException) {
        ResponseEntity.badRequest().body(LinkedHashMap<String, String>().apply {
            put("message", "username already taken")
            put("data", e.message ?: "")
        })
    }

}