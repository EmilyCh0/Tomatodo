package com.example.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    // 전체 observe
    @Query("SELECT * FROM todo")
    fun observeTodo(): Flow<List<Todo>>

    // 하나 observe
    @Query("SELECT * FROM todo WHERE id = :todoId")
    fun observeTodoById(todoId: Int): Flow<Todo>

    // 전체 select
    @Query("SELECT * FROM todo")
    suspend fun getTodo(): List<Todo>

    // id로 select
    @Query("SELECT * FROM todo WHERE id = :todoId")
    suspend fun getTodoById(todoId: Int): Todo?

    // 하나 insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    // update
    @Update
    suspend fun updateTodo(todo: Todo)

    // completed update
    @Query("UPDATE todo SET completed = :isCompleted WHERE id = :todoId")
    suspend fun updateCompleted(todoId: Int, isCompleted: Boolean)

    // id로 하나 delete
    @Query("DELETE FROM todo WHERE id = :todoId")
    suspend fun deleteTodoById(todoId: Int)

}