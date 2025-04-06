package com.autoflowx

import com.autoflowx.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
@EnableConfigurationProperties(JwtProperties::class)
@SpringBootApplication(scanBasePackages = ["com.autoflowx"])
class AutoflowxApplication

fun main(args: Array<String>) {
	runApplication<AutoflowxApplication>(*args)
}
