package com.example.todo.todo


import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Todo
import com.example.todo.databinding.TodoItemBinding

class TodoAdapter(
    private val viewModel: TodoViewModel,
    private val onItemClicked: (Todo) -> Unit
): ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallback){

    class TodoViewHolder(private var binding: TodoItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(viewModel: TodoViewModel, todo: Todo){
            binding.apply {
                viewmodel = viewModel
                item = todo
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoAdapter.TodoViewHolder {
        return TodoViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
        holder.bind(viewModel, item)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Todo>(){
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}