package io.github.abdulroufsidhu.orgolink_auth.model

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "users")
class User(
    @Column(unique = true)
    var username: String? = null,
    var password: String? = null,
    var salt: String? = null,
) : BaseTable()

