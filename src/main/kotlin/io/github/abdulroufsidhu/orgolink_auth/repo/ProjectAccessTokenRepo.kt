package io.github.abdulroufsidhu.orgolink_auth.repo

import io.github.abdulroufsidhu.orgolink_auth.model.ProjectAccessToken
import io.github.abdulroufsidhu.orgolink_auth.model.ProjectRole
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProjectAccessTokenRepo : JpaRepository<ProjectAccessToken, UUID> {
  fun findByToken(token: String): ProjectAccessToken?

  fun findByProjectIdAndIsRevokedFalse(projectId: UUID): List<ProjectAccessToken>

  fun findByUserIdAndIsRevokedFalse(userId: UUID): List<ProjectAccessToken>

  fun findByUserIdAndProjectIdAndIsRevokedFalse(
          userId: UUID,
          projectId: UUID
  ): List<ProjectAccessToken>

  fun existsByTokenAndIsRevokedFalse(token: String): Boolean

  @Query(
          "SELECT pat FROM ProjectAccessToken pat WHERE pat.token = :token AND pat.isRevoked = false AND pat.expiresAt > CURRENT_TIMESTAMP"
  )
  fun findValidToken(@Param("token") token: String): ProjectAccessToken?

  @Query(
          "SELECT pat FROM ProjectAccessToken pat WHERE pat.projectId = :projectId AND pat.isRevoked = false AND pat.expiresAt > CURRENT_TIMESTAMP"
  )
  fun findValidTokensByProjectId(@Param("projectId") projectId: UUID): List<ProjectAccessToken>

  @Query(
          "SELECT pat FROM ProjectAccessToken pat WHERE pat.userId = :userId AND pat.isRevoked = false AND pat.expiresAt > CURRENT_TIMESTAMP"
  )
  fun findValidTokensByUserId(@Param("userId") userId: UUID): List<ProjectAccessToken>

  @Query(
          "SELECT pat FROM ProjectAccessToken pat WHERE pat.projectId = :projectId AND pat.role = :role AND pat.isRevoked = false AND pat.expiresAt > CURRENT_TIMESTAMP"
  )
  fun findValidTokensByProjectIdAndRole(
          @Param("projectId") projectId: UUID,
          @Param("role") role: ProjectRole
  ): List<ProjectAccessToken>

  @Query(
          "SELECT COUNT(pat) FROM ProjectAccessToken pat WHERE pat.projectId = :projectId AND pat.isRevoked = false AND pat.expiresAt > CURRENT_TIMESTAMP"
  )
  fun countValidTokensByProjectId(@Param("projectId") projectId: UUID): Long

  @Query(
          "SELECT COUNT(pat) FROM ProjectAccessToken pat WHERE pat.userId = :userId AND pat.isRevoked = false AND pat.expiresAt > CURRENT_TIMESTAMP"
  )
  fun countValidTokensByUserId(@Param("userId") userId: UUID): Long
}
