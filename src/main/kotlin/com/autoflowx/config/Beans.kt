package com.autoflowx.config


import com.autoflowx.security.JwtFilter
import com.autoflowx.security.JwtUtil
import com.autoflowx.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.ReactiveUserDetailsService

@Configuration
class BeansConfig(private val jwtProperties: JwtProperties,
                  private val customUserDetailsService: CustomUserDetailsService
) {

    @Bean
    fun jwtUtil(): JwtUtil {
        return JwtUtil(jwtProperties.secret, jwtProperties.expiration)
    }

    @Bean
    fun jwtFilter(jwtUtil: JwtUtil): JwtFilter {
        return JwtFilter(jwtUtil, customUserDetailsService)
    }

    @Primary
    @Bean
    fun userDetailsService(): ReactiveUserDetailsService = customUserDetailsService
}