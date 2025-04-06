    package com.autoflowx.controller

    import com.autoflowx.dto.AuthRequest
    import com.autoflowx.dto.AuthResponse
    import com.autoflowx.security.JwtUtil
    import com.autoflowx.service.CustomUserDetailsService
    import org.springframework.http.ResponseEntity
    import org.springframework.security.authentication.ReactiveAuthenticationManager
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
    import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
    import org.springframework.security.core.userdetails.ReactiveUserDetailsService
    import org.springframework.security.core.userdetails.User
    import org.springframework.security.crypto.password.PasswordEncoder
    import org.springframework.web.bind.annotation.*
    import reactor.core.publisher.Mono

    @RestController
    @RequestMapping("/auth")
    class AuthController(
        private val authenticationManager: ReactiveAuthenticationManager,
        private val userDetailsService: CustomUserDetailsService,
        private val jwtUtil: JwtUtil,
        private val passwordEncoder: PasswordEncoder
    ) {

        @PostMapping("/login")
        fun login(@RequestBody request: AuthRequest): Mono<ResponseEntity<AuthResponse>> {
            val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
            return authenticationManager.authenticate(authToken)
                .flatMap {
                    val token = jwtUtil.generateToken(request.username)
                    Mono.just(ResponseEntity.ok(AuthResponse(token)))
                }
        }

        @PostMapping("/signup")
        fun signup(@RequestBody request: AuthRequest): Mono<ResponseEntity<String>> {
            val userDetails = User.withUsername(request.username)
                .password(passwordEncoder.encode(request.password))
                .roles("USER")
                .build()

            return userDetailsService.createUser(userDetails)
                .thenReturn(ResponseEntity.ok("User registered successfully!"))
        }
    }
