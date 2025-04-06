package com.autoflowx.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
class SecurityConfig(private val jwtFilter: JwtFilter) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user: UserDetails = User.withUsername("admin")
            .password(passwordEncoder().encode("password"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers("/auth/**").permitAll() // Allow login/signup
                    .anyExchange().authenticated()
            }
            .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Correctly add JWT filter
            .build()
    }
//    @Bean
//    fun authenticationManager(userDetailsService: UserDetailsService): AuthenticationManager {
//        val authProvider = DaoAuthenticationProvider()
//        authProvider.setUserDetailsService(userDetailsService)
//        authProvider.setPasswordEncoder(passwordEncoder())
//        return ProviderManager(authProvider)
//    }
@Bean
fun authenticationManager(
    userDetailsService: UserDetailsService,
    passwordEncoder: PasswordEncoder
): AuthenticationManager {
    val provider = DaoAuthenticationProvider()
    provider.setUserDetailsService(userDetailsService)
    provider.setPasswordEncoder(passwordEncoder)  // Must be BCrypt
    return ProviderManager(provider)
}
}
