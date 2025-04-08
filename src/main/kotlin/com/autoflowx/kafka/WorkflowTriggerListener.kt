package com.autoflowx.kafka

import com.autoflowx.service.WorkflowExecutor
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Component
class WorkflowTriggerListener(
    private val workflowExecutor: WorkflowExecutor
) {

    private val logger = LoggerFactory.getLogger(WorkflowTriggerListener::class.java)
    private val objectMapper = jacksonObjectMapper()

    data class WorkflowTriggerMessage(val workflowId: String)

    @KafkaListener(topics = ["workflow.triggered"], groupId = "autoflowx-group")
    fun listen(message: String) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val parsed = objectMapper.readValue<WorkflowTriggerMessage>(message)
                logger.info("Received workflow trigger for ID: ${parsed.workflowId}")
                workflowExecutor.execute(parsed.workflowId)
            } catch (e: Exception) {
                logger.error("Error parsing Kafka message: $message", e)
            }
        }
    }

}
