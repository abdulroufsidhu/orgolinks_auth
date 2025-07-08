package io.github.abdulroufsidhu.orgolink_auth.configs

import io.github.abdulroufsidhu.orgolink_auth.filters.JWTAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfigs(private val jwtAuthFilter: JWTAuthenticationFilter) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { customizer -> customizer.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/public/**", "/auth/**", "/register")
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { customizer ->
                customizer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)


        return http.build()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration) = authConfig.authenticationManager

    @Bean
    fun authenticationProvider(userDetailsService: UserDetailsService): AuthenticationProvider {
        val provider = DaoAuthenticationProvider(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder(12)
}