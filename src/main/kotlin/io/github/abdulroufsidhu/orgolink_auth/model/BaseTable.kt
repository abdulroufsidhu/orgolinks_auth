package io.github.abdulroufsidhu.orgolink_auth.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date
import java.util.UUID

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTable (
    @Column @Id() @GeneratedValue(strategy = GenerationType.UUID) open var id: UUID? = null,
    @Column(name = "created_at", nullable = false, updatable = false,) @CreatedDate() open var created_at: Date? = Date(),
    @Column(name = "updated_at") @LastModifiedDate() open var updated_at: Date? = Date(),
    @Column(name = "created_by") @CreatedBy open var created_by: UUID? = null,
    @Column(name = "updated_by") @LastModifiedBy open var updated_by: UUID? = null,
    )