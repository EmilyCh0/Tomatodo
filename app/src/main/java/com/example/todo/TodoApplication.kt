package com.example.todo

import android.app.Application
import com.example.todo.data.TodoDatabase

class TodoApplication: Application() {
    val todoDatabase: TodoDatabase by lazy {
        TodoDatabase.getDatabase(this)
    }
}