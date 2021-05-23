package davi.xavier.todolist

import android.content.res.Configuration
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
    private lateinit var todoViewModel: TodoViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.addButton.setOnClickListener { 
            add()
        }

        todoViewModel = TodoViewModel(DatabaseInstance.getInstance(this).todoDao())

        updateLayout()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig);
        updateLayout()
    }
    
    private fun updateLayout() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            supportFragmentManager.findFragmentById(R.id.catFrag)?.let {
                supportFragmentManager.beginTransaction()
                    .show(it)
                    .commit()
            }
        }
        else 
        {
            supportFragmentManager.findFragmentById(R.id.catFrag)?.let {
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .commit()
            }
        }
    }
    
    private fun add() {
        val text = binding.textField.text.toString()
        if (text.isNotEmpty())
        {
            todoViewModel.newTodo(text)
        }
    }
    
}
