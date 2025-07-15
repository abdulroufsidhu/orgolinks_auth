package io.github.abdulroufsidhu.orgolink_auth.dto.requestdto

import io.github.abdulroufsidhu.orgolink_auth.model.Project
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CreateProjectRequestDTO(
        @field:NotBlank(message = "Project name cannot be blank")
        @field:Size(max = 100, message = "Project name cannot exceed 100 characters")
        val name: String?,
        @field:Size(max = 500, message = "Description cannot exceed 500 characters")
        val description: String? = null,
        @field:NotBlank(message = "Project key cannot be blank")
        @field:Size(min = 3, max = 50, message = "Project key must be between 3 and 50 characters")
        @field:Pattern(
                regexp = "^[a-zA-Z0-9_-]+$",
                message =
                        "Project key can only contain alphanumeric characters, hyphens, and underscores"
        )
        val projectKey: String?,
        val isPublic: Boolean = false
) {
  fun toProject(): Project =
          Project(
                  name = name,
                  description = description,
                  projectKey = projectKey,
                  isPublic = isPublic,
                  isActive = true
          )
}
