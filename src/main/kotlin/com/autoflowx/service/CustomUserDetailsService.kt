package com.autoflowx.service

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomUserDetailsService : ReactiveUserDetailsService {

    private val users = mutableMapOf<String, UserDetails>()

    override fun findByUsername(username: String): Mono<UserDetails> {
        return Mono.justOrEmpty(users[username])
    }

    fun createUser(user: UserDetails): Mono<Void> {
        users[user.username] = user
        return Mono.empty()
    }
}