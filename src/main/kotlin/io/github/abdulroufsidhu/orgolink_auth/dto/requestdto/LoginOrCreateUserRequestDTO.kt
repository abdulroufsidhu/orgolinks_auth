package io.github.abdulroufsidhu.orgolink_auth.dto.requestdto

import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUser
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class LoginOrCreateUserRequestDTO(
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(max = 100, message = "Username cannot exceed 100 characters")
    val username: String?,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$",
        message = "Password must be minimum eight characters, at least one letter, one number and one special character"
    )
    val password: String?
) {
    fun asOrgoOrgoUser(): OrgoUser = OrgoUser(
        username = username,
        password = password
    )
}