package com.example.todo.todo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.TodoApplication
import com.example.todo.ViewModelFactory
import com.example.todo.addtodo.AddTodoDialog
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

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        val bindingViewModel = binding.viewmodel
        if(bindingViewModel!=null){
            todoAdapter = TodoAdapter(bindingViewModel){
                val action = TodoFragmentDirections.actionTodoFragmentDestToTodoDetailFragment(todoId = it.id, todoTitle = it.title)
                this.findNavController().navigate(action)
            }
            binding.recyclerview.adapter = todoAdapter
        }

        binding.addTodoFab.setOnClickListener {
            // val action = TodoFragmentDirections.actionTodoFragmentDestToAddTodoFragment()
            // findNavController().navigate(action)
            val dialog: AddTodoDialog = AddTodoDialog().getInstance()
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(
                    fragmentManager,
                    "Add Todo Dialog"
                )
            }

        }

    }
}