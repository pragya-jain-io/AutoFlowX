package com.autoflowx.controller

import com.autoflowx.model.Workflow
import com.autoflowx.service.WorkflowService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/workflows")
class WorkflowController(private val workflowService: WorkflowService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createWorkflow(@RequestBody workflow: Workflow): Mono<Workflow> {
        return workflowService.createWorkflow(workflow)
    }

    @GetMapping
    fun getAllWorkflows(): Flux<Workflow> {
        return workflowService.getAllWorkflows()
    }

    @GetMapping("/{id}")
    fun getWorkflowById(@PathVariable id: String): Mono<Workflow> {
        return workflowService.getWorkflowById(id)
    }
}
