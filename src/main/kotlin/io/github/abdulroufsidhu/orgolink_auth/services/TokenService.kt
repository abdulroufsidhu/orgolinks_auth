package io.github.abdulroufsidhu.orgolink_auth.services

import io.github.abdulroufsidhu.orgolink_auth.model.OrgoUserPrincipal
import io.github.abdulroufsidhu.orgolink_auth.model.UserAccessToken
import io.github.abdulroufsidhu.orgolink_auth.repo.TokenRepo
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.apache.commons.lang3.time.DateUtils
import org.hibernate.type.descriptor.DateTimeUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Calendar
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit

@Service
class TokenService(
    private val tokenRepo: TokenRepo,
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val jwtExpiration: Long,
) {

    fun generateToken(userDetails: UserDetails): String {
        val now = Date()
        val expirationDate = Date(now.time + jwtExpiration)

        val token =
            Jwts.builder()
                .setSubject(userDetails.username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact()

        if (userDetails is OrgoUserPrincipal) {
            userDetails.id?.let { userId ->
                tokenRepo.save(
                    UserAccessToken(
                        token = token,
                        expiresAt = expirationDate,
                        userId = userId,
                    )
                )
            }
        }

        return token
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username &&
                !isTokenExpired(token) &&
                tokenRepo.existsByTokenAndIsRevokedFalse(token)
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun revokeAllUserTokens(userId: UUID) {
        val validUserTokens = tokenRepo.findByUserIdAndIsRevokedFalse(userId)
        validUserTokens.forEach { token ->
            token.isRevoked = true
            tokenRepo.save(token)
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).body
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
