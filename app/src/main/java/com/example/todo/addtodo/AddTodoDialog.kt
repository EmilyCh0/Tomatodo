package com.example.todo.addtodo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.TodoApplication
import com.example.todo.ViewModelFactory
import com.example.todo.databinding.DialogAddTodoBinding
import com.example.todo.todo.TodoViewModel

class AddTodoDialog: DialogFragment(), View.OnClickListener {

    private var _binding: DialogAddTodoBinding?=null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by activityViewModels{
        ViewModelFactory(
            (activity?.application as TodoApplication).todoDatabase.todoDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAddTodoBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
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
        dismiss()
    }

    fun getInstance(): AddTodoDialog{
        return AddTodoDialog()
    }

    override fun onClick(v: View?) {
        dismiss()
    }
}