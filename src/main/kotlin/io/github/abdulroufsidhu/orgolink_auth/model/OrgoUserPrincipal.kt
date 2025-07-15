package io.github.abdulroufsidhu.orgolink_auth.model

import java.util.Collections
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

open class OrgoUserPrincipal(private val orgoUser: OrgoUser) : UserDetails {
  val id = orgoUser.id

  fun getUser(): OrgoUser = orgoUser

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return Collections.singleton(SimpleGrantedAuthority("User"))
  }

  @Throws(NoSuchFieldError::class)
  override fun getPassword(): String = orgoUser.password ?: throw NoSuchFieldError()

  @Throws(UsernameNotFoundException::class)
  override fun getUsername(): String =
          orgoUser.username
                  ?: throw UsernameNotFoundException("username is null while accessing principle")

  override fun isAccountNonExpired(): Boolean {
    return true
  }

  override fun isAccountNonLocked(): Boolean {
    return true
  }

  override fun isCredentialsNonExpired(): Boolean {
    return true
  }

  override fun isEnabled(): Boolean {
    return true
  }
}
