package com.autoflowx.service

import com.autoflowx.model.Workflow
import com.autoflowx.repository.WorkflowRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class WorkflowService(private val workflowRepository: WorkflowRepository) {

    fun createWorkflow(workflow: Workflow): Mono<Workflow> {
        return workflowRepository.save(workflow)
    }

    fun getAllWorkflows(): Flux<Workflow> {
        return workflowRepository.findAll()
    }

    fun getWorkflowById(id: String): Mono<Workflow> {
        return workflowRepository.findById(id)
    }
}
