package com.autoflowx.repository
import com.autoflowx.model.ExecutionLog
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ExecutionLogRepository : ReactiveMongoRepository<ExecutionLog, String>