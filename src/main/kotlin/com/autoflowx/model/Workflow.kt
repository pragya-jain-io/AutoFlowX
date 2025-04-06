package com.autoflowx.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "workflows")
data class Workflow(
    @Id val id: String? = null,
    val name: String,
    val description: String,
    val steps: List<Step>
)