package com.example.todo.todo

import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Todo

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, todoItems: List<Todo>?){
    todoItems?.let {
        (listView.adapter as TodoAdapter).submitList(todoItems)
    }
}

@BindingAdapter("app:completedTodo")
fun setStyle(textView: TextView, isCompleted: Boolean){
    if(isCompleted) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        textView.setTextColor(Color.GRAY)
    }
    else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        textView.setTextColor(Color.BLACK)
    }
}

@BindingAdapter("app:imageResource")
fun setImage(imageView: ImageView, resource: Int){
    imageView.setImageResource(resource)
}