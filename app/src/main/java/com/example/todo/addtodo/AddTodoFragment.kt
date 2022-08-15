package com.example.todo.addtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.TodoApplication
import com.example.todo.ViewModelFactory
import com.example.todo.databinding.FragmentAddTodoBinding
import com.example.todo.todo.TodoViewModel


class AddTodoFragment : Fragment() {

    private var _binding: FragmentAddTodoBinding?= null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by activityViewModels{
        ViewModelFactory(
            (activity?.application as TodoApplication).todoDatabase.todoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            saveFab.setOnClickListener {
                val title = titleEt.text.toString()
                val desc = descriptionEt.text.toString()
                addNewTodo(title, desc)
            }
        }
    }

    private fun addNewTodo(title: String, description: String){
        viewModel.addNewTodo(title, description)
        val action = AddTodoFragmentDirections.actionAddTodoFragmentToTodoFragmentDest()
        findNavController().navigate(action)
    }


}