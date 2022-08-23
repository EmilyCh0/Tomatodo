package com.example.todo.todo

import androidx.lifecycle.*
import com.example.todo.R
import com.example.todo.data.Todo
import com.example.todo.data.TodoDao
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class TodoViewModel(private val todoDao: TodoDao): ViewModel() {

    val todoItems: LiveData<List<Todo>> = todoDao.observeTodo().distinctUntilChanged().asLiveData()

    val filteredItems: MutableLiveData<List<Todo>> = todoDao.observeTodo().asLiveData() as MutableLiveData<List<Todo>>

    private fun insertTodo(todo: Todo){
        viewModelScope.launch { todoDao.insertTodo(todo) }
    }

    private fun getNewTodoEntry(title: String, description: String): Todo{
        val imgResList = listOf<Int>(
            R.drawable.apple2,
            R.drawable.tomato_w_leg,
            R.drawable.avocado_w_arm,
            R.drawable.lime
        )
        return Todo(title = title, description = description, imgSrc = imgResList.random())
    }

    fun filterTodo(type: Int){
        filteredItems.value = getTodoToShow(type)
    }

    private fun getTodoToShow(type: Int): List<Todo>{
        val todoToShow = ArrayList<Todo>()
        var todos: List<Todo> = listOf()
        viewModelScope.launch {
            todos = todoDao.getTodo()
            for(todo in todos){
                when(type){
                    0 -> todoToShow.add(todo)
                    1 -> if (todo.isActive){
                        todoToShow.add(todo)
                    }
                    2 -> if(todo.isCompleted){
                        todoToShow.add(todo)
                    }
                }
            }
        }
        return todoToShow
    }

    fun addNewTodo(title: String, description: String){
        val newTodo = getNewTodoEntry(title, description)
        insertTodo(newTodo)
    }

    fun toggleTodoCheckBox(todo: Todo, completed: Boolean) = viewModelScope.launch {
        if(completed){
            // complete
            todoDao.updateCompleted(todo.id, completed)
        }else{
            // activate
            todoDao.updateCompleted(todo.id, completed)
        }
    }

    fun retrieveTodo(id: Int): LiveData<Todo>{
        return todoDao.getTodoById(id).asLiveData()
    }

    fun updateTodo(todo: Todo){
        viewModelScope.launch{
            todoDao.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch {
            todoDao.deleteTodoById(todo.id)
        }
    }

}

