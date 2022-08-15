package com.example.todo.todo

import androidx.lifecycle.*
import com.example.todo.data.Todo
import com.example.todo.data.TodoDao
import kotlinx.coroutines.launch

class TodoViewModel(private val todoDao: TodoDao): ViewModel() {

    val todoItems: LiveData<List<Todo>> = todoDao.observeTodo().asLiveData()

    private fun insertTodo(todo: Todo){
        viewModelScope.launch { todoDao.insertTodo(todo) }
    }

    private fun getNewTodoEntry(title: String, description: String): Todo{
        return Todo(title = title, description = description)
    }

    fun addNewTodo(title: String, description: String){
        val newTodo = getNewTodoEntry(title, description)
        insertTodo(newTodo)
    }

}

