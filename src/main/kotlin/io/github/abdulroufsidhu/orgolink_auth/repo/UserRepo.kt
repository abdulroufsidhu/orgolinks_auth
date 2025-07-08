package io.github.abdulroufsidhu.orgolink_auth.repo

import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepo: JpaRepository<OrgoUser, UUID> {
    fun findByUsername(username: String?) : OrgoUser?

    fun existsByUsername(username: String?) : Boolean

    
}