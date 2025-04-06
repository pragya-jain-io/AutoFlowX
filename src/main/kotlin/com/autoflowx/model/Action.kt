package com.autoflowx.model

data class Action(
    val type: String,         // e.g., HTTP, Kafka, Script, etc.
    val config: Map<String, String> // flexible config for different action types
)
