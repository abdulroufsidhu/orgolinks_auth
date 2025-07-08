package io.github.abdulroufsidhu.orgolink_auth.services

import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUser
import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUserPrincipal
import io.github.abdulroufsidhu.orgolink_auth.repo.UserRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class OrgoUserDetailsService(private val userRepo: UserRepo): UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails{
        val orgoUser: OrgoUser? = userRepo.findByUsername(username)
        if (orgoUser == null) {
            println("user not found")
            throw UsernameNotFoundException("No User found holding username $username")
        }
        println("loaded user id is ${orgoUser.id?.toString()}")
        return OrgoUserPrincipal(orgoUser)
    }
}