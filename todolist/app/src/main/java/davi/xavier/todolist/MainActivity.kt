package davi.xavier.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import davi.xavier.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.addButton.setOnClickListener { add() }
        
        adapter = TodoAdapter()
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
    }
    
    private fun add() {
        val text = binding.textField.text.toString()
        if (text.isNotEmpty())
        {
            adapter.addTodo(text)
            binding.textField.setText("")
        }
    }
    
    class TodoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.elementText)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }
    
    class TodoAdapter() : RecyclerView.Adapter<TodoHolder>() {
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
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_element, parent, false)

            return TodoHolder(view)
        }

        override fun onBindViewHolder(holder: TodoHolder, position: Int) {
            holder.textView.text = todos[position]
            holder.deleteButton.setOnClickListener { deleteTodo(position) }
        }

        override fun getItemCount(): Int {
            return todos.size
        }

    }
}
