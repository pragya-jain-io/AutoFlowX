package com.autoflowx.repository

import com.autoflowx.model.Workflow
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkflowRepository : ReactiveMongoRepository<Workflow, String>