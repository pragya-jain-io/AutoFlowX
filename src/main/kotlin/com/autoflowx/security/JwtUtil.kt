package com.autoflowx.security


import org.springframework.stereotype.Component
import java.util.*
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec
@ConfigurationProperties(prefix = "jwt")
@Component
class JwtUtil(private val jwtProperties: com.autoflowx.config.JwtProperties) {
    val secret = jwtProperties.secret  // from application.yml
    val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    //private val key = SecretKeySpec(jwtProperties.secret.toByteArray(), "HmacSHA256")

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
//            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            getClaims(token).expiration.after(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getUsername(token: String): String {
        return getClaims(token).subject
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }
}