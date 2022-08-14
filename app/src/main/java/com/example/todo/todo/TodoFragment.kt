package com.example.todo.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.TodoApplication
import com.example.todo.ViewModelFactory
import com.example.todo.data.Todo
import com.example.todo.databinding.FragmentTodoBinding


class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding?= null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by activityViewModels {
        ViewModelFactory(
            (activity?.application as TodoApplication).todoDatabase.todoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TodoAdapter()

        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.adapter = adapter
        }
        viewModel.todoItems.observe(this.viewLifecycleOwner){ items ->
            items.let {
                adapter.submitList(it)
            }
        }


    }
}