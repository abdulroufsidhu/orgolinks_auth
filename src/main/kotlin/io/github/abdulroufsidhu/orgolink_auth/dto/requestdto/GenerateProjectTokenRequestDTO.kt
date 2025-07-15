package io.github.abdulroufsidhu.orgolink_auth.dto.requestdto

import io.github.abdulroufsidhu.orgolink_auth.model.ProjectRole
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class GenerateProjectTokenRequestDTO(
        @field:NotNull(message = "Role cannot be null") val role: ProjectRole?,
        @field:Size(max = 255, message = "Description cannot exceed 255 characters")
        val description: String? = null,
        val expirationDays: Long? = 30 // Default 30 days
)
