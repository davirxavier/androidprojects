package davi.xavier.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import davi.xavier.todolist.databinding.ActivityMainBinding
import davi.xavier.todolist.db.todo.DatabaseInstance
import davi.xavier.todolist.db.todo.StringDiffChecker
import davi.xavier.todolist.db.todo.TodoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var todoViewModel: TodoViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.addButton.setOnClickListener { 
            add()
        }
        
        adapter = TodoAdapter()
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)

        todoViewModel = TodoViewModel(DatabaseInstance.getInstance(this).todoDao())
        
        todoViewModel.getTodoList().observe(this, { todos ->
            adapter.setAll(todos.map { t -> if (t.text == null) "" else (t.text as String) })
            
            adapter.deleteCallback = {
                if (it >= 0 && it < todos.size) 
                {
                    todoViewModel.deleteTodo(todos[it].id)
                }
            }
        })
    }
    
    private fun add() {
        val text = binding.textField.text.toString()
        if (text.isNotEmpty())
        {
            todoViewModel.newTodo(text)
        }
    }
    
    class TodoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.elementText)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }
    
    class TodoAdapter() : RecyclerView.Adapter<TodoHolder>() {
        var deleteCallback: (pos: Int) -> Unit = {}
        private var todos: MutableList<String> = mutableListOf()

        fun addTodo(text: String) {
            todos.add(text)
            notifyItemInserted(todos.size)
        }
        
        fun deleteTodo(i: Int) {
            if (i < todos.size && i >= 0)
            {
                todos.removeAt(i)
                notifyItemRemoved(i)
                notifyItemRangeChanged(i, todos.size)
            }
        }
        
        fun setAll(newTodos: List<String>) {
            val result = DiffUtil.calculateDiff(StringDiffChecker(this.todos, newTodos))
            todos.clear()
            todos.addAll(newTodos)
            
            result.dispatchUpdatesTo(this)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_element, parent, false)

            return TodoHolder(view)
        }

        override fun onBindViewHolder(holder: TodoHolder, position: Int) {
            holder.textView.text = todos[position]
            holder.deleteButton.setOnClickListener { 
                deleteCallback(holder.bindingAdapterPosition) 
            }
        }

        override fun getItemCount(): Int {
            return todos.size
        }

    }
}
