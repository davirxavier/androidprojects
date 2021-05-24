package davi.xavier.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import davi.xavier.todolist.databinding.FragmentListBinding
import davi.xavier.todolist.db.todo.DatabaseInstance
import davi.xavier.todolist.db.todo.TodoAdapter
import davi.xavier.todolist.db.todo.TodoViewModel
import davi.xavier.todolist.db.todo.TodoViewModelFactory

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var currentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        adapter = TodoAdapter()
        binding.todoList.adapter = adapter
        binding.todoList.layoutManager = LinearLayoutManager(requireActivity())

        todoViewModel = ViewModelProvider(requireActivity(), 
            TodoViewModelFactory(DatabaseInstance.getInstance(requireContext()).todoDao())).get(TodoViewModel::class.java)

        todoViewModel.getTodoList().observe(viewLifecycleOwner, { todos ->
            adapter.setAll(todos.map { t -> if (t.text == null) "" else (t.text as String) })
            adapter.deleteCallback = {
                if (it >= 0 && it < todos.size)
                {
                    todoViewModel.deleteTodo(todos[it].id)
                }
            }
        })
        
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentContext = context
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ListFragment.
         */
        @JvmStatic
        fun newInstance() =
            ListFragment().apply {
            }
    }
}
