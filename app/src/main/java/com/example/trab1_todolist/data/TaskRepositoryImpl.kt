package com.example.trab1_todolist.data

import com.example.trab1_todolist.domain.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {
    override suspend fun insert(title: String, description: String?, startTime: String, endTime: String, id: Long?) {
        val entity = id?.let {
            dao.getBy(it)?.copy(
                    title = title,
                    description = description,
                    startTime = startTime,
                    endTime = endTime
                )
        } ?: TaskEntity(
            title = title,
            description = description,
            startTime = startTime,
            endTime = endTime,
            isCompleted = false
        )

        dao.insert(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existingEntity = dao.getBy(id) ?: return
        val updatedEntity = existingEntity.copy(isCompleted = isCompleted)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long) {
        val existingEntity = dao.getBy(id) ?: return
        dao.delete(existingEntity)
    }

    override fun getAll(): Flow<List<Task>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                Task(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    startTime = entity.startTime,
                    endTime = entity.endTime,
                    isCompleted = entity.isCompleted
                )
            }
        }
    }

    override suspend fun getBy(id: Long): Task? {
        return dao.getBy(id)?.let { entity ->
            Task(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                startTime = entity.startTime,
                endTime = entity.endTime,
                isCompleted = entity.isCompleted
            )
        }
    }
}