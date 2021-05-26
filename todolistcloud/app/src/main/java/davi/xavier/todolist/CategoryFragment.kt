package davi.xavier.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import davi.xavier.todolist.databinding.FragmentCategoryBinding
import davi.xavier.todolist.db.category.CategoryAdapter
import davi.xavier.todolist.db.category.CategoryViewModel
import davi.xavier.todolist.db.todo.DatabaseInstance
import davi.xavier.todolist.db.todo.TodoViewModel
import davi.xavier.todolist.db.todo.TodoViewModelFactory

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        
        val adapter = CategoryAdapter()
        binding.catList.adapter = adapter
        binding.catList.layoutManager = LinearLayoutManager(context)
        
        val viewModel = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
        val todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        
        viewModel.getAll().observe(viewLifecycleOwner, { 
            adapter.setAll(it.map { cat -> cat.name ?: "" })
            adapter.checkedCallback = { i: Int, _: RadioButton ->
                Log.e("pos: ", i.toString())
                Log.e("id: ", it[i].id.toString())
                if (i >= 0 && i < it.size)
                {
                    adapter.checked = i
                    adapter.notifyDataSetChanged()

                    todoViewModel.filterTodosByCat(it[i].id, viewLifecycleOwner)
                }
            }
        })
        
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment()
    }
}
