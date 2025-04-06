package com.autoflowx.security

import com.autoflowx.config.JwtProperties
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: ReactiveUserDetailsService
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authHeader = exchange.request.headers.getFirst("Authorization")

        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            if (!jwtUtil.validateToken(token)) return chain.filter(exchange)

            val username = jwtUtil.getUsername(token)
            userDetailsService.findByUsername(username)
                .map {
                    val auth = UsernamePasswordAuthenticationToken(it, null, it.authorities)
                    val context = SecurityContextImpl(auth)
                    context
                }
                .flatMap {
                    chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(it)))
                }
        } else {
            chain.filter(exchange)
        }
    }
}
