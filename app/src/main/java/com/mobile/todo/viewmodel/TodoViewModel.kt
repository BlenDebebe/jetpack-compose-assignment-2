package com.mobile.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.todo.data.model.Todo
import com.mobile.todo.data.repository.TodoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    private val _selectedTodo = MutableStateFlow<Todo?>(null)
    val selectedTodo: StateFlow<Todo?> = _selectedTodo

    fun loadTodos() {
        viewModelScope.launch {
            repository.getTodos().collect {
                _todos.value = it
            }
        }
    }

    fun selectTodo(id: Int) {
        viewModelScope.launch {
            _selectedTodo.value = repository.getTodoById(id)
        }
    }
}
