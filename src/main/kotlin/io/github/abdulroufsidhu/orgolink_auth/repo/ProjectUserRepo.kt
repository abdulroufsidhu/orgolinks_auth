package io.github.abdulroufsidhu.orgolink_auth.repo

import io.github.abdulroufsidhu.orgolink_auth.model.ProjectRole
import io.github.abdulroufsidhu.orgolink_auth.model.ProjectUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProjectUserRepo : JpaRepository<ProjectUser, UUID> {
    fun findByUserIdAndProjectId(userId: UUID, projectId: UUID): ProjectUser?

    fun findByUserIdAndIsActiveTrue(userId: UUID): List<ProjectUser>

    fun findByProjectIdAndIsActiveTrue(projectId: UUID): List<ProjectUser>

    fun findByProjectIdAndRoleAndIsActiveTrue(projectId: UUID, role: ProjectRole): List<ProjectUser>

    fun existsByUserIdAndProjectIdAndIsActiveTrue(userId: UUID, projectId: UUID): Boolean

    @Query(
        "SELECT pu FROM ProjectUser pu WHERE pu.userId = :userId AND pu.projectId = :projectId AND pu.isActive = true"
    )
    fun findActiveProjectUser(
        @Param("userId") userId: UUID,
        @Param("projectId") projectId: UUID
    ): ProjectUser?

    @Query(
        "SELECT pu FROM ProjectUser pu WHERE pu.projectId = :projectId AND pu.role IN :roles AND pu.isActive = true"
    )
    fun findByProjectIdAndRolesAndIsActiveTrue(
        @Param("projectId") projectId: UUID,
        @Param("roles") roles: List<ProjectRole>
    ): List<ProjectUser>

    @Query(
        "SELECT pu FROM ProjectUser pu WHERE pu.userId = :userId AND pu.role = :role AND pu.isActive = true"
    )
    fun findByUserIdAndRoleAndIsActiveTrue(
        @Param("userId") userId: UUID,
        @Param("role") role: ProjectRole
    ): List<ProjectUser>

    @Query(
        "SELECT COUNT(pu) FROM ProjectUser pu WHERE pu.projectId = :projectId AND pu.isActive = true"
    )
    fun countActiveUsersByProjectId(@Param("projectId") projectId: UUID): Long

    @Query(
        "SELECT COUNT(pu) FROM ProjectUser pu WHERE pu.projectId = :projectId AND pu.role = :role AND pu.isActive = true"
    )
    fun countActiveUsersByProjectIdAndRole(
        @Param("projectId") projectId: UUID,
        @Param("role") role: ProjectRole
    ): Long
}
