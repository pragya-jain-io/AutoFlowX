package com.autoflowx.service

import com.autoflowx.model.Workflow
import com.autoflowx.repository.WorkflowRepository
import org.springframework.stereotype.Service
import com.autoflowx.model.Step
import com.autoflowx.model.Action
import kotlinx.coroutines.reactor.awaitSingleOrNull


@Service
class WorkflowExecutor(private val workflowRepository: WorkflowRepository) {

    suspend fun execute(workflowId: String) {
        val workflow = workflowRepository.findById(workflowId).awaitSingleOrNull()
            ?: throw IllegalArgumentException("Workflow not found")


        val steps = workflow.steps.sortedBy { it.order }

        for (step in steps) {
            when (step.action.type.lowercase()) {
                "http" -> executeHttpAction(step)
                "kafka" -> executeKafkaAction(step)
                "log" -> executeLogAction(step)
                else -> println("Unsupported action type: ${step.action.type}")
            }
        }
    }

    private suspend fun executeHttpAction(step: Step) {
        val url = step.action.config["url"] ?: return
        val method = step.action.config["method"]?.uppercase() ?: "GET"
        println("Executing HTTP $method request to $url")
        // You can add actual WebClient logic here
    }

    private suspend fun executeKafkaAction(step: Step) {
        val topic = step.action.config["topic"] ?: return
        val message = step.action.config["message"] ?: "{}"
        println("Publishing Kafka message to $topic: $message")
        // You can integrate KafkaTemplate/WebFlux Kafka logic here
    }

    private suspend fun executeLogAction(step: Step) {
        val message = step.action.config["message"] ?: "No log message provided"
        println("LOG: $message")
    }
}