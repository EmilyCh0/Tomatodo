package com.example.todo.data

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.R
import java.util.*

@Entity(tableName = "todo")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "completed") var isCompleted: Boolean = false,
    @DrawableRes @ColumnInfo(name = "img_src") var imgSrc: Int = R.drawable.apple
){
    val isActive get() = !isCompleted

    val hasDescription get() = description != ""
}
