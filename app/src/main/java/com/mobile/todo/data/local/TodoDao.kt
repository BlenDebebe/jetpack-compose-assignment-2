package com.mobile.todo.data.local

import androidx.room.*
import com.mobile.todo.data.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    suspend fun getTodos(): List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos: List<Todo>)

    @Query("SELECT * FROM Todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?
}
