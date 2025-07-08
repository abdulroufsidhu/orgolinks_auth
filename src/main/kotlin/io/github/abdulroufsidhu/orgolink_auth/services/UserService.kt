package io.github.abdulroufsidhu.orgolink_auth.services

import io.github.abdulroufsidhu.orgolink_auth.dto.ValidResponseData
import io.github.abdulroufsidhu.orgolink_auth.dto.requestdto.LoginOrCreateUserRequestDTO
import io.github.abdulroufsidhu.orgolink_auth.exceptions.UsernameAlreadyExists
import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUser
import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUserPrincipal
import io.github.abdulroufsidhu.orgolink_auth.repo.UserRepo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRep: UserRepo,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService,
    private val userDetailsService: OrgoUserDetailsService,
) {

    fun createUser(user: LoginOrCreateUserRequestDTO): ResponseEntity<ValidResponseData<String>> {
        if (userRep.existsByUsername(user.username)) throw UsernameAlreadyExists("Username already exists")
        val incommingPassword = user.password
        val savedUser = userRep.saveAndFlush(user.asOrgoOrgoUser().apply {
            password = passwordEncoder.encode(password)
        })
        return login(LoginOrCreateUserRequestDTO(user.username, incommingPassword))
    }

    fun login(
        requeestDto: LoginOrCreateUserRequestDTO
    ): ResponseEntity<ValidResponseData<String>> {
        val authentication: Authentication =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(requeestDto.username, requeestDto.password)
            )

        val userDetails = userDetailsService.loadUserByUsername(requeestDto.username)
        val token = tokenService.generateToken(userDetails)

        return ResponseEntity.ok(
            ValidResponseData(message = "Login successful", data = token)
        )
    }

    fun logout(request: HttpServletRequest): ResponseEntity<ValidResponseData<Nothing>> {
        val authHeader = request.getHeader("Authorization")
        val jwt = authHeader?.substring(7)

        jwt?.let {
            val username = tokenService.extractUsername(it)
            val userDetails = userDetailsService.loadUserByUsername(username)
            (userDetails as? OrgoUserPrincipal)?.id?.let { userId: UUID ->
                tokenService.revokeAllUserTokens(userId)
            }
        }

        return ResponseEntity.ok(
            ValidResponseData(
                message = "Logged out successfully",
                data = null
            )
        )
    }
}