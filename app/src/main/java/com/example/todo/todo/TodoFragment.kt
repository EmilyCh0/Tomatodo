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
import com.google.android.material.tabs.TabLayout


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
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> viewModel.filterTodo(0)
                    1 -> viewModel.filterTodo(1)
                    2 -> viewModel.filterTodo(2)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}