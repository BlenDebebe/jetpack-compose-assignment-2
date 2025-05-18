package com.mobile.todo.data.repository

import com.mobile.todo.data.local.TodoDao
import com.mobile.todo.data.remote.TodoApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

class TodoRepository(private val api: TodoApi, private val dao: TodoDao) {
    fun getTodos() = flow {
        val cached = dao.getTodos()
        emit(cached)

        try {
            val remote = api.getTodos()
            dao.insertTodos(remote)
            emit(remote)
        } catch (e: Exception) {
            emit(cached) // fallback to cached
        }
    }

    suspend fun getTodoById(id: Int) = dao.getTodoById(id)
}
