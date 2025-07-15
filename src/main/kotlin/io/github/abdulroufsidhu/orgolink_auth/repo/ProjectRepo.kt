package io.github.abdulroufsidhu.orgolink_auth.repo

import io.github.abdulroufsidhu.orgolink_auth.model.Project
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepo : JpaRepository<Project, UUID> {
  fun findByProjectKey(projectKey: String): Project?

  fun existsByProjectKey(projectKey: String): Boolean

  fun findByIsActiveTrue(): List<Project>

  fun findByIsPublicTrueAndIsActiveTrue(): List<Project>

  @Query("SELECT p FROM Project p WHERE p.name LIKE %:name% AND p.isActive = true")
  fun findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") name: String): List<Project>

  @Query(
          "SELECT p FROM Project p JOIN ProjectUser pu ON p.id = pu.projectId WHERE pu.userId = :userId AND p.isActive = true AND pu.isActive = true"
  )
  fun findProjectsByUserId(@Param("userId") userId: UUID): List<Project>

  @Query("SELECT p FROM Project p WHERE p.created_by = :userId AND p.isActive = true")
  fun findProjectsByCreatedBy(@Param("userId") userId: UUID): List<Project>
}
