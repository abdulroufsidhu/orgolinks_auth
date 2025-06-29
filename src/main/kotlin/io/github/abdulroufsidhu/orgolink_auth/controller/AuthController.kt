package io.github.abdulroufsidhu.orgolink_auth.controller

import io.github.abdulroufsidhu.orgolink_auth.model.User
import io.github.abdulroufsidhu.orgolink_auth.services.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val userService: UserService) {

    @GetMapping("/")
    fun securedThankYou(request: HttpServletRequest): ResponseEntity<String> {
        return ResponseEntity.ok(
            request.session.id
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody user: User) = userService.createUser(user)

    @GetMapping("/public/")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity.ok("Thank you")
    }
}