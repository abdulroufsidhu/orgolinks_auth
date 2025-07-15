package io.github.abdulroufsidhu.orgolink_auth.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "project_users")
class ProjectUser(
    @Column(name = "user_id", nullable = false) var userId: UUID? = null,
    @Column(name = "project_id", nullable = false) var projectId: UUID? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: ProjectRole = ProjectRole.USER,
    @Column(name = "is_active", nullable = false) var isActive: Boolean = true
) : BaseTable()
