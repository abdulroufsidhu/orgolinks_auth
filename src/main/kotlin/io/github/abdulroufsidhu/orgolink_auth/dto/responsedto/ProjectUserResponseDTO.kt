package io.github.abdulroufsidhu.orgolink_auth.dto.responsedto

import io.github.abdulroufsidhu.orgolink_auth.model.Project
import io.github.abdulroufsidhu.orgolink_auth.model.ProjectRole
import io.github.abdulroufsidhu.orgolink_auth.model.ProjectUser
import java.util.Date
import java.util.UUID

data class ProjectUserResponseDTO(
        val id: UUID?,
        val userId: UUID?,
        val projectId: UUID?,
        val role: ProjectRole,
        val isActive: Boolean,
        val username: String?,
        val projectName: String?,
        val projectKey: String?,
        val createdAt: Date?,
        val updatedAt: Date?
) {
  companion object {
    fun from(projectUser: ProjectUser, project: Project?, username: String?): ProjectUserResponseDTO {
      return ProjectUserResponseDTO(
              id = projectUser.id,
              userId = projectUser.userId,
              projectId = projectUser.projectId,
              role = projectUser.role,
              isActive = projectUser.isActive,
              username = username,
              projectName = project?.name,
              projectKey = project?.projectKey,
              createdAt = projectUser.created_at,
              updatedAt = projectUser.updated_at
      )
    }
  }
}
