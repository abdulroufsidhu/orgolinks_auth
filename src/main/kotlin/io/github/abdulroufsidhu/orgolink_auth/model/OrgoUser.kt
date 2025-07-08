package io.github.abdulroufsidhu.orgolink_auth.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.validation.constraints.NotBlank

@Entity(name = "users")
class OrgoUser(
    @NotBlank
    @Column(unique = true)
    var username: String? = null,
    @NotBlank
    var password: String? = null,
) : BaseTable()

