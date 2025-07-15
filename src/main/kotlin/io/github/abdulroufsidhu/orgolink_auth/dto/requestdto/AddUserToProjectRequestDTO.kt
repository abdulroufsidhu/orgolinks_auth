package io.github.abdulroufsidhu.orgolink_auth.dto.requestdto

import io.github.abdulroufsidhu.orgolink_auth.model.ProjectRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddUserToProjectRequestDTO(
        @field:NotBlank(message = "Username cannot be blank") val username: String?,
        @field:NotNull(message = "Role cannot be null") val role: ProjectRole?
)
