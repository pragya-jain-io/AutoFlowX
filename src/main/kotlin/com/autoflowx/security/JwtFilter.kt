package com.autoflowx.security

import org.springframework.stereotype.Component
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        // TODO: Extract JWT, validate token, authenticate user
        return chain.filter(exchange) // Continue the filter chain
    }
}