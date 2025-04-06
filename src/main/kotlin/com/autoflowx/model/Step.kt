package com.autoflowx.model


data class Step(
    val stepId: String,
    val name: String,
    val order: Int,
    val action: Action
)