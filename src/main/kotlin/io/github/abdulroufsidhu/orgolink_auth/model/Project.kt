package io.github.abdulroufsidhu.orgolink_auth.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "projects")
class Project(
    @NotBlank(message = "Project name cannot be blank")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    var name: String? = null,
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(length = 500)
    var description: String? = null,
    @Column(nullable = false)
    var isActive: Boolean = true,
    @Column(nullable = false)
    var isPublic: Boolean = false,
    @NotBlank(message = "Project key cannot be blank")
    @Size(max = 50, message = "Project key cannot exceed 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    var projectKey: String? = null,
) : BaseTable()
