package io.github.abdulroufsidhu.orgolink_auth.repo

import io.github.abdulroufsidhu.orgolink_auth.model.UserAccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TokenRepo : JpaRepository<UserAccessToken, UUID> {
    fun findByToken(token: String): UserAccessToken?
    fun findByUserIdAndIsRevokedFalse(userId: UUID): List<UserAccessToken>
    fun existsByTokenAndIsRevokedFalse(token: String): Boolean
}