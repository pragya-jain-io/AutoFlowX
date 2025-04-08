package com.autoflowx.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("execution_logs")
data class ExecutionLog(
    @Id val id: String? = null,
    val workflowId: String,
    val stepName: String,
    val status: String, // "STARTED", "SUCCESS", "FAILED"
    val timestamp: Instant = Instant.now(),
    val message: String? = null
)
