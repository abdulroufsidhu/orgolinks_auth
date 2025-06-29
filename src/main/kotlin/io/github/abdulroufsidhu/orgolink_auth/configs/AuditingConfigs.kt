package io.github.abdulroufsidhu.orgolink_auth.configs

import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUserPrincipal
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
class AuditorAwareImpl : AuditorAware<UUID> {
    override fun getCurrentAuditor(): Optional<UUID> {
        val auth = SecurityContextHolder.getContext().authentication

        if (auth == null || !auth.isAuthenticated || auth.principal == "anonymousUser") {
            println("AuditorAware: No authenticated user")
            return Optional.empty()
        }

        val userPrincipal = auth.principal as OrgoUserPrincipal
        println("AuditorAware: Found user ID = ${userPrincipal.id}")
        return userPrincipal.id?.let { Optional.of(it) } ?: Optional.empty()
    }
}
