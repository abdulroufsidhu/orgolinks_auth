package io.github.abdulroufsidhu.orgolink_auth.configs

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Orgolink Auth API",
        version = "1.0",
        description = "Authentication and Authorization Service for Orgolink"
    ),
    servers = [
        Server(url = "/", description = "Default Server URL")
    ],
    security = [SecurityRequirement(name = "bearerAuth")]
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth token",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
class OpenAPIConfig