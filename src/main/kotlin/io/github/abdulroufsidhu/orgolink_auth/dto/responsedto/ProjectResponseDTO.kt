package io.github.abdulroufsidhu.orgolink_auth.dto.responsedto

import io.github.abdulroufsidhu.orgolink_auth.model.Project
import io.github.abdulroufsidhu.orgolink_auth.model.ProjectRole
import java.util.Date
import java.util.UUID

data class ProjectResponseDTO(
        val id: UUID?,
        val name: String?,
        val description: String?,
        val projectKey: String?,
        val isActive: Boolean,
        val isPublic: Boolean,
        val createdAt: Date?,
        val updatedAt: Date?,
        val userRole: ProjectRole? = null
) {
  companion object {
    fun from(project: Project, userRole: ProjectRole? = null): ProjectResponseDTO {
      return ProjectResponseDTO(
              id = project.id,
              name = project.name,
              description = project.description,
              projectKey = project.projectKey,
              isActive = project.isActive,
              isPublic = project.isPublic,
              createdAt = project.created_at,
              updatedAt = project.updated_at,
              userRole = userRole
      )
    }
  }
}
