package com.example.todo.tododetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.TodoApplication
import com.example.todo.ViewModelFactory
import com.example.todo.addtodo.AddTodoFragmentDirections
import com.example.todo.data.Todo
import com.example.todo.databinding.FragmentTodoDetailBinding
import com.example.todo.todo.TodoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class TodoDetailFragment : Fragment() {

    private val navigationArgs: TodoDetailFragmentArgs by navArgs()

    private var _binding: FragmentTodoDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var todo: Todo

    private val viewModel: TodoViewModel by activityViewModels {
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
        _binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        setupMenuProvider()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.todoId
        viewModel.retrieveTodo(id).observe(this.viewLifecycleOwner){ selectedItem ->
            todo = selectedItem
            bind(todo)
        }
    }

    private fun bind(todo: Todo){
        binding.apply {
            titleEt.setText(todo.title)
            descriptionEt.setText(todo.description)
            saveFab.setOnClickListener {
                updateTodo(titleEt.text.toString(), descriptionEt.text.toString())
            }
        }
    }

    private fun updateTodo(title: String, description: String){
        todo.title = title
        todo.description = description
        viewModel.updateTodo(todo)
        val action = TodoDetailFragmentDirections.actionTodoDetailFragmentToTodoFragmentDest()
        findNavController().navigate(action)
    }

    private fun deleteTodo(todo: Todo){
        viewModel.deleteTodo(todo)
        findNavController().navigateUp()
    }

    private fun setupMenuProvider(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider{
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.tododetail_frag_menu, menu)
                }
                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId){
                        R.id.delete_menu -> {
                            deleteTodo(todo)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }
}